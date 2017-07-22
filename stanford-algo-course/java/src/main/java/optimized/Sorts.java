package optimized;

import week1.InversionCountingMergeSort;

public class Sorts {

    private final static InversionCountingMergeSort invMergeSort = new InversionCountingMergeSort();
    private final static MergeSort mergeSort = new MergeSort();
    private final static ParallelMergeSort parallelMergeSort = new ParallelMergeSort();
    private final static InPlaceMergeSort inPlaceMergeSort = new InPlaceMergeSort();
    private final static CopyOfInPlaceMergeSort inPlaceMergeSort2 = new CopyOfInPlaceMergeSort();

    public static void mergeSort(int[] input) {
        mergeSort.sort(input);
    }

    public static void inPlaceMergeSort(int[] input) {
        inPlaceMergeSort.sort(input);
    }

    public static void inPlaceMergeSort2(int[] input) {
        inPlaceMergeSort2.sort(input);
    }

    public static void parallelMergeSort(int[] input) {
        parallelMergeSort.sort(input);
    }

    public static long sortAndCountInversions(int[] input) {
        return invMergeSort.mergeSortCountingInversions(input);
    }

    public static boolean isSorted(final int[] input) {
        return isSorted(input, 0, input.length);
    }

	public static boolean isSorted(final int[] input, final int from, final int to) {
		for (int i = from; i < to-1; i++) {
            if (input[i] > input[i + 1])
                return false;
        }
        return true;
	}
}
