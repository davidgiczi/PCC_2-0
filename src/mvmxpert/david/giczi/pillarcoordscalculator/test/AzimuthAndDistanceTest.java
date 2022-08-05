package mvmxpert.david.giczi.pillarcoordscalculator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.AzimuthAndDistance;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.Point;

public class AzimuthAndDistanceTest {

	
	private AzimuthAndDistance calculator;
	

	@Test
	public void distanceCalculator1(){
		calculator = new AzimuthAndDistance(new Point("A", 10, 10), new Point("B", 10, 100));
		assertEquals(90.0, calculator.calcDistance());
	}
	
	@Test
	public void distanceCalculator2(){
		calculator = new AzimuthAndDistance(new Point("A", 10.56, 10.52), new Point("B", 3.98, 100.45));
		assertEquals(90.1704014630078, calculator.calcDistance());
	}
	
	@Test
	public void distanceCalculator3(){
		calculator = new AzimuthAndDistance(new Point("A", 100.725, 10.5258), new Point("B", 13.9876, 100.459));
		assertEquals(124.94541616642044, calculator.calcDistance());
	}
	
	@Test
	public void distanceCalculator4(){
		calculator = new AzimuthAndDistance(new Point("A", 10.25, 10.5), new Point("B", 10.25, 10.5));
		assertEquals(0.0, calculator.calcDistance());
	}
	
	@Test
	public void azimuthCalculator1() {
		calculator = new AzimuthAndDistance(new Point("A", 10, 10),new Point("B", 10, 20));
		assertEquals(0.0, calculator.calcAzimuth());
	}
	
	@Test
	public void azimuthCalculator2() {
		calculator = new AzimuthAndDistance(new Point("A", 10, 10),new Point("B", 20, 20));
		assertEquals(Math.PI / 4, calculator.calcAzimuth());
	}
	
	@Test
	public void azimuthCalculator3() {
		calculator = new AzimuthAndDistance(new Point("A", 10, 20),new Point("B", 10, 10));
		assertEquals(Math.PI, calculator.calcAzimuth());
	}
	
	@Test
	public void azimuthCalculator4() {
		calculator = new AzimuthAndDistance(new Point("A", 20, 10),new Point("B", 10, 10));
		assertEquals(Double.NaN, calculator.calcAzimuth());
	}
	
	@Test
	public void azimuthCalculator5() {
		calculator = new AzimuthAndDistance(new Point("A", 20, 10),new Point("B", 10, 20));
		assertEquals(7 * Math.PI / 4, calculator.calcAzimuth());
	}
	
	@Test
	public void azimuthCalculator6() {
		calculator = new AzimuthAndDistance(new Point("A", 20, 20),new Point("B", 10, 10));
		assertEquals(5 * Math.PI / 4, calculator.calcAzimuth());
	}
}
