package week6;

import org.junit.Test;

import common.InputData;

import static org.junit.Assert.*;

public class TwoSumSolverTest {
	
	private long[] input = InputData.week6a();
	
	@Test
	public void testSize() {
		TwoSumSolver solver = new TwoSumSolver(input, -1, 1);
		assertTrue(solver.distinctSize() <= 1_000_000);
	}
	
	@Test
	public void testOptimizedSolver() {
		TwoSumOptimizedSolver solver = new TwoSumOptimizedSolver(input, -10000, 10000);
		assertEquals(427, solver.solve());
	}
	
	@Test
	public void testSolverSameOnSmallTRange() {
		TwoSumOptimizedSolver solver1 = new TwoSumOptimizedSolver(input, -1, 1);
		TwoSumSolver solver2 = new TwoSumSolver(input, -1, 1);
		assertEquals(solver1.solve(), solver2.solve());
	}
}