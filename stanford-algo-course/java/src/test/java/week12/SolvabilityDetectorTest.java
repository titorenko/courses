package week12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import week12.TwoSATSolverViaSCC.SolvabilityDetector;

public class SolvabilityDetectorTest {
	
	int n = 100;
	
	@Test
	public void testPositiveCase1() {
		SolvabilityDetector detector = new TwoSATSolverViaSCC.SolvabilityDetector(n);
		detector.componentStart();
		detector.visit(-51+n);
		detector.visit(10+n);
		detector.visit(11+n);
		detector.visit(51+n);
		detector.componentEnd();
		assertFalse(detector.isSolvable());
	}
	
	@Test
	public void testNegativeCase1() {
		SolvabilityDetector detector = new TwoSATSolverViaSCC.SolvabilityDetector(n);
		detector.componentStart();
		detector.visit(-50+n);
		detector.visit(-11+n);
		detector.visit(10+n);
		detector.visit(51+n);
		detector.componentEnd();
		assertTrue(detector.isSolvable());
	}
}
