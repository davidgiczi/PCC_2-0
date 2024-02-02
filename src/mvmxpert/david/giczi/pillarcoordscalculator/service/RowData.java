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
	private Double firstHrMeas;
	private Double secondHrMeas;
	private Double firstVrMeas;
	private Double secondVrMeas;
	private boolean isDeleted;
	
	public RowData() {
		this.measuredPointDataStore = new ArrayList<>();
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
	public void setHorizontalAngle(String horizontalAngle) {
		this.horizontalAngle = horizontalAngle.substring(0, horizontalAngle.indexOf('.')) 
				+ "-" + horizontalAngle.substring(horizontalAngle.indexOf('.') + 1, horizontalAngle.indexOf('.') + 3)
				+ "-" + horizontalAngle.substring(horizontalAngle.indexOf('.') + 3);
	}
	
	public String getVerticalAngle() {
		return verticalAngle;
	}
	public void setVerticalAngle(String verticalAngle) {
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
	public double getFirstHrMeas() {
		return firstHrMeas;
	}
	public void setFirstHrMeas(Double firstHrMeas) {
		this.firstHrMeas = firstHrMeas;
	}
	public double getSecondHrMeas() {
		return secondHrMeas;
	}
	public void setSecondHrMeas(Double secondHrMeas) {
		this.secondHrMeas = secondHrMeas;
	}
	public double getFirstVrMeas() {
		return firstVrMeas;
	}
	public void setFirstVrMeas(Double firstVrMeas) {
		this.firstVrMeas = firstVrMeas;
	}
	public double getSecondVrMeas() {
		return secondVrMeas;
	}
	public void setSecondVrMeas(Double secondVrMeas) {
		this.secondVrMeas = secondVrMeas;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
