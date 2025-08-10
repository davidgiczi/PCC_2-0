package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointID;


public class SteakoutControl {

	private List<Point> designedPillarCoords;
	private Point directionPoint;
	private BaseType baseType;
	private PointID pointID;
	private String pointIDValue;
	private String delimiter;
	private double rotation;
	private List<SteakoutedCoords> controlledCoords;
	private HomeController homeController;
	private DecimalFormat df = new DecimalFormat("###.###");
	
	public SteakoutControl(HomeController homeController, BaseType baseType) {
		this.homeController = homeController;
		this.baseType = baseType;
		controlledCoords = new ArrayList<>();
	}

	public void setDesignedPillarCoords(List<Point> designedPillarCoords) {
		this.designedPillarCoords = designedPillarCoords;
	}

	public void setDirectionPoint(Point directionPoint) {
		this.directionPoint = directionPoint;
	}

	public void setControlledCoords(List<SteakoutedCoords> controlledCoords) {
		this.controlledCoords = controlledCoords;
	}
	
	public void setPointID(PointID pointID) {
		this.pointID = pointID;
	}

	public void setPointIDValue(String pointIDValue) {
		this.pointIDValue = pointIDValue;
	}


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public List<SteakoutedCoords> getControlledCoords() {
		return controlledCoords;
	}
	

