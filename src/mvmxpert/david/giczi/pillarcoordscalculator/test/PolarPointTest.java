package mvmxpert.david.giczi.pillarcoordscalculator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.PolarPoint;

public class PolarPointTest {

	
	private PolarPoint calculator;
	
	@Test
	public void polarPointCalculator1() {
		calculator = new PolarPoint(new Point("A", 10, 10), 10, Math.PI / 2, "B");
		assertEquals(20, calculator.calcPolarPoint().getX_coord());
		assertEquals(10, calculator.calcPolarPoint().getY_coord());
	}
	
	@Test
	public void polarPointCalculator2() {
		calculator = new PolarPoint(new Point("A", 10, 10), Math.sqrt(200), Math.PI / 4, "B");
		assertEquals(20, calculator.calcPolarPoint().getX_coord());
		assertEquals(20, calculator.calcPolarPoint().getY_coord());
	}
	
	@Test
	public void polarPointCalculator3() {
		calculator = new PolarPoint(new Point("A", 10, 10), 10, 0, "B");
		assertEquals(10, calculator.calcPolarPoint().getX_coord());
		assertEquals(20, calculator.calcPolarPoint().getY_coord());
	}
	
	@Test
	public void polarPointCalculator4() {
		calculator = new PolarPoint(new Point("A", 20, 20), Math.sqrt(200), 5 * Math.PI / 4, "B");
		assertEquals(10, calculator.calcPolarPoint().getX_coord());
		assertEquals(10, calculator.calcPolarPoint().getY_coord());
	}
	
}
