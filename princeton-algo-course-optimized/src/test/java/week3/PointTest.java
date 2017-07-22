package week3;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
	private static double PRECISION = 1E-15;

	@Test
	public void testSlopeOnHorizontal() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(2, 1);
		assertEquals(0, p1.slopeTo(p2), PRECISION);
		assertEquals(0, p2.slopeTo(p1), PRECISION);
	}
	
	@Test
	public void testSlopeOnVanillaCase() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(2, 2);
		assertEquals(1.0, p1.slopeTo(p2), PRECISION);
		assertEquals(1.0, p2.slopeTo(p1), PRECISION);
	}
	
	@Test
	public void testSlopeOnVertical() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(1, 2);
		assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2), PRECISION);
		assertEquals(Double.POSITIVE_INFINITY, p2.slopeTo(p1), PRECISION);
	}
	
	@Test
	public void testSlopeDegenerate() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(1, 1);
		assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(p2), PRECISION);
	}
}
