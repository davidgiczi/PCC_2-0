package mvmxpert.david.giczi.pillarcoordscalculator.service;


import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointType;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import java.util.*;


public class MeasuredPillarData {

    public MeasuredPillarDataController measuredPillarDataController;
    private final List<MeasPoint> measPillarPoints;
    private List<MeasPoint> pillarBasePoints;
    private List<MeasPoint> pillarTopPoints;
    private MeasPoint pillarCenterPoint;
    private MeasPoint baseLineDirectionPoint;
    private boolean isRightRotationAngle;
    public double radRotation;
    private int angleRotation;
    private int minRotation;
    private int secRotation;

    public MeasuredPillarData(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        measPillarPoints = new ArrayList<>();
    }

    public List<MeasPoint> getMeasPillarPoints() {
        return measPillarPoints;
    }

    public MeasPoint getPillarCenterPoint() {
        return pillarCenterPoint;
    }
    public void setPillarCenterPoint(MeasPoint pillarCenterPoint) {
        this.pillarCenterPoint = pillarCenterPoint;
    }

    public MeasPoint getBaseLineDirectionPoint() {
        return baseLineDirectionPoint;
    }

    public void setBaseLineDirectionPoint(MeasPoint baseLineDirectionPoint) {
        this.baseLineDirectionPoint = baseLineDirectionPoint;
    }

    public List<MeasPoint> getPillarBasePoints() {
        return pillarBasePoints;
    }

    public List<MeasPoint> getPillarTopPoints() {
        return pillarTopPoints;
    }

    public int getAngleRotation() {
        return angleRotation;
    }

    public void setAngleRotation(int angleRotation) {
        this.angleRotation = angleRotation;
    }
    public int getMinRotation() {
        return minRotation;
    }

    public void setMinRotation(int minRotation) {
        this.minRotation = minRotation;
    }

    public int getSecRotation() {
        return secRotation;
    }

    public void setSecRotation(int secRotation) {
        this.secRotation = secRotation;
    }
    
    
    public boolean isRightRotationAngle() {
		return isRightRotationAngle;
	}

	public void setRightRotationAngle(boolean isRightRotationAngle) {
		this.isRightRotationAngle = isRightRotationAngle;
	}

	public void calcPillarLegsPoint(){
        pillarBasePoints = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            List<MeasPoint> pillarLegPoints = new ArrayList<>();
            for (MeasPoint measPillarPoint : measPillarPoints) {
                if( measPillarPoint.isUsed() &&
                        PointType.ALAP == measPillarPoint.getPointType() &&
                        i == measPillarPoint.getGroupID() ){
                    pillarLegPoints.add(measPillarPoint);
                }
            }
        if( pillarLegPoints.isEmpty() ){
            continue;
        }
            double pcs = 0;
            double x = 0.0;
            for (MeasPoint pillarLegPoint : pillarLegPoints) {
                x += pillarLegPoint.getX_coord();
                pcs++;
            }
            x = x / pcs;
            pcs = 0;
            double y = 0.0;
            for (MeasPoint pillarLegPoint : pillarLegPoints) {
                y += pillarLegPoint.getY_coord();
                pcs++;
            }
            y = y / pcs;
            pcs = 0;
            double z = 0.0;
            for (MeasPoint pillarLegPoint : pillarLegPoints) {
                z += pillarLegPoint.getZ_coord();
                pcs++;
            }
            z = z / pcs;
            MeasPoint pillarLegPoint = new MeasPoint(pillarLegPoints.get(0).getPointID(),
                    x, y, z, PointType.ALAP);
            pillarLegPoint.setUsed(true);
            pillarLegPoint.setGroupID(i);
            pillarBasePoints.add(pillarLegPoint);
        }
    }
	
	public boolean isAscPillarOrder(String centerPillarID, String directionPillarId) {
		int centerID = 0;
		int directionID = 1;
		try {
			centerID = Integer.parseInt(centerPillarID == null ? pillarCenterPoint.getPointID() : centerPillarID);
	        directionID = Integer.parseInt(directionPillarId == null ? baseLineDirectionPoint.getPointID() : directionPillarId);
		}
		catch(NumberFormatException e) {
			
		}
		
		return centerID < directionID;
	}

    public void addIDsForPillarLegs() {
        radRotation = Math.toRadians(angleRotation + minRotation / 60.0 + secRotation / 3600.0);

        Point center = new Point("center", getPillarBaseCenterPoint().getX_coord(),
                getPillarBaseCenterPoint().getY_coord());
        Point direction = new Point("direction", baseLineDirectionPoint.getX_coord(),
                baseLineDirectionPoint.getY_coord());
        AzimuthAndDistance baseLineDirection = new AzimuthAndDistance(center, direction);

        for (MeasPoint pillarBasePoint : pillarBasePoints) {
            pillarBasePoint.setAzimuth(center);
        }

        for (int i = 0; i < pillarBasePoints.size(); i++) {
            for (int j = i + 1; j < pillarBasePoints.size(); j++) {
                if (pillarBasePoints.get(i).getAzimuth() > pillarBasePoints.get(j).getAzimuth()) {
                    Collections.swap(pillarBasePoints, i, j);
                }
            }
        }

        if( pillarBasePoints.size() == 1 ){
           pillarBasePoints.get(0).setPointID(measuredPillarDataController
                   .measuredPillarData
                   .getPillarCenterPoint()
                   .getPointID());
            return;
        }
        else if( pillarBasePoints.size() != 4 ) {
                return;
        }

        if ( isAscPillarOrder(null, null) ) {

            if (radRotation == Math.PI) {

                if ( baseLineDirection.calcAzimuth() >= 0 &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(0).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("B");
                    pillarBasePoints.get(1).setPointID("C");
                    pillarBasePoints.get(2).setPointID("D");
                    pillarBasePoints.get(3).setPointID("A");
                } else if (pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(1).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("A");
                    pillarBasePoints.get(1).setPointID("B");
                    pillarBasePoints.get(2).setPointID("C");
                    pillarBasePoints.get(3).setPointID("D");
                } else if (pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(2).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("D");
                    pillarBasePoints.get(1).setPointID("A");
                    pillarBasePoints.get(2).setPointID("B");
                    pillarBasePoints.get(3).setPointID("C");
                } else if (pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(3).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("C");
                    pillarBasePoints.get(1).setPointID("D");
                    pillarBasePoints.get(2).setPointID("A");
                    pillarBasePoints.get(3).setPointID("B");
                } else if (pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= 2 * Math.PI) {
                    pillarBasePoints.get(0).setPointID("B");
                    pillarBasePoints.get(1).setPointID("C");
                    pillarBasePoints.get(2).setPointID("D");
                    pillarBasePoints.get(3).setPointID("A");
                }
                return;
            }
        }

        if ( !isAscPillarOrder(null, null) ) {

            if (radRotation == Math.PI) {

                if ( baseLineDirection.calcAzimuth() >= 0 &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(0).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("D");
                    pillarBasePoints.get(1).setPointID("A");
                    pillarBasePoints.get(2).setPointID("B");
                    pillarBasePoints.get(3).setPointID("C");
                } else if (pillarBasePoints.get(0).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(1).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("C");
                    pillarBasePoints.get(1).setPointID("D");
                    pillarBasePoints.get(2).setPointID("A");
                    pillarBasePoints.get(3).setPointID("B");
                } else if (pillarBasePoints.get(1).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(2).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("B");
                    pillarBasePoints.get(1).setPointID("C");
                    pillarBasePoints.get(2).setPointID("D");
                    pillarBasePoints.get(3).setPointID("A");
                } else if (pillarBasePoints.get(2).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= pillarBasePoints.get(3).getAzimuth()) {
                    pillarBasePoints.get(0).setPointID("A");
                    pillarBasePoints.get(1).setPointID("B");
                    pillarBasePoints.get(2).setPointID("C");
                    pillarBasePoints.get(3).setPointID("D");
                } else if (pillarBasePoints.get(3).getAzimuth() < baseLineDirection.calcAzimuth() &&
                        baseLineDirection.calcAzimuth() <= 2 * Math.PI) {
                    pillarBasePoints.get(0).setPointID("D");
                    pillarBasePoints.get(1).setPointID("A");
                    pillarBasePoints.get(2).setPointID("B");
                    pillarBasePoints.get(3).setPointID("C");
                }
                return;
            }
        }

        if( isAscPillarOrder(null, null) ){

            double rotation = isRightRotationAngle ? (Math.PI - radRotation) / 2 : - (Math.PI - radRotation) / 2;
            
            double alfa = 0 >= baseLineDirection.calcAzimuth() - rotation ?  
            		baseLineDirection.calcAzimuth() - rotation + 2 * Math.PI : baseLineDirection.calcAzimuth() - rotation;

          if( 0 < alfa && alfa <= pillarBasePoints.get(0).getAzimuth()){
              pillarBasePoints.get(0).setPointID("B");
              pillarBasePoints.get(1).setPointID("C");
              pillarBasePoints.get(2).setPointID("D");
              pillarBasePoints.get(3).setPointID("A");
          }
          else if( pillarBasePoints.get(0).getAzimuth() < alfa && alfa <= pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < alfa && alfa <= pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < alfa && alfa <= pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < alfa && alfa <= 2 * Math.PI ){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
            return;
        }

        if( !isAscPillarOrder(null, null) )   {

            double rotation = isRightRotationAngle ? (Math.PI + radRotation) / 2 : - (Math.PI + radRotation) / 2;
            
            double alfa = baseLineDirection.calcAzimuth() + rotation > 2 * Math.PI ?  
            		baseLineDirection.calcAzimuth() + rotation - 2 * Math.PI : baseLineDirection.calcAzimuth() + rotation;

            if( 0 < alfa && alfa <= pillarBasePoints.get(0).getAzimuth()){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
           else if( pillarBasePoints.get(0).getAzimuth() < alfa && alfa <= pillarBasePoints.get(1).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("A");
                pillarBasePoints.get(1).setPointID("B");
                pillarBasePoints.get(2).setPointID("C");
                pillarBasePoints.get(3).setPointID("D");
            }
            else if( pillarBasePoints.get(1).getAzimuth() < alfa && alfa <= pillarBasePoints.get(2).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("D");
                pillarBasePoints.get(1).setPointID("A");
                pillarBasePoints.get(2).setPointID("B");
                pillarBasePoints.get(3).setPointID("C");
            }
            else if( pillarBasePoints.get(2).getAzimuth() < alfa && alfa <= pillarBasePoints.get(3).getAzimuth() ){
                pillarBasePoints.get(0).setPointID("C");
                pillarBasePoints.get(1).setPointID("D");
                pillarBasePoints.get(2).setPointID("A");
                pillarBasePoints.get(3).setPointID("B");
            }
            else if( pillarBasePoints.get(3).getAzimuth() < alfa && alfa <= 2 * Math.PI ){
                pillarBasePoints.get(0).setPointID("B");
                pillarBasePoints.get(1).setPointID("C");
                pillarBasePoints.get(2).setPointID("D");
                pillarBasePoints.get(3).setPointID("A");
            }
        }
    }
    
    public String calcPillarBaseDirectionDifference() {
    	
    		Point pointA = null;
    		Point pointB = null;
    		Point pointC = null;
    		Point pointD = null;
    	
    	for (MeasPoint measPoint: pillarBasePoints){
    		
    	if( "A".equals(measPoint.getPointID()) ){
    			pointA = new Point(measPoint.getPointID(), measPoint.getX_coord(), measPoint.getY_coord());
    		}
    	else if( "B".equals(measPoint.getPointID()) ){
    			pointB = new Point(measPoint.getPointID(), measPoint.getX_coord(), measPoint.getY_coord());
    		}
    	else if( "C".equals(measPoint.getPointID()) ){
    			pointC = new Point(measPoint.getPointID(), measPoint.getX_coord(), measPoint.getY_coord());
    		}
    	else if( "D".equals(measPoint.getPointID()) ){
    			pointD = new Point(measPoint.getPointID(), measPoint.getX_coord(), measPoint.getY_coord());
    	}
    }
    	if( pointA == null || pointB == null || pointC == null || pointD == null ) {
    		return "";
    	}
    	
    	AzimuthAndDistance azimuthBaseLine = new AzimuthAndDistance
    			( new Point(pillarCenterPoint.getPointID(), pillarCenterPoint.getX_coord(), pillarCenterPoint.getY_coord()), 
    			new Point(baseLineDirectionPoint.getPointID(), baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()) );
    	
    	double e1;
    	double e2;
    	
    	if( radRotation == Math.PI && isAscPillarOrder(null, null) ) {
    		AzimuthAndDistance DtoA = new AzimuthAndDistance(pointD, pointA);
    		e1 = Math.toDegrees(azimuthBaseLine.calcAzimuth() - DtoA.calcAzimuth());
    		AzimuthAndDistance CtoB = new AzimuthAndDistance(pointC, pointB);
    		e2 = Math.toDegrees(azimuthBaseLine.calcAzimuth() - CtoB.calcAzimuth());	
    		return "D → A: e=" + getAngleMinSecFormat(e1) + ", E= " + (int) (100 * DtoA.calcDistance() * e1 / ( 180 / Math.PI ))  +  "cm\n" +
    			   "C → B: e=" + getAngleMinSecFormat(e2) + ", E= " + (int) (100 * CtoB.calcDistance() * e2 / ( 180 / Math.PI ))  +  "cm\n\n" + 
    			   "Alap elcsavarodás: e=" + getAngleMinSecFormat(getPillarBaseTwisting());
    	}
    	else if( radRotation == Math.PI && !isAscPillarOrder(null, null) ) {
    		AzimuthAndDistance AtoD = new AzimuthAndDistance(pointA, pointD);
    		e1 = Math.toDegrees(azimuthBaseLine.calcAzimuth() - AtoD.calcAzimuth());
    		AzimuthAndDistance BtoC = new AzimuthAndDistance(pointB, pointC);
    		e2 = Math.toDegrees(azimuthBaseLine.calcAzimuth() - BtoC.calcAzimuth());
    		return "A → D: e=" + getAngleMinSecFormat(e1) + ", E= " + (int) (100 * AtoD.calcDistance() * e1 / ( 180 / Math.PI ))  + "cm\n" +
    			   "B → C: e=" + getAngleMinSecFormat(e2) + ", E= " + (int) (100 * BtoC.calcDistance() * e2 / ( 180 / Math.PI ))  + "cm\n\n" +
    			   "Alap elcsavarodás: e="+ getAngleMinSecFormat(getPillarBaseTwisting());
    	}
    	else if( radRotation != Math.PI && isAscPillarOrder(null, null) ) {
    		AzimuthAndDistance DtoA = new AzimuthAndDistance(pointD, pointA);
    		double rotation = isRightRotationAngle ? - (Math.PI - radRotation) / 2 : (Math.PI - radRotation) / 2;
    		double alfa = azimuthBaseLine.calcAzimuth() - DtoA.calcAzimuth() + rotation;
    		e1 = Math.toDegrees(alfa);
    		AzimuthAndDistance CtoB = new AzimuthAndDistance(pointC, pointB);
    		double beta = azimuthBaseLine.calcAzimuth() - CtoB.calcAzimuth() + rotation;
    		e2 = Math.toDegrees(beta);	
    		return "D → A: e=" + getAngleMinSecFormat(e1) + ", E= " + (int) (100 * DtoA.calcDistance() * e1 / ( 180 / Math.PI ))  +  "cm\n" +
    			   "C → B: e=" + getAngleMinSecFormat(e2) + ", E= " + (int) (100 * CtoB.calcDistance() * e2 / ( 180 / Math.PI ))  +  "cm\n\n" + 
    			   "Alap elcsavarodás: e=" + getAngleMinSecFormat(getPillarBaseTwisting());
    	}
    	else if( radRotation != Math.PI && !isAscPillarOrder(null, null) ) {
    		AzimuthAndDistance AtoD = new AzimuthAndDistance(pointA, pointD);
    		double rotation = isRightRotationAngle ?  - (Math.PI - radRotation) / 2 : (Math.PI - radRotation) / 2;
    		double alfa = azimuthBaseLine.calcAzimuth() - AtoD.calcAzimuth() + rotation;
    		e1 = Math.toDegrees(alfa);
    		AzimuthAndDistance BtoC = new AzimuthAndDistance(pointB, pointC);
    		double beta = azimuthBaseLine.calcAzimuth() - BtoC.calcAzimuth() + rotation;
    		e2 = Math.toDegrees(beta);	
    		return "A → D: e=" + getAngleMinSecFormat(e1) + ", E= " + (int) (100 * AtoD.calcDistance() * e1 / ( 180 / Math.PI ))  +  "cm\n" +
    			   "B → C: e=" + getAngleMinSecFormat(e2) + ", E= " + (int) (100 * BtoC.calcDistance() * e2 / ( 180 / Math.PI ))  +  "cm\n\n" + 
    			   "Alap elcsavarodás: e=" + getAngleMinSecFormat(getPillarBaseTwisting());
    	}
    	
    	return "";
    }
    
    private double getPillarBaseTwisting() {
    	try {
            AzimuthAndDistance mainLineData =
                    new AzimuthAndDistance(new Point("teoCenter",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                            new Point("direction",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."))));
            AzimuthAndDistance differenceData =
                    new AzimuthAndDistance(new Point("measuredCenter",
             measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord(), 
             measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()),
                            new Point("direction",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."))));   
        return Math.toDegrees(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    	}
    	catch (NumberFormatException e) {
			return Double.NaN;
		}
    }
    
    public String getAngleMinSecFormat(double angleValue){

        int angle = (int) Math.abs( angleValue );
        int min = (int) ((Math.abs( angleValue ) - angle) * 60);
        int sec = (int) (Math.abs( angleValue ) * 3600 -  angle * 3600 - min * 60);

        return (angleValue > 0 ? "+" : "-") + angle  + "° " + (min < 10 ? "0" + min : min) + "' " +
               (sec < 10 ? "0" + sec : sec) + "\"";
      }

    public MeasPoint getPillarBaseCenterPoint(){
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        for (MeasPoint basePoint : pillarBasePoints) {
            x += basePoint.getX_coord();
            y += basePoint.getY_coord();
            z += basePoint.getZ_coord();
        }
        x = x / pillarBasePoints.size();
        y = y / pillarBasePoints.size();
        z = z / pillarBasePoints.size();
        return new MeasPoint(pillarCenterPoint.getPointID(), x , y, z, PointType.ALAP);
    }
    public void calcPillarTopPoints(){
        pillarTopPoints = new ArrayList<>();
        for (MeasPoint topPoint: measPillarPoints) {
            if( topPoint.isUsed() && topPoint.getPointType() == PointType.CSUCS ){
                pillarTopPoints.add(topPoint);
            }
        }
    }
    public MeasPoint getPillarTopCenterPoint(){
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        for (MeasPoint topPoint : pillarTopPoints) {
            x += topPoint.getX_coord();
            y += topPoint.getY_coord();
            z += topPoint.getZ_coord();
        }
        x = x / pillarTopPoints.size();
        y = y / pillarTopPoints.size();
        z = z / pillarTopPoints.size();
        return new MeasPoint("TopCenter", x , y, z, PointType.CSUCS);
    }

    public void convertMeasuredDataToMeasPoints(List<String> measData){
        measData.forEach(data -> {
            String[] baseData = data.split(",");
            String[] topData = data.split(";");
            if( baseData.length == 1 && topData.length == 1){
              measuredPillarDataController. getInfoAlert("Nem megfelelő mérési fájl formátum",
                        "Az elválasztó karakter a mérési fájlban \",\" vagy \";\" lehet.");
                return;
            }
            if( baseData.length > 1) {
                MeasPoint basePoint = new MeasPoint(baseData[0],
                        Double.parseDouble(baseData[1]),
                        Double.parseDouble(baseData[2]),
                        Double.parseDouble(baseData[3]),
                        PointType.ALAP);
                if ( !measPillarPoints.contains(basePoint) ) {
                        measPillarPoints.add(basePoint);
                }
            }
            if( topData.length > 1 ) {
                MeasPoint topPoint = new MeasPoint(topData[0],
                        Double.parseDouble(topData[1]),
                        Double.parseDouble(topData[2]),
                        Double.parseDouble(topData[3]),
                        PointType.CSUCS);
                if ( !measPillarPoints.contains(topPoint) ) {
                    measPillarPoints.add(topPoint);
                }
            }
        });
    }

    public double getXDifferenceOnMainLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));

        return differenceData.calcDistance()
                * Math.cos(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    }

    public double getYDifferenceOnMainLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));
        return differenceData.calcDistance()
                * Math.sin(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    }

    public double getXDifferenceOnBackwardLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));
        
        return differenceData.calcDistance()
                * Math.cos(mainLineData.calcAzimuth() + (isRightRotationAngle ? radRotation : - radRotation) - differenceData.calcAzimuth());
    }

    public double getYDifferenceOnBackwardLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("direction",
                                baseLineDirectionPoint.getX_coord(), baseLineDirectionPoint.getY_coord()));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("baseCenter",
                        getPillarBaseCenterPoint().getX_coord(), getPillarBaseCenterPoint().getY_coord()),
                        new Point("topCenter",
                                getPillarTopCenterPoint().getX_coord(), getPillarTopCenterPoint().getY_coord()));
        return differenceData.calcDistance()
                * Math.sin(mainLineData.calcAzimuth() +  (isRightRotationAngle ? radRotation : - radRotation) - differenceData.calcAzimuth());
    }

    public void parseProjectFileData(List<String> projectFileData){

        this.pillarBasePoints = new ArrayList<>();
        int i = 9;
       while( i < projectFileData.size() && projectFileData.get(i).endsWith("ALAP") ){
            String[] baseData = projectFileData.get(i).split("#");
            MeasPoint basePoint = new MeasPoint(baseData[0],
                    Double.parseDouble(baseData[1]),
                    Double.parseDouble(baseData[2]),
                    Double.parseDouble(baseData[3]),
                    PointType.ALAP);
            pillarBasePoints.add(basePoint);
            i++;
        }
        this.pillarTopPoints = new ArrayList<>();
        while( i < projectFileData.size() && projectFileData.get(i).endsWith("CSUCS") ){
            String[] baseData = projectFileData.get(i).split("#");
            MeasPoint topPoint = new MeasPoint(baseData[0],
                    Double.parseDouble(baseData[1]),
                    Double.parseDouble(baseData[2]),
                    Double.parseDouble(baseData[3]),
                    PointType.CSUCS);
            pillarTopPoints.add(topPoint);
            i++;
        }
    }

}
