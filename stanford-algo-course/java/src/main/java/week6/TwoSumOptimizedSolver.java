package week6;

import java.util.Arrays;

import com.google.common.base.Preconditions;

public class TwoSumOptimizedSolver {
	
	private final long[] numbers;
	private final boolean[] solutions;
	private int nSolutions;
	private final int lowerBound;
	private final int upperBound;

	
	/**
	 * Find all count of all such t's that there exist x,y from input such that x + y = t, x != y and lowBound <= t <= upperBound 
	 */
	public TwoSumOptimizedSolver(long[] input, int lowerBound, int upperBound) {
		Preconditions.checkArgument(upperBound >= lowerBound);
		final long[] copy = Arrays.copyOf(input, input.length);
		Arrays.parallelSort(copy);
		this.numbers = copy;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.solutions = new boolean[upperBound - lowerBound +1];
		this.nSolutions = 0;
	}

	public int solve() {
		final long[] numbers = this.numbers;
		
		int right = numbers.length - 1;
		for (int left = 0; left < numbers.length; left++) {
			final long x = numbers[left];
			for (int j = right+1; j < numbers.length; j++) {
				final long y = numbers[j];
				final long sum = x + y; 
				if (sum > upperBound) {
					break;
				} else if (sum >= lowerBound && x != y) {
					mark(sum);
				}
			}
			for (int j = right; j > left; j--) {
				final long y = numbers[j];
				final long sum = x + y;
				if (sum < lowerBound) {
					right = j;
					break;
				} else if (sum <= upperBound && x != y)  {
					mark(sum);
				} 
			}
		}
		return nSolutions;
	}
	
	private void mark(long sum) {
		final int idx = (int) (sum-lowerBound);
		if (!solutions[idx]) {
			solutions[idx] = true;
			nSolutions++;
		}
	}
}