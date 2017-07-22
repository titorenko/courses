package week6;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.LongStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TwoSumSolverIntegrationTest {
	private static final int SEED = 0;
	private static final int SPREAD_FACTOR = 5;
	
	@Parameters(name = "Size {0} and range {1}")
	public static Iterable<Integer[]> sizes() {
		Integer[][] sizes = new Integer[][] {
			{10, 100},
			{100, 10000},
			{1000, 10000},
			{1000, 100},
			{10000, 5}
		};
		return Arrays.asList(sizes);
	}

	private final long[] input;
	private final int t;
	
	public TwoSumSolverIntegrationTest(int n, int t) {
		this.input = generateRandomLongArray(n, t);
		this.t = t; 
	}

	private long[] generateRandomLongArray(int size, int t) {
		Random random = new Random(SEED);
		LongStream stream = random.longs(-t*SPREAD_FACTOR, t*SPREAD_FACTOR);
		return stream.limit(size).toArray();
	}
	
	@Test 
	public void testThatHashAndFastMethodsAreSame() {
		TwoSumOptimizedSolver solver1 = new TwoSumOptimizedSolver(input, -t, t);
		TwoSumSolver solver2 = new TwoSumSolver(input, -t, t);
		assertEquals(solver1.solve(), solver2.solve());
	}
}