package week6;

import gnu.trove.set.hash.TLongHashSet;

import com.google.common.base.Preconditions;
import common.InputData;

/**
The goal of this problem is to implement a variant of the 2-SUM algorithm 
(covered in the Week 6 lecture on hash table applications).

The file contains 1 million integers, both positive and negative (there might be some repetitions!).
This is your array of integers, with the ith row of the file specifying the ith entry of the array.

Your task is to compute the number of target values t in the interval [-10000,10000] (inclusive) such that 
there are distinct numbers x,y in the input file that satisfy x+y=t. 
(NOTE: ensuring distinctness requires a one-line addition to the algorithm from lecture.)
*/
public class TwoSumSolver {
	private final TLongHashSet numbers;
	private final int lowerBound;
	private final int upperBound;

	public TwoSumSolver(long[] input, int lowerBound, int upperBound) {
		Preconditions.checkArgument(upperBound >= lowerBound);
		//this.numbers = new LongBag(input.length * 11);
		this.numbers = new TLongHashSet(input.length * 11);
		numbers.addAll(input);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	int distinctSize() {
		return numbers.size();
	}
	
	public int solve() {
		int nSolutions = 0;
		final long[] keys = numbers.toArray();
		for (int t=lowerBound; t<=upperBound; t++) {
			for (long x : keys) {
				final long y = t - x;
				if (x < y && numbers.contains(y)) {
					nSolutions++;
					break;
				}
			}
		}
		return nSolutions;
	}
	
	public static void main(String[] args) {
		int t = 10000;
		TwoSumSolver solver = new TwoSumSolver(InputData.week6a(), -t, t);
		long start = System.currentTimeMillis();
		int result = solver.solve();
		long duration = System.currentTimeMillis() - start;
		System.out.println("\nResult: "+result+", done in "+duration);//427
	}
}