package week6;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.IntStream;


/**
 * The goal of this problem is to implement the "Median Maintenance" algorithm (covered in the Week 5 
 * lecture on heap applications). The text file contains a list of the integers from 1 to 10000 in 
 * unsorted order; you should treat this as a stream of numbers, arriving one by one. Letting xi denote 
 * the ith number of the file, the kth median mk is defined as the median of the numbers x1,…,xk. 
 * (So, if k is odd, then mk is ((k+1)/2)th smallest number among x1,…,xk; if k is even, then mk is the 
 * (k/2)th smallest number among x1,…,xk.)
 * In the box below you should type the sum of these 10000 medians, modulo 10000 (i.e., only the last 4 digits). 
 * That is, you should compute (m1+m2+m3+⋯+m10000) mod 10000.
 */
public class MedianMaintenance {
	PriorityQueue<Integer> left = new PriorityQueue<>(new Comparator<Integer>() {
		@Override
		public int compare(Integer i1, Integer i2) {
			return i2 - i1;
		}
	});
	PriorityQueue<Integer> right = new PriorityQueue<>();
	
	void add(int i) {
		if (left.size() == 0 || left.peek() > i) {
			left.add(i);
		} else {
			right.add(i);
		}
		rebalance();
	}
	
	private void rebalance() {
		if (Math.abs(left.size() - right.size()) <= 1) return;
		if (left.size() < right.size()) {
			left.add(right.poll());
		} else {
			right.add(left.poll());
		}
	}

	int median() {
		int k = size();
		int idx = k % 2 == 0 ? k/2 : (k+1) / 2;
		if (idx == left.size()) {
			return left.peek();
		} else if (idx == left.size()+1) {
			return right.peek();
		} else {
			throw new IllegalStateException("Priority queue are unbalanced, this is unexpected: "+left.size()+", "+right.size());
		}
	}
	
	public int nextMedian(int i) {
		add(i);
		return median();
	}
	
	int size() {
		return left.size() + right.size();
	}
	
	/**
	 * @return sum of all medians, modulo 10000 
	 */
	public int compute(IntStream ints) {
		IntStream medians = ints.map(this::nextMedian);
		return medians.reduce(0, (sum, i) -> (sum +i) % 10000);
	}
}