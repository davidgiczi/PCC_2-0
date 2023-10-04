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
	private int rotation;
	private boolean sideOfRotation;
	private List<SteakoutedCoords> controlledCoords;
	private HomeController homeController;
	
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
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public void setSideOfRotation(boolean sideOfRotation) {
		this.sideOfRotation = sideOfRotation;
	}

	public void controlSteakout() {
		DecimalFormat df = new DecimalFormat("###.###");
		List<String> steakoutedPointData = homeController.fileProcess.getSteakoutedPointData();
		for (String controlData : steakoutedPointData) { 
			String[] data = controlData.split(delimiter);
			for(int i = 0; i < designedPillarCoords.size(); i++) {
				SteakoutedCoords steakouted = new SteakoutedCoords(baseType, designedPillarCoords.get(i).getPointID());
				steakouted.setDirectionPoint(directionPoint);
				steakouted.setCenterPoint(designedPillarCoords.get(0));
				if(getPointIdentifier(designedPillarCoords.get(i).getPointID()).equals(data[0])) {
					
				if(baseType == BaseType.WEIGHT_BASE) {
					steakouted.setPathDistance(
							df.format(new AzimuthAndDistance(designedPillarCoords.get(0), designedPillarCoords.get(1)).calcDistance()));
					steakouted.setCenterToHalfwayOfHolesDistance(
							df.format(new AzimuthAndDistance(designedPillarCoords.get(0), designedPillarCoords.get(5)).calcDistance()));
					}
				else if(baseType == BaseType.PLATE_BASE) {
					
					double horizonatlDistanceFromSideOfHole = 
							new AzimuthAndDistance(designedPillarCoords.get(0), designedPillarCoords.get(6)).calcDistance() -
							new AzimuthAndDistance(designedPillarCoords.get(2), designedPillarCoords.get(3)).calcDistance() / 2;
					steakouted.setHorizontalDistanceFromSideOfHole(
							df.format(horizonatlDistanceFromSideOfHole));
					double verticalDistanceFromSideOfHole =
							new AzimuthAndDistance(designedPillarCoords.get(0), designedPillarCoords.get(5)).calcDistance() -
							new AzimuthAndDistance(designedPillarCoords.get(1), designedPillarCoords.get(2)).calcDistance() / 2;
					steakouted.setVerticalDistanceFromSideOfHole(
							df.format(verticalDistanceFromSideOfHole));
					}
					steakouted.setStkPointID(data[0]);
					steakouted.setRotation(rotation);
					steakouted.setSideOfAngel(sideOfRotation);
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
	
	public List<SteakoutedCoords> getControlledCoords() {
		return controlledCoords;
	}
	
	public List<Point> getDesignedPillarCoords() {
		return designedPillarCoords;
	}

	public void printControlledPoints() {
		homeController.fileProcess.saveSteakoutedPoints(controlledCoords);
	}
	
}
