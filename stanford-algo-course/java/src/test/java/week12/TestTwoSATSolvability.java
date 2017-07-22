package week12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestTwoSATSolvability {
	
	@Test
	public void testSolvable() {
		TwoSATProblem ts = InputParser.parseResource("/2satTest1.txt");
		assertTrue(new TwoSATSolverViaSCC(ts).isSolvable());
	}
	
	@Test
	public void testNotSolvable() {
		TwoSATProblem ts = InputParser.parseResource("/2satTest2.txt");
		assertFalse(new TwoSATSolverViaSCC(ts).isSolvable());
	}
	
	@Test
	public void testSolvablePapadi() {
		TwoSATProblem ts = InputParser.parseResource("/2satTest1.txt");
		assertTrue(new PapadimitriouTwoSATSolver(ts).isSolvable());
	}
	
	@Test
	public void testNotSolvablePapadi() {
		TwoSATProblem ts = InputParser.parseResource("/2satTest2.txt");
		assertFalse(new PapadimitriouTwoSATSolver(ts).isSolvable());
	}
}
