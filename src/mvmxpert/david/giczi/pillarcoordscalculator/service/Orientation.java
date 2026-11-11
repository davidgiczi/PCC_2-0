package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.util.ArrayList;
import java.util.List;

public class Orientation {

	
	private Point standingPoint;
	private String aveOrientation;
	private List<Point> orientationPointList;
	private List<Double> directionValueList;
	private List<AzimuthAndDistance> azimuthAndDistanceList;
	public static List<OrientationData> ORIENTATION_RESULT_DATA;
	
	
	public Orientation() {
	}
	
	public Orientation(List<RowData> orientationDataList, boolean byDistance) { 
		if( orientationDataList.isEmpty() ) {
			throw new NumberFormatException();
		}
		setOrientationData(orientationDataList);
		calcAzimuthAndDistance();
		ORIENTATION_RESULT_DATA = new ArrayList<>();
		calcAverageOrientationAngleValue(byDistance);
	}
	
	public Point getStandingPoint() {
		return standingPoint;
	}
	
	public void setStandingPoint(Point standingPoint) {
		this.standingPoint = standingPoint;
	}

	public String getAveOrientation() {
		return aveOrientation;
	}
	
	public List<Point> getOrientationPointList() {
		return orientationPointList;
	}

	public void setOrientationPointList(List<Point> orientationPointList) {
		this.orientationPointList = orientationPointList;
	}

	private void setOrientationData(List<RowData> orientationDataList) {
		String[] orientationData = orientationDataList.get(0).toString().split(";");
		standingPoint = new Point(orientationData[0], 
				Double.parseDouble(orientationData[1]), Double.parseDouble(orientationData[2]));
		this.orientationPointList = new ArrayList<>();
		this.directionValueList = new ArrayList<>();
		for (RowData orientationInputData : orientationDataList) {
			 orientationData = orientationInputData.toString().split(";");
			 orientationPointList.add(new Point(orientationData[5], 
				Double.parseDouble(orientationData[7]), Double.parseDouble(orientationData[8])));
			 String[] directionData = orientationData[10].split("-"); 
			 directionValueList.add(Math.toRadians(Double.parseDouble(directionData[0]) + 
					Double.parseDouble(directionData[1]) / 60.0 + Double.parseDouble(directionData[2]) / 3600.0));
		}
	}
	
	private void calcAzimuthAndDistance(){
		azimuthAndDistanceList = new ArrayList<>();
		for (Point orientationPoint : orientationPointList) {
			azimuthAndDistanceList.add(new AzimuthAndDistance(standingPoint, orientationPoint));
		}
	}
	private void calcAverageOrientationAngleValue(boolean byDistance) {
		if( azimuthAndDistanceList == null || azimuthAndDistanceList.isEmpty() ||
				directionValueList == null || directionValueList.isEmpty() ) {
			return;
		}
		double sumOrientation = 0d;
		double sumP = 0d;
		for ( int i = 0; i < azimuthAndDistanceList.size(); i++) {
			
			double orientationValue = azimuthAndDistanceList.get(i).calcAzimuth() - directionValueList.get(i);
			
			if( byDistance ){
				sumOrientation += orientationValue * azimuthAndDistanceList.get(i).calcDistance() / 1000.0;
				sumP += azimuthAndDistanceList.get(i).calcDistance() / 1000.0;
			}
			else {
				sumOrientation += orientationValue;
			}
			
			OrientationData resultData = new OrientationData(directionValueList.get(i));
			resultData.setDirectionPointId(azimuthAndDistanceList.get(i).getPointB().getPointID());
			resultData.setAzimuthAngelValue(convertDataForAngleMinSecFormat(azimuthAndDistanceList.get(i).calcAzimuth()));
			resultData.setDistanceValue(azimuthAndDistanceList.get(i).calcDistance());
			resultData.setOrientationAngelValue(convertDataForAngleMinSecFormat(orientationValue));
			resultData.setDirectionAngelValue(convertDataForAngleMinSecFormat(directionValueList.get(i)));
			ORIENTATION_RESULT_DATA.add(resultData);
		}
		double aveOrientation;
		if( byDistance ) {
			aveOrientation = sumOrientation / sumP;
		}
		else {
			aveOrientation = sumOrientation / azimuthAndDistanceList.size();
		}
		this.aveOrientation = convertDataForAngleMinSecFormat(aveOrientation);
		for (OrientationData orientationData : ORIENTATION_RESULT_DATA) {
			orientationData.setOrientedDirectionValue(aveOrientation);
		}
	}
		
	public static String convertDataForAngleMinSecFormat(double inputData) {
		inputData = Math.toDegrees(inputData);
		int angle = Math.abs((int)(Math.abs(inputData)));
		int min = Math.abs((int) ((Math.abs(inputData) - angle) * 60));
		int sec = Math.abs((int) Math.round((Math.abs(inputData) - angle) * 3600 - (min * 60)));
		String minValue = 10 > min ? "0" + min : String.valueOf(min);
		String secValue = 10 > sec ? "0" + sec : String.valueOf(sec);
		return 0 > inputData ? "-" + angle + "-" + minValue + "-" + secValue :
										angle + "-" + minValue + "-" + secValue;
	} 
	
}