	public void controlSteakout() {
		List<String> steakoutedPointData = homeController.fileProcess.getSteakoutedPointData();
		for (String controlData : steakoutedPointData) { 
			for (int i = 0; i < designedPillarCoords.size(); i++) {
			String[] data = controlData.split(delimiter);
			SteakoutedCoords steakouted = new SteakoutedCoords(baseType, designedPillarCoords.get(i).getPointID());
			steakouted.setDirectionPoint(directionPoint);
			steakouted.setCenterPoint(designedPillarCoords.get(0));
			
			if(getPointIdentifier(designedPillarCoords.get(i).getPointID()).equals(data[0])) {
					
				if(baseType == BaseType.WEIGHT_BASE) {		
					
					if( data[0].endsWith("_1")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setForwardDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_2")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setRightDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_3")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setBackwardDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_4")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setLeftDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_5")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setForwardDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_6")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setRightDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_7")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setBackwardDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_8")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setLeftDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_25")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setForwardDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_26")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setBackwardDistance(calcDistanceForWeightBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForWeightBase(steakoutedPoint));
					}
	}
				else if(baseType == BaseType.PLATE_BASE) {
					
					
					if( data[0].endsWith("_5") ) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setLeftDistance(calcDistanceForPlateBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForPlateBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_6")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setForwardDistance(calcDistanceForPlateBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForPlateBase(steakoutedPoint));	
					}
					else if( data[0].endsWith("_7")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setRightDistance(calcDistanceForPlateBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForPlateBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_8")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setBackwardDistance(calcDistanceForPlateBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForPlateBase(steakoutedPoint));
					}
					else if( data[0].endsWith("_9")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setForwardDistance(calcDistanceForPlateBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForPlateBase(steakoutedPoint));
					}	
					else if( data[0].endsWith("_10")) {
						Point steakoutedPoint = new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
						steakouted.setBackwardDistance(calcDistanceForPlateBase(steakoutedPoint));
						steakouted.setAngle(calcAngleForPlateBase(steakoutedPoint));
					}	
					
		}
					steakouted.setStkPointID(data[0]);
					steakouted.setXcoordForDesignPoint(designedPillarCoords.get(i).getX_coord());
					steakouted.setYcoordForDesignPoint(designedPillarCoords.get(i).getY_coord()); 
					steakouted.setXcoordForSteakoutPoint(Double.parseDouble(data[1]));
					steakouted.setYcoordForSteakoutPoint(Double.parseDouble(data[2]));
					steakouted.setMetaData();
					controlledCoords.add(steakouted);
	}
			}
		}
		
		Collections.sort(controlledCoords);
	}
	
	
	private String getPointIdentifier(String pointNumber) {
		
		String identifier = pointNumber;
		
		if(pointID == PointID.PREFIX) {
		identifier = pointIDValue + identifier;
		}
		else if(pointID == PointID.POSTFIX) {
		identifier = identifier + pointIDValue;
		}
		
		return identifier;
	}
	
	private Point getCenterPoint() {
		List<String> steakoutedPointData = homeController.fileProcess.getSteakoutedPointData();
		for (String controlData : steakoutedPointData) {
			String[] data = controlData.split(delimiter);
			if( getPointIdentifier(designedPillarCoords.get(0).getPointID()).equals(data[0]) ){
				return new Point(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]));
			}
		}
		return designedPillarCoords.get(0);
	}
	
	private String calcDistanceForWeightBase(Point steakoutedPoint) {
		Point center = getCenterPoint();
		return df.format(new AzimuthAndDistance(center, steakoutedPoint).calcDistance()).replace(",", ".");
	}
	
	private String calcDistanceForPlateBase(Point steakoutedPoint) {
		Point center = getCenterPoint();
		double centerDistance = new AzimuthAndDistance(center, steakoutedPoint).calcDistance();
		
		if( steakoutedPoint.getPointID().endsWith("_5") || steakoutedPoint.getPointID().endsWith("_7")) {
		return df.format(centerDistance - 
				new AzimuthAndDistance(designedPillarCoords.get(1), designedPillarCoords.get(2)).calcDistance() / 2.0)
				.replace(",", ".");	
		}
		else if(steakoutedPoint.getPointID().endsWith("_6") || steakoutedPoint.getPointID().endsWith("_8")) {
			return df.format(centerDistance - 
					new AzimuthAndDistance(designedPillarCoords.get(2), designedPillarCoords.get(3)).calcDistance() / 2.0)
					.replace(",", ".");	
		}
		return df.format(centerDistance).replace(",", ".");	
	}
	
	private double calcAngleForWeightBase(Point steakoutedPoint) {
		if( rotation == 0.0 ) {
			return rotation;
		}
		else if( steakoutedPoint.getPointID().endsWith("_25") || steakoutedPoint.getPointID().endsWith("_26")) {
			return Math.toDegrees(rotation);
		}
		Point center = getCenterPoint();
		
		double angle = Math.abs(new AzimuthAndDistance(center, steakoutedPoint).calcAzimuth() - 
				new AzimuthAndDistance(center, directionPoint).calcAzimuth());
		
		 if( angle > Math.PI )  {
			return steakoutedPoint.getPointID().endsWith("_3") ?
					Math.toDegrees(Math.PI - Math.abs(angle - 2 * Math.PI)) :
					Math.toDegrees(Math.abs(angle - 2 * Math.PI));
		}
		 
		return steakoutedPoint.getPointID().endsWith("_3") ?
				Math.toDegrees(Math.PI - angle) :
				Math.toDegrees(angle);
	}
	
	
	private double calcAngleForPlateBase(Point steakoutedPoint) {
		if( rotation == 0.0 ) {
			return rotation;
		}
		else if( steakoutedPoint.getPointID().endsWith("_9") || steakoutedPoint.getPointID().endsWith("_10")) {
			return Math.toDegrees(rotation);
		}
		Point center = getCenterPoint();
		
		double angle = Math.abs(new AzimuthAndDistance(center, steakoutedPoint).calcAzimuth() - 
				new AzimuthAndDistance(center, directionPoint).calcAzimuth());
		
		if( angle > Math.PI )  {
			return steakoutedPoint.getPointID().endsWith("_8") ?
					Math.toDegrees(Math.PI - Math.abs(angle - 2 * Math.PI)) :
					Math.toDegrees(Math.abs(angle - 2 * Math.PI));
		}
		
		return steakoutedPoint.getPointID().endsWith("_8") ?
				Math.toDegrees(Math.PI - angle) :
				Math.toDegrees(angle);
	}
	

	public List<Point> getDesignedPillarCoords() {
		return designedPillarCoords;
	}

	public void printControlledPoints() {
		homeController.fileProcess.saveSteakoutedPoints(controlledCoords);
	}
	
}
