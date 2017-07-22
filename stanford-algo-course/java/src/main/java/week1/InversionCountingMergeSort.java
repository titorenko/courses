package week1;

import java.util.Arrays;

/**
 * More or less direct implementation of sort from week1 to compare
 * java and go performance
 */
public class InversionCountingMergeSort {
	
	private static final int INSERTION_SORT_CUTOFF = 48;

	/** Sort integer array in place. Function runs in n*log(n) time and n space.
	 @return number of inversions in input array */
	public long mergeSortCountingInversions(int[] input) {
		int[] buffer = Arrays.copyOf(input, input.length);
		return mergeSort(input, 0, input.length, buffer);
	}
	
	long mergeSort(int[] input, int start, int end, int[] buffer) {
		final int size = end - start;
		if (size < 2) return 0;
		if (size < INSERTION_SORT_CUTOFF) return insertionSort(input, start, end);
		final int mid = (start + end) >>> 1;
		long leftInv = mergeSort(buffer, start, mid, input);
		long rightInv = mergeSort(buffer, mid, end, input);
		long splitInv = merge(buffer, start, mid, end, input);
		return leftInv + rightInv + splitInv;
	}

	private long insertionSort(final int[] input, final int start, final int end) {
		long inversions = 0;
		for (int i = start+1; i < end; i++) {
			final int x = input[i];
			int j = i;
			while(j > start && input[j-1] > x) {
				input[j] = input[j-1];
				inversions++;
				j--;
			}
			input[j] = x;
		}
		return inversions;
	}

	long merge(final int[] src, final int start, final int mid, final int end, final int[] dest) {
		int leftIdx = start;
		int rightIdx = mid;
		long inversions = 0;
		for (int idx = start; idx < end;) {
			if (src[leftIdx] < src[rightIdx]) {
				dest[idx] = src[leftIdx];
				leftIdx++;
			} else {
				dest[idx] = src[rightIdx];
				rightIdx++;
				inversions += mid - leftIdx;
			}
			idx++;
			if (leftIdx >= mid){
				System.arraycopy(src, rightIdx, dest, idx, end-rightIdx);
				break;
			}
			if (rightIdx >= end) {
				System.arraycopy(src, leftIdx, dest, idx, mid-leftIdx);
				break;
			}
		}
		return inversions;
	}
}