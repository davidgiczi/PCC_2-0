package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.text.DecimalFormat;

import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;

public class SteakoutedCoords implements Comparable<SteakoutedCoords> {

	private int id;
	private Point centerPoint;
	private Point directionPoint;
	private String pointID;
	private String stkPointID;
	private String comment;
	private String sign;
	private double rotation;
	private boolean sideOfAngel;
	private BaseType baseType;
	private String pathDistance;
	private String centerToForwardBackwardDistance;
	private String centerToLeftRightDistance;
	private String horizontalDistanceFromSideOfHole;
	private String verticalDistanceFromSideOfHole;
	private double XcoordForDesignPoint;
	private double YcoordForDesignPoint;
	private double XcoordForSteakoutPoint;
	private double YcoordForSteakoutPoint;
	private DecimalFormat df;
	
	public SteakoutedCoords(BaseType baseType, String pointID) {
		this.baseType = baseType;
		this.pointID = pointID;
		df = new DecimalFormat("0.000");
	}
	
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}
	
	public void setDirectionPoint(Point directionPoint) {
		this.directionPoint = directionPoint;
	}

	public void setXcoordForDesignPoint(double xcoordForDesignPoint) {
		XcoordForDesignPoint = xcoordForDesignPoint;
	}

	public void setYcoordForDesignPoint(double ycoordForDesignPoint) {
		YcoordForDesignPoint = ycoordForDesignPoint;
	}

	public void setXcoordForSteakoutPoint(double xcoordForSteakoutPoint) {
		XcoordForSteakoutPoint = xcoordForSteakoutPoint;
	}

	public void setYcoordForSteakoutPoint(double ycoordForSteakoutPoint) {
		YcoordForSteakoutPoint = ycoordForSteakoutPoint;
	}
	
	public void setPathDistance(String pathDistance) {
		this.pathDistance = pathDistance;
	}
	
	
	public void setCenterToForwardBackwardDistance(String centerToForwardBackwardDistance) {
		this.centerToForwardBackwardDistance = centerToForwardBackwardDistance;
	}
	
	public void setCenterToLeftRightDistance(String centerToLeftRightDistance) {
		this.centerToLeftRightDistance = centerToLeftRightDistance;
	}

	public void setHorizontalDistanceFromSideOfHole(String horizontalDistanceFromSideOfHole) {
		this.horizontalDistanceFromSideOfHole = horizontalDistanceFromSideOfHole;
	}

	public void setVerticalDistanceFromSideOfHole(String verticalDistanceFromSideOfHole) {
		this.verticalDistanceFromSideOfHole = verticalDistanceFromSideOfHole;
	}
	
	public String getStkPointID() {
		return stkPointID;
	}

	public void setStkPointID(String stkPointID) {
		this.stkPointID = stkPointID;
	}
	
	public double getXcoordForDesignPoint() {
		return XcoordForDesignPoint;
	}

	public double getYcoordForDesignPoint() {
		return YcoordForDesignPoint;
	}

	public double getXcoordForSteakoutPoint() {
		return XcoordForSteakoutPoint;
	}

	public double getYcoordForSteakoutPoint() {
		return YcoordForSteakoutPoint;
	}
	
	public void setSideOfAngel(boolean sideOfAngel) {
		this.sideOfAngel = sideOfAngel;
	}

	public String getSteakoutedPointData() {
		return pointID + 
				"\t" + comment +
				"\t" + sign +
				"\t" + String.valueOf(XcoordForDesignPoint).replace('.', ',') + 
				"\t" + String.valueOf(YcoordForDesignPoint).replace('.', ',') + 
				"\t" + String.valueOf(XcoordForSteakoutPoint).replace('.', ',') +
				"\t" + String.valueOf(YcoordForSteakoutPoint).replace('.', ',') +
				"\t" + (pointID.equals(centerPoint.getPointID()) ? 
						getXDifferenceOnMainLine() + "\t" + getYDifferenceOnMainLine() : getLinearDifferenceDataForReportFile());
	}
	
	public String getLinearDifferenceData() {
		if(pointID.equals(centerPoint.getPointID())) {
			return	getXDifferenceOnMainLine() + "\t" + getYDifferenceOnMainLine();
		}
		AzimuthAndDistance centerToDesignPoint = 
				new AzimuthAndDistance(centerPoint, new Point(pointID, XcoordForDesignPoint, YcoordForDesignPoint));
		AzimuthAndDistance centerToSteakoutedPoint = 
				new AzimuthAndDistance(centerPoint, new Point(pointID, XcoordForSteakoutPoint, YcoordForSteakoutPoint));
		double deltaAzimuthInSec = 3600 * Math.toDegrees(centerToDesignPoint.calcAzimuth() - centerToSteakoutedPoint.calcAzimuth());
		double E = 100 * centerToDesignPoint.calcDistance() * deltaAzimuthInSec / (3600 * 180 / Math.PI);
		return	"e= " + (deltaAzimuthInSec > 0 ? "+" : "") + 
				(int) deltaAzimuthInSec + "\", E= " + (deltaAzimuthInSec > 0 ? "+" : "") + (int) (10 * E) / 10.0 + "cm";
		
	}
	
	private String getLinearDifferenceDataForReportFile() {
		AzimuthAndDistance centerToDesignPoint = 
				new AzimuthAndDistance(centerPoint, new Point(pointID, XcoordForDesignPoint, YcoordForDesignPoint));
		AzimuthAndDistance centerToSteakoutedPoint = 
				new AzimuthAndDistance(centerPoint, new Point(pointID, XcoordForSteakoutPoint, YcoordForSteakoutPoint));
		double deltaAzimuthInSec = 3600 * Math.toDegrees(centerToDesignPoint.calcAzimuth() - centerToSteakoutedPoint.calcAzimuth());
		double E = 100 * centerToDesignPoint.calcDistance() * deltaAzimuthInSec / (3600 * 180 / Math.PI);
		return	(deltaAzimuthInSec > 0 ? "+" : "") + (int) deltaAzimuthInSec + "\"\t" + 
				(deltaAzimuthInSec > 0 ? "+" : "") + (int) (10 * E) / 10.0 + "cm";
		
	}
	
	private String getYDifferenceOnMainLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        centerPoint.getX_coord(), centerPoint.getY_coord()),
                        new Point("direction",
                                directionPoint.getX_coord(), directionPoint.getY_coord()));
        AzimuthAndDistance steakoutData =
                new AzimuthAndDistance(new Point("baseCenter",
                        centerPoint.getX_coord(), centerPoint.getY_coord()),
                        new Point("steakouted",
                                XcoordForSteakoutPoint, YcoordForSteakoutPoint));
        return df.format(steakoutData.calcDistance()
                * Math.sin(mainLineData.calcAzimuth() - steakoutData.calcAzimuth())).replace(",", ".") + "m";
    }

	private String getXDifferenceOnMainLine(){
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("baseCenter",
                        centerPoint.getX_coord(), centerPoint.getY_coord()),
                        new Point("direction",
                                directionPoint.getX_coord(), directionPoint.getY_coord()));
        AzimuthAndDistance steakoutData =
                new AzimuthAndDistance(new Point("baseCenter",
                        centerPoint.getX_coord(), centerPoint.getY_coord()),
                        new Point("steakouted",
                        		XcoordForSteakoutPoint, YcoordForSteakoutPoint));

        return df.format(steakoutData.calcDistance()
                * Math.cos(mainLineData.calcAzimuth() - steakoutData.calcAzimuth())).replace(",", ".") + "m";
    }
	
	public String getPointID() {
		return pointID;
	}

	public String getDeltaX() {
		return df.format(XcoordForDesignPoint - XcoordForSteakoutPoint);
	}
	
	public String getDeltaY() {
		return df.format(YcoordForDesignPoint - YcoordForSteakoutPoint);
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPointID(String pointID) {
		this.pointID = pointID;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setBaseType(BaseType baseType) {
		this.baseType = baseType;
	}

	public void setMetaData() {
		switch (baseType) {
		case PLATE_BASE:
			if( rotation == 0 ) 
			setMetaDataForPlateBaseControlledPoint();
			else 
			setMetaDataForRotatedPlateBaseControlledPoint();
			break;
		case WEIGHT_BASE:
			if( rotation == 0 ) 
			setMetaDataForWeightBaseControlledPoint();
			else 
			setMetaDataForRotatedWeightBaseControlledPoint();
		}
	}
	
	private int getPointIntegerID(Point point) {
		int id = 0;
		try {
			id = Integer.parseInt(point.getPointID());
		} catch (NumberFormatException e) {
			
			if( point.equals(centerPoint) )
				id = 0;
			else if( point.equals(directionPoint) )
				id = 1;
		}
		return id;
	}

	private void setMetaDataForWeightBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "karó szeggel";
			comment = "a nyomvonal iránya az oszlop közepétől " + pathDistance + " méterre " + 
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "előre" : "hátra");
			break;
		case "2":
			id = 2;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az oszlop közepétől " + pathDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra");
			break;
		case "3":
			id = 3;
			sign = "karó szeggel";
			comment = "a nyomvonal iránya az oszlop közepétől " + pathDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "hátra" : "előre");
			break;
		case "4":
			id = 4;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az oszlop közepétől " + pathDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra");
			break;
		case "5":
			id = 5;
			sign = "karó szeggel";
			comment = "a nyomvonal iránya az oszlop közepétől " + centerToForwardBackwardDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "előre" : "hátra");
			break;
		case "6": 
			id = 6;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az oszlop közepétől " + centerToLeftRightDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra");
			break;
		case "7":
			id = 7;
			sign = "karó szeggel";
			comment = "a nyomvonal iránya az oszlop közepétől " + centerToForwardBackwardDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "hátra" : "előre");
			break;
		case "8":
			id = 8;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az oszlop közepétől " + centerToLeftRightDistance + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra");
			break;
		case "9":
			id = 9;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "10":
			id = 10;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "11":
			id = 11;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "12":
			id = 12;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "13":
			id = 13;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "14":
			id = 14;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "15":
			id = 15;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "16":
			id = 16;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "17":
			id = 17;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "18":
			id = 18;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "19":
			id = 19;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "20":
			id = 20;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "21":
			id = 21;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "22":
			id = 22;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "23":
			id = 23;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "24":
			id = 24;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
		}
		
		
	}
	
	private void setMetaDataForPlateBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "2":
			id = 2;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "3":
			id = 3;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "4":
			id = 4;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "5":
			id = 5;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az alap gödrének szélétől " 
			+ verticalDistanceFromSideOfHole + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra");
			break;
		case "6":
			id = 6;
			sign = "karó szeggel";
			comment = "a nyomvonal iránya az alap gödrének szélétől " 
			+ horizontalDistanceFromSideOfHole + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "előre" : "hátra");
			break;
		case "7":
			id = 7;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az alap gödrének szélétől " 
			+ verticalDistanceFromSideOfHole + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra");;
			break;
		case "8":
			id = 8;
			sign = "karó szeggel";
			comment = "a nyomvonal iránya az alap gödrének szélétől " 
			+ horizontalDistanceFromSideOfHole + " méterre " +
			(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "hátra" : "előre");
		}
		
	}
	
	private void setMetaDataForRotatedWeightBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "karó szeggel";
			comment = "az oszlop tengelyének iránya az oszlop közepétől " + pathDistance + " méterre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "előre" : "hátra")
					+ " (az oszlop tengelye a nyomvonallal " + convertAngleMinSecFormat(Math.abs((180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "2":
			id = 2;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az oszlop közepétől " + pathDistance + " méterre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra")
					+ " (az oszlop karja a nyomvonallal " + convertAngleMinSecFormat(90 - (180 - rotation) / 2) + " szöget zár be)";
			break;
		case "3":
			id = 3;
			sign = "karó szeggel";
			comment = "az oszlop tengelyének iránya az oszlop közepétől " + pathDistance + " méterre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "hátra" : "előre")
					+ " (az oszlop tengelye a nyomvonallal " + convertAngleMinSecFormat(Math.abs((180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "4":
			id = 4;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az oszlop közepétől " + pathDistance + " méterre " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra")
					+ " (az oszlop karja a nyomvonallal " + convertAngleMinSecFormat(90 + (180 - rotation) / 2) + " szöget zár be)";
			break;
		case "5":
			id = 5;
			sign = "karó szeggel";
			comment = "az oszlop tengelyének iránya a gödrök széleinél " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "előre" : "hátra")
					+ " (az oszlop tengelye a nyomvonallal " + convertAngleMinSecFormat(Math.abs((180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "6":
			id = 6;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya a gödrök széleinél " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra")
					+ " (az oszlop karja a nyomvonallal " + convertAngleMinSecFormat(90 - (180 - rotation) / 2) + " szöget zár be)";
			break;
		case "7":
			id = 7;
			sign = "karó szeggel";
			comment = "az oszlop tengelyének iránya a gödrök széleinél " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "hátra" : "előre")
					+ " (az oszlop tengelye a nyomvonallal " + convertAngleMinSecFormat(Math.abs((180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "8":
			id = 8;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya a gödrök széleinél " +
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra")
					+ " (az oszlop karja a nyomvonallal " + convertAngleMinSecFormat(90 + (180 - rotation) / 2) + " szöget zár be)";
			break;
		case "9":
			id = 9;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "10":
			id = 10;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "11":
			id = 11;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "12":
			id = 12;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "13":
			id = 13;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "14":
			id = 14;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "15":
			id = 15;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "16":
			id = 16;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "17":
			id = 17;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "18":
			id = 18;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "19":
			id = 19;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "20":
			id = 20;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "21":
			id = 21;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "22":
			id = 22;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "23":
			id = 23;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "24":
			id = 24;
			sign = "karó";
			comment ="az oszlop láb gödör széle";
			break;
		case "25" :
			id = 25;
			sign = "karó";
			comment = directionPoint.getPointID() + ". számú oszlop iránya 20 méterre";
			break;
		case "26" :
			id = 26;
			sign = "karó";
			if( centerPoint.getPointID().equals("1") || centerPoint.getPointID().equals("T1"))
			comment = "Az előző oszlop iránya 20 méterre";
			else if(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) - 1) + ". számú oszlop iránya 20 méterre";
			else if (getPointIntegerID(centerPoint) > getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) + 1) + ". számú oszlop iránya 20 méterre";		
		}
	}
	
	private void setMetaDataForRotatedPlateBaseControlledPoint() {
		
		String[] pointIDComponents = pointID.split("_");
		
		if( pointIDComponents.length == 1) {
			setCenterPoint();
			return;
		}
		
		switch (pointIDComponents[1]) {
		case "1":
			id = 1;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "2":
			id = 2;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "3":
			id = 3;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "4":
			id = 4;
			sign = "karó";
			comment = "az alap gödrének pontja";
			break;
		case "5":
			id = 5;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az alap gödrének szélétől " 
			+ verticalDistanceFromSideOfHole + " méterre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "balra" : "jobbra")
					+ " (az oszlop karja a nyomvonallal " + 
					(sideOfAngel ? convertAngleMinSecFormat(90  + Math.abs(180 - rotation) / 2) : 
						convertAngleMinSecFormat(90  - Math.abs(180 - rotation) / 2))  + " szöget zár be)";
			break;
		case "6":
			id = 6;
			sign = "karó szeggel";
			comment = "az oszlop tengelyének iránya az alap gödrének szélétől " 
			+ horizontalDistanceFromSideOfHole + " méterre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "előre" : "hátra")
					+ " (az oszlop tengelye a nyomvonallal " + convertAngleMinSecFormat(Math.abs((180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "7":
			id = 7;
			sign = "karó szeggel";
			comment = "az oszlopkar iránya az alap gödrének szélétől " 
			+ verticalDistanceFromSideOfHole + " méterre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "jobbra" : "balra")
					+ " (az oszlop karja a nyomvonallal " + 
					(sideOfAngel ? convertAngleMinSecFormat(rotation / 2) : 
						convertAngleMinSecFormat(90  + Math.abs(180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "8":
			id = 8;
			sign = "karó szeggel";
			comment = "az oszlop tengelyének iránya az alap gödrének szélétől " 
			+ horizontalDistanceFromSideOfHole + " méterre " + 
					(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint) ?  "hátra" : "előre")
					+ " (az oszlop tengelye a nyomvonallal " + 
					convertAngleMinSecFormat(Math.abs((180 - rotation) / 2)) + " szöget zár be)";
			break;
		case "9":
			id = 9;
			sign = "karó";
			comment = directionPoint.getPointID() + ". számú oszlop iránya 20 méterre";
			break;
		case "10":
			id = 10;
			sign = "karó";
			if( centerPoint.getPointID().equals("1") || centerPoint.getPointID().equals("T1"))
			comment = "Az előző oszlop iránya 20 méterre";
			else if(getPointIntegerID(centerPoint) < getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) - 1) + ". számú oszlop iránya 20 méterre";
			else if (getPointIntegerID(centerPoint) > getPointIntegerID(directionPoint))
			comment  = (getPointIntegerID(centerPoint) + 1) + ". számú oszlop iránya 20 méterre";	
		}
		
	}
	
	private  String convertAngleMinSecFormat(double data){
        int angle = (int) data;
        int min = (int) ((data - angle) * 60);
        double sec = ((int) ((data - angle) * 3600 - min * 60));
        return (0 > data ? "-" :  "") + Math.abs(angle) + "° "
                + (9 < Math.abs(min) ? Math.abs(min) : "0" + Math.abs(min)) + "' "
                + (9 < Math.abs(sec) ? Math.abs(sec) : "0" + Math.abs(sec)) + "\"";
    }
	
	private void setCenterPoint() {
		id = 0;
		sign = "karó szeggel";
		comment = "az oszlop közepe";
	}

	@Override
	public int compareTo(SteakoutedCoords o) {
		return this.id > o.id ?  1 : this.id == o.id ? 0 : - 1;
	}

	@Override
	public String toString() {
		return getDeltaX().replace(",", ".") + "\t" + getDeltaY().replace(",", ".");
	}
	
	
}
 