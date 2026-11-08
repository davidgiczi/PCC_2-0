package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.text.DecimalFormat;

public class OrientationData {

	private String directionPointId;
	private String azimuthAngelValue;
	private String distanceValue;
	private String directionAngelValue;
	private String orientationAngelValue;
	private String orientedDirectionValue;
	private double directionValue;
	

	public OrientationData(double directionValue) {
		this.directionValue = directionValue;
	}

	public String getDirectionPointId() {
		return directionPointId;
	}

	public void setDirectionPointId(String directionPointId) {
		this.directionPointId = directionPointId;
	}



	public String getAzimuthAngelValue() {
		return azimuthAngelValue;
	}



	public void setAzimuthAngelValue(String azimuthAngelValue) {
		this.azimuthAngelValue = azimuthAngelValue;
	}



	public String getDistanceValue() {
		return distanceValue;
	}



	public void setDistanceValue(double distanceValue) {
		this.distanceValue = new DecimalFormat("0.000").format(distanceValue).replace(",", ".");
	}

	
	public String getDirectionAngelValue() {
		return directionAngelValue;
	}



	public void setDirectionAngelValue(String directionAngelValue) {
		this.directionAngelValue = directionAngelValue;
	}

	public String getOrientationAngelValue() {
		return orientationAngelValue;
	}



	public void setOrientationAngelValue(String orientationAngelValue) {
		this.orientationAngelValue = orientationAngelValue;
	}

	public String getOrientedDirectionValue() {
		return orientedDirectionValue;
	}

	
	public void setOrientedDirectionValue(double aveOrientationValue) {
		this.orientedDirectionValue = Orientation.convertDataForAngleMinSecFormat(aveOrientationValue + directionValue);
	}

	
	@Override
	public String toString() {
		return "OrientationData[" + directionPointId + ", " + azimuthAngelValue
				+ ", " + distanceValue + ", " + directionAngelValue + ", " + orientationAngelValue 
				+ ", " + orientedDirectionValue + "]";
	}
	
}
