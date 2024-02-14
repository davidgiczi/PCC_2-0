package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.util.Objects;

public class TheoreticalPointData {

	String rowNumber;
	private String theoreticalPointName;
	private String theoreticalPointX;
	private String theoreticalPointY;
	private String theoreticalPointZ;
	private String theoreticalPointSignName;
	private boolean isDeleted;
	
	
	public String getTheoreticalPointName() {
		return theoreticalPointName;
	}
	public void setTheoreticalPointName(String theoreticalPointName) {
		this.theoreticalPointName = theoreticalPointName;
	}
	public String getTheoreticalPointX() {
		return theoreticalPointX;
	}
	public void setTheoreticalPointX(String theoreticalPointX) {
		this.theoreticalPointX = theoreticalPointX;
	}
	public String getTheoreticalPointY() {
		return theoreticalPointY;
	}
	public void setTheoreticalPointY(String theoreticalPointY) {
		this.theoreticalPointY = theoreticalPointY;
	}
	public String getTheoreticalPointZ() {
		return theoreticalPointZ;
	}
	public void setTheoreticalPointZ(String theoreticalPointZ) {
		this.theoreticalPointZ = theoreticalPointZ;
	}
	public String getTheoreticalPointSignName() {
		return theoreticalPointSignName;
	}
	public void setTheoreticalPointSignName(String theoreticalPointSignName) {
		this.theoreticalPointSignName = theoreticalPointSignName;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(theoreticalPointName, theoreticalPointSignName, theoreticalPointX, theoreticalPointY,
				theoreticalPointZ);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TheoreticalPointData other = (TheoreticalPointData) obj;
		return Objects.equals(theoreticalPointName, other.theoreticalPointName)
				&& Objects.equals(theoreticalPointSignName, other.theoreticalPointSignName)
				&& Objects.equals(theoreticalPointX, other.theoreticalPointX)
				&& Objects.equals(theoreticalPointY, other.theoreticalPointY)
				&& Objects.equals(theoreticalPointZ, other.theoreticalPointZ);
	}
	@Override
	public String toString() {
		return theoreticalPointName + ";" + theoreticalPointY + ";" + theoreticalPointX
				+ ";" + theoreticalPointZ + ";" + (theoreticalPointSignName == null ? ";" : theoreticalPointSignName + ";");
	}
	
}
