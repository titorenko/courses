package optimized;

import java.util.Arrays;

class MergeSort {
	
	private static final int INSERTION_SORT_CUTOFF = 32;

	/** Sort integer array in place. Function runs in n*log(n) time and n space.*/
	public void sort(int[] input) {
		if (Sorts.isSorted(input)) return;
		int[] buffer = Arrays.copyOf(input, input.length);
		mergeSort(input, 0, input.length, buffer);
	}
	
	void mergeSort(int[] input, int start, int end, int[] buffer) {
		final int size = end - start;
		if (size < 2) return;
		if (size < INSERTION_SORT_CUTOFF) {
			InsertionSort.sort(input, start, end);
		} else {
			final int mid = (start + end) >>> 1;
			mergeSort(buffer, start, mid, input);
			mergeSort(buffer, mid, end, input);
			merge(buffer, start, mid, end, input);
		}
	}

	void merge(final int[] src, final int start, final int mid, final int end, final int[] dest) {
		if (src[mid-1] < src[mid]) {//optimized merge when already sorted
			System.arraycopy(src, start, dest, start, end-start);
			return;
		}
		int leftIdx = start;
		int rightIdx = mid;
		for (int idx = start; idx < end; idx++) {
			dest[idx] = src[leftIdx] < src[rightIdx] ? src[leftIdx++] : src[rightIdx++]; 
			if (leftIdx >= mid){
				System.arraycopy(src, rightIdx, dest, idx+1, end-rightIdx);
				return;
			}
			if (rightIdx >= end){
				System.arraycopy(src, leftIdx, dest, idx+1, mid-leftIdx);
				return;
			}
		}
	}
}