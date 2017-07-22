package optimized;

import java.util.Arrays;
import java.util.Random;

public class CopyOfInPlaceMergeSort {
    
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
        if (end - start < 10) {
            InsertionSort.sort(input, start, end);
            return;
        }
      /*  int[] prefix = new int[start];
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
        //now all elements in left part will be bigger when mid
        int k = mid;
        while(i < mid) {
            if(k >= end || input[mid] < input[k]) {
                //we need maintain invariant of having input[mid] smallest element of right half
                //part form mid to k is to be consider as binary heap
                int tmp = input[i];
                input[i] = input[mid];
                //restore heap property
                bubleDown(0, tmp, k-mid, mid, input);
                i++;
            } else {
                swap(input, i, k);
                i++;
                k++;
            }                
        }
        heapsort(input, mid, k-1);
        reverse(input, mid, k-1);
        if (mid<k) {
            merge(input, mid, Math.min(k, end-1), end);
        }
    }
    private void reverse(int[] input, int mid, int k) {//TODO remove this step by alternating merge code
        while(mid < k) {
            swap(input, mid, k);
            mid++;
            k--;
        }
        
    }

    private void heapsort(int[] input, int start, int end) {
        while(end >= start) {
            int tmp = input[end];
            input[end] = input[start];
            bubleDown(0, tmp, end-start, start, input);
            end--;
        }
    }

    private void bubleDown(int k, final int v, int size, int mid, int[] elements) {
        final int half = size >>> 1; // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1;//find smaller child index & element 
            final int right = child + 1;
            int c = elements[mid+child];
            if (right < size && c > elements[mid+right]) {
                c = elements[mid+(child = right)];
            }
            if (v < c) break;
            elements[mid+k] = c;
            k = child;
        }
        elements[mid+k] = v;
    }

    private void swap(int[] input, int i, int j) {
        int tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;
    }
    
    static Random r = new Random(0);
    
    public static void main(String[] args) {
        int t = 1;
        int b = 200;
        for (int n=50; n<51; n++) {
            System.out.println("Testing n="+n);
            for (int i = 0; i < t; i++) {
                int[] left = generateSorted(n, b);
                int[] right = generateSorted(n, b);
                int[] toMerge = new int[n*2];
                System.arraycopy(left, 0, toMerge, 0, n);
                System.arraycopy(right, 0, toMerge, n, n);
                int[] clone = toMerge.clone();
                new CopyOfInPlaceMergeSort().merge(toMerge, 0, n, n*2);
                if (!Sorts.isSorted(toMerge)) {
                    throw new RuntimeException("Failed on "+Arrays.toString(clone));
                }
            }
        }
        System.out.println("All OK");
    }
    
  /*  public static void main(String[] args) {
        int[] toMerge = {9, 16, 16, 17, 1, 3, 4, 19};
        new CopyOfInPlaceMergeSort().merge(toMerge, 0, 4, toMerge.length);
        System.out.println("Result: "+Arrays.toString(toMerge));
        Preconditions.checkArgument(Sorts.isSorted(toMerge));
    }*/
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


    private static int[] generateSorted(int n, int b) {
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
    }

}