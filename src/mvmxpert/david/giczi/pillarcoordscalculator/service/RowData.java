package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.util.ArrayList;
import java.util.List;

public class RowData {
	
	private String rowNumber;
	private String standingPointName;
	private String standingPointY;
	private String standingPointX;
	private String standingPointZ;
	private String totalStationHeight;
	private String measuredPointName;
	private String measuredPointY;
	private String measuredPointX;
	private String measuredPointZ;
	private String measuredPointSign;
	private String horizontalAngle;
	private String verticalAngle;
	private String horizontalDistance;
	private String measuredPointSignHeight;
	private String date;
	private String time;
	private List<RowData> measuredPointDataStore;
	private TheoreticalPointData theoreticalPointData;
	private String firstHrMeas;
	private String firstVrMeas;
	private String firstDistValue;
	private boolean isDeleted;
	
	public RowData() {
		this.measuredPointDataStore = new ArrayList<>();
	}
	
	public String getMediumHrValue(){
		
		String[] firstHrData = firstHrMeas.split("-");
		double firstValue = Integer.parseInt(firstHrData[0]) + 
				Integer.parseInt(firstHrData[1]) / 60.0 + 
				Integer.parseInt(firstHrData[2]) / 3600.0;
		String[] secondHrData = horizontalAngle.split("-");
		double secondValue = Integer.parseInt(secondHrData[0]) + 
				Integer.parseInt(secondHrData[1]) / 60.0 + 
				Integer.parseInt(secondHrData[2]) / 3600.0;
		secondValue = secondValue > 180 ? secondValue - 180 : secondValue + 180;
		double mediumHrValue = (firstValue + secondValue) / 2.0;
		
		return convertToAngleMinSecFormat(mediumHrValue);
	}
	
	public String getMediumVrValue(){
		
		String[] firstVrData = firstVrMeas.split("-");
		double firstValue = Integer.parseInt(firstVrData[0]) + 
				Integer.parseInt(firstVrData[1]) / 60.0 + 
				Integer.parseInt(firstVrData[2]) / 3600.0;
		String[] secondVrData = verticalAngle.split("-");
		double secondValue = Integer.parseInt(secondVrData[0]) + 
				Integer.parseInt(secondVrData[1]) / 60.0 + 
				Integer.parseInt(secondVrData[2]) / 3600.0;
		double mediumVrValue = (firstValue - secondValue + 360) / 2.0;
		mediumVrValue = 0 > mediumVrValue ? mediumVrValue + 360 : mediumVrValue;
		return convertToAngleMinSecFormat(mediumVrValue);
	}
	
	public String getMediumDistanceValue() {
		double firstValue;
		double secondValue;
		try {
		firstValue = Double.parseDouble(horizontalDistance);
		secondValue = Double.parseDouble(firstDistValue);
		}catch (NumberFormatException e) {
			return "0.000";
		}
		
		double mediumValue = (firstValue + secondValue) / 2.0;
		return String.format("%.3f" ,mediumValue).replace(",", ".");
	}

	private String convertToAngleMinSecFormat(double angleValue) {
		
		int angle = (int) angleValue;
		int min = (int) ((angleValue - angle) * 60);
		int sec = (int) ((angleValue - angle) * 3600 - min * 60);
		
		return angle + "-" + (min < 10 ?  "0" + min : min) + "-" + (sec < 10 ?  "0" + sec : sec);
	}
		
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getStandingPointName() {
		return standingPointName;
	}
	public void setStandingPointName(String standingPointName) {
		this.standingPointName = standingPointName;
	}
	public String getStandingPointY() {
		return standingPointY;
	}
	public void setStandingPointY(String standingPointY) {
		this.standingPointY = standingPointY;
	}
	public String getStandingPointX() {
		return standingPointX;
	}
	public void setStandingPointX(String standingPointX) {
		this.standingPointX = standingPointX;
	}
	public String getStandingPointZ() {
		return standingPointZ;
	}
	public void setStandingPointZ(String standingPointZ) {
		this.standingPointZ = standingPointZ;
	}
	public String getTotalStationHeight() {
		return totalStationHeight;
	}
	public void setTotalStationHeight(String totalStationHeight) {
		this.totalStationHeight = totalStationHeight;
	}

	public String getMeasuredPointName() {
		return measuredPointName;
	}
	public void setMeasuredPointName(String measuredPointName) {
		this.measuredPointName = measuredPointName;
	}
	public String getMeasuredPointY() {
		return measuredPointY;
	}
	public void setMeasuredPointY(String measuredPointY) {
		this.measuredPointY = measuredPointY;
	}
	public String getMeasuredPointX() {
		return measuredPointX;
	}
	public void setMeasuredPointX(String measuredPointX) {
		this.measuredPointX = measuredPointX;
	}
	public String getMeasuredPointZ() {
		return measuredPointZ;
	}
	public void setMeasuredPointZ(String measuredPointZ) {
		this.measuredPointZ = measuredPointZ;
	}
	public String getMeasuredPointSign() {
		return measuredPointSign;
	}
	public void setMeasuredPointSign(String measuredPointSign) {
		this.measuredPointSign = measuredPointSign;
	}
	public String getHorizontalAngle() {
		return horizontalAngle;
	}
	
	public void setHorizontalAngleFromDisplayer(String horizontalAngle) {
		this.horizontalAngle = horizontalAngle;
	}
	
	public void setHorizontalAngle(String horizontalAngle) {
		if( horizontalAngle.contains("-")) {
			this.horizontalAngle = horizontalAngle;
			return;
		}
		this.horizontalAngle = horizontalAngle.substring(0, horizontalAngle.indexOf('.')) 
				+ "-" + horizontalAngle.substring(horizontalAngle.indexOf('.') + 1, horizontalAngle.indexOf('.') + 3)
				+ "-" + horizontalAngle.substring(horizontalAngle.indexOf('.') + 3);
	}
	
	public String getVerticalAngle() {
		return verticalAngle;
	}
	
	public void setVerticalAngleFromDisplayer(String verticalAngle) {
		this.verticalAngle = verticalAngle;
	}
	
	public void setVerticalAngle(String verticalAngle) {
		if( verticalAngle.contains("-") ) {
			this.verticalAngle = verticalAngle;
			return;
		}
		this.verticalAngle = verticalAngle.substring(0, verticalAngle.indexOf('.')) 
				+ "-" + verticalAngle.substring(verticalAngle.indexOf('.') + 1, verticalAngle.indexOf('.') + 3)
				+ "-" + verticalAngle.substring(verticalAngle.indexOf('.') + 3);
	}
	public String getHorizontalDistance() {
		return horizontalDistance;
	}
	public void setHorizontalDistance(String horizontalDistance) {
		this.horizontalDistance = horizontalDistance;
	}
	
	public String getMeasuredPointSignHeight() {
		return measuredPointSignHeight;
	}
	public void setMeasuredPointSignHeight(String measuredPointSignHeight) {
		this.measuredPointSignHeight = measuredPointSignHeight;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		if( date.contains(".") ) {
			this.date = date;
			return;
		}
		String[] dateData = date.split("/");
		String month = Integer.parseInt(dateData[0]) < 10 ? "0" + dateData[0] : dateData[0];
		String day = Integer.parseInt(dateData[1]) < 10 ? "0" + dateData[1] : dateData[1];
		this.date = dateData[2] + "." + month + "." + day + ".";
	}
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public List<RowData> getMeasuredPointDataStore() {
		return measuredPointDataStore;
	}
	public void setMeasuredPointDataStore(List<RowData> measuredPointDataStore) {
		this.measuredPointDataStore = measuredPointDataStore;
	}
	public TheoreticalPointData getTheoreticalPointData() {
		return theoreticalPointData;
	}
	public void setTheoreticalPointData(TheoreticalPointData theoreticalPointData) {
		this.theoreticalPointData = theoreticalPointData;
	}
	
	public String getFirstHrMeas() {
		return firstHrMeas;
	}

	public void setFirstHrMeas(String firstHrMeas) {
		this.firstHrMeas = firstHrMeas;
	}

	public String getFirstVrMeas() {
		return firstVrMeas;
	}

	public void setFirstVrMeas(String firstVrMeas) {
		this.firstVrMeas = firstVrMeas;
	}

	public String getFirstDistValue() {
		return firstDistValue;
	}

	public void setFirstDistValue(String firstDistValue) {
		this.firstDistValue = firstDistValue;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	

	@Override
	public String toString() {
		return standingPointName + ";" + standingPointY + ";" + standingPointX + ";" + standingPointZ + ";"+ totalStationHeight + ";" 
				+  measuredPointName + ";" + measuredPointSign + ";" + measuredPointY + ";" + measuredPointX + ";" + measuredPointZ + ";" + 
				   horizontalAngle + ";" + verticalAngle + ";" + horizontalDistance + ";" + measuredPointSignHeight + 
				   ";;" + date + ";" + time + ";";
	}

}
