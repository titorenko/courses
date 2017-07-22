package optimized;

import java.util.Arrays;
import java.util.Random;

import com.google.common.base.Preconditions;

public class InPlaceMergeSort {
    
    private static final int INSERTION_SORT_CUTOFF = 32;
    
    public void sort(int[] input) {
        mergeSort(input, 0, input.length);
    }

    void mergeSort(int[] input, int start, int end) {
        final int size = end - start;
        if (size < 2) return;
        if (size < INSERTION_SORT_CUTOFF) {
            InsertionSort.sort(input, start, end);
        } else {
            final int mid = (start + end) >>> 1;
            mergeSort(input, start, mid);
            mergeSort(input, mid, end);
            merge(input, start, mid, end);
        }
    }

    void merge(int[] input, final int start, final int mid, final int end) {
    /*    int[] prefix = new int[start];
        int[] left = new int[mid - start];
        int[] right = new int[end - mid];
        System.arraycopy(input, 0, prefix, 0, prefix.length);
        System.arraycopy(input, start, left, 0, left.length);
        System.arraycopy(input, mid, right, 0, right.length);
        System.out.println("Prefix: "+Arrays.toString(prefix));
        System.out.println("Merging: "+Arrays.toString(left)+" with "+Arrays.toString(right));
        System.out.println("----------------------");*/
        int i;
        for (i = start; i < mid && input[i] <= input[mid]; i++);
        
        int k = mid;
        int j = mid;
        while(i < mid) {
            //System.out.println(Arrays.toString(input)+", i="+i+", j="+j+", k="+k);
            if(k >= end || input[j] < input[k]) {
                swap(input, i, j);
                i++;
                j++;
                if (j >= k) j = mid;
            } else {
                swap(input, i, k);
                i++;
                k++;
            }                
        }
        if (j!=mid) {
            InsertionSort.sort(input, mid, Math.min(k, end-1));
        }
        /*int last = Math.min(k-1, end-1);
        System.arraycopy(input, mid+1, input, mid, last-mid);*/
        if (mid<k) {
            merge(input, mid, Math.min(k, end-1), end);
        }
    }

    private void swap(int[] input, int i, int j) {
        int tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;
    }
    
    static Random r = new Random();
    
    /*public static void main(String[] args) {
        int t = 10000;
        int b = 20;
        for (int n=2; n<51; n++) {
            System.out.println("Testing n="+n);
            for (int i = 0; i < t; i++) {
                int[] left = generateSorted(n, b);
                int[] right = generateSorted(n, b);
                int[] toMerge = new int[n*2];
                System.arraycopy(left, 0, toMerge, 0, n);
                System.arraycopy(right, 0, toMerge, n, n);
                int[] clone = toMerge.clone();
                new InPlaceMergeSort().merge(toMerge, 0, n, n*2);
                if (!Sorts.isSorted(toMerge)) {
                    throw new RuntimeException("Failed on "+Arrays.toString(clone));
                }
            }
        }
    }*/
    
    public static void main(String[] args) {
        int[] toMerge = {2, 9, 10, 14, 15, 17, 0, 2, 3, 11, 11, 17};
        new InPlaceMergeSort().merge(toMerge, 0, toMerge.length / 2, toMerge.length);
        System.out.println("Result: "+Arrays.toString(toMerge));
        Preconditions.checkArgument(Sorts.isSorted(toMerge));
    }
  /*  
    public static void main(String[] args) {
        int nInputs = 15;
        int[][] toSort = new int[nInputs][];
        int n = 100;
        for (int i = 0; i < nInputs; i++) {
            toSort[i] = generate(n, n*2);
            n = (int) (n*1.5);
        }
        int nTries = 3;
        InPlaceMergeSort sorter = new InPlaceMergeSort();
        //MergeSort sorter = new MergeSort();
        //InsertionSort sorter = new InsertionSort();
        double[] times = new double[toSort.length];
        double[] sizes = new double[toSort.length];
        for (int t = 0; t < nTries; t++) {
            System.out.println("----------- Try "+t);
            int[][] clone = toSort.clone();
            for (int i = 0; i < clone.length; i++) {
                clone[i] = toSort[i].clone();
            }
            for (int k = 0; k < clone.length; k++) {
                long start = System.nanoTime();
                sorter.sort(clone[k]);
                long duration = System.nanoTime() - start;
                times[k] = duration / 1000000.0;
                sizes[k] = clone[k].length;
            }
        }
        System.out.println("Sizes: "+Arrays.toString(sizes));
        System.out.println("Times: "+Arrays.toString(times));
    }*/


   /* private static int[] generateSorted(int n, int b) {
        int[] result = generate(n, b);
        Arrays.sort(result);
        return result;
    }

    private static int[] generate(int n, int b) {
        int[] result = new int[n];
        for (int i = 0; i < result.length; i++) {
            result[i] = r.nextInt(b);
        }
        return result;
    }*/

}