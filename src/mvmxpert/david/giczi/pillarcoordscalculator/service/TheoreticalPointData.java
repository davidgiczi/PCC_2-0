package mvmxpert.david.giczi.pillarcoordscalculator.service;


public class TheoreticalPointData {

	private String theoreticalPointName;
	private String theoreticalPointX;
	private String theoreticalPointY;
	private String theoreticalPointZ;
	private String theoreticalPointSignName;
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
	
	@Override
	public String toString() {
		return "TheoreticalPointData [theoreticalPointName=" + theoreticalPointName + ", theoreticalPointX="
				+ theoreticalPointX + ", theoreticalPointY=" + theoreticalPointY + ", theoreticalPointZ="
				+ theoreticalPointZ + ", theoreticalPointSignName=" + theoreticalPointSignName + "]";
	}
	
}
