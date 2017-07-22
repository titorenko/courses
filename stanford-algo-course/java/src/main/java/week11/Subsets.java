package week11;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import optimized.Sorts;

/**
 * Generates subsets from set of n elements with m elements in each subset.
 * Generally most methods will always include first element, as per TSP requirements.
 * Subsets are generated in the following sequence, for example for subset of length 2 from 4:
 * <pre>
 *  0 0 1 1
 *  0 1 0 1
 *  1 0 0 1
 *  0 1 1 0
 *  1 0 1 0
 *  1 1 0 0
 * </pre>
 *  or in set index representation that is used to represent subsets here
 * <pre>
 *  1 0
 *  2 0
 *  3 0
 *  2 1
 *  3 1
 *  3 2
 * </pre>
 *  
 *  Note that such representation is ordered. We can also compute sequence order given subset. 
 *  We try to do so efficiently by caching "jumps" that calculated using binomial coefficients.
 *  
 *  For example to get index of "3 1" in c[4][2] example above, observe that since first index is 1
 *  we have to skip c[n-1][m-1] elements, because this is the number of elements with first bit
 *  1 and rest of 1 elements selected from 3 remaining places, i.e. c[3][1].
 *  
 *  So the final index is c[3][1]+c[1][1] = 3 + 1 = 4 (zero based)
 *  
 *  Another example for "3 2", final index = c[3][1]+c[2][1] = 5. See indexOf method for details.
 */
public class Subsets {
    
    private final int n;
    private final int[][] c;
    private final int[][][] sums;

    Subsets(int n) {
        this.n = n;
        this.c = computeBinomialCoefficients(n, n);
        this.sums = precomputeSumsOfBinomials(n);
    }

    private int[][][] precomputeSumsOfBinomials(int n) {
        final int[][][] result = new int[n][][];
        for (int i = 0; i < n; i++) {
            result[i] = new int[n][];
            for (int j = 0; j < n; j++) {
                result[i][j] = new int[n];
                for (int k = 0; k < result[i][j].length; k++) {
                    result[i][j][k] = computeSumOfBinomials(i, j, k);
                }
            }
        }
        return result;
    }
    
    private int[][] computeBinomialCoefficients(int N, int K) {
        int[][] c = new int[n+1][];
        for (int i = 0; i < c.length; i++) c[i] = new int[K+1];
        for (int k = 1; k <= K; k++) c[0][k] = 0;
        for (int n = 0; n <= N; n++) c[n][0] = 1;

        for (int n = 1; n <= N; n++)
           for (int k = 1; k <= K; k++)
              c[n][k] = c[n-1][k-1] + c[n-1][k];
        return c;
    }
    
    /** returns next subset in natural order, reusing the array*/ 
    void nextSubset(int[] subset) {
        final int m = subset.length-1;
        final int max = n-m-1;
        int i = m;
        for(; i>0 && (i!=m && subset[i]>=subset[i+1] || subset[i] >= max+i); i--);//find index to increment
        subset[i] = subset[i]+1;
        for (int j = i+1; j <= m; j++) {
            subset[j] = subset[i]+(j-i);
        }
    }
    
    class SubsetSpliterator implements Spliterator<int[][]> {
        
        private static final int MIN_PARALLELISM_SIZE = 200;
		private int[][] state;
        private int lastIndex;

        SubsetSpliterator(int m) {
            this.state = initialStreamState(m);
            this.lastIndex = c[n-1][m-1];
        }

        private SubsetSpliterator(int[][] state, int lastIndex) {
            this.state = state;
            this.lastIndex = lastIndex;
        }

        @Override
        public boolean tryAdvance(Consumer<? super int[][]> action) {
            action.accept(state);
            nextStreamState(state);
            return estimateSize() > 0;
        }

        @Override
        public Spliterator<int[][]> trySplit() {
            if (estimateSize() < MIN_PARALLELISM_SIZE) {
                return null;
            }
            int newLastIndex = lastIndex;
            int[][] newState = fastForward(state);
            SubsetSpliterator result = new SubsetSpliterator(newState, newLastIndex);
            this.lastIndex = newState[1][0];
            return result;
        }
        
        private int[][] fastForward(int[][] state) {
        	int[][] result = new int[2][];
        	int mid = (int) (estimateSize() / 2);
	        int idx = state[1][0] + mid;
	        result[0] = getSubsetWithIndex(idx, state[0].length);
	        result[1] = new int[] {idx};
	        return result;
        }

        @Override
        public long estimateSize() {
            return lastIndex - state[1][0];
        }

        @Override
        public int characteristics() {
            return IMMUTABLE + SIZED + SUBSIZED + NONNULL + DISTINCT;
        }
    }
    
    /**
     * Generate all subsets that contain 0 element and have exactly m elements.
     * First array contains subset, second contains sequence number of the element in the subset natural order.
     */
    Stream<int[][]> subsets(int m) {
        return StreamSupport.stream(new SubsetSpliterator(m), true);
    }
    
    private void nextStreamState(int[][] state) {
        nextSubset(state[0]);
        state[1][0] = state[1][0] + 1; 
    }
    
    private int[][] initialStreamState(int m) {
        int[][] result = new int[2][];
        result[0] = getFirstSubset(m);
        result[1] = new int[] {0};
        return result;
    }
    
    int[] getFirstSubset(int m) {
        int[] seed = new int[m];
        for (int i = 0; i < m; i++) {
            seed[i] = i;
        }
        return seed;
    }
    
    int indexOf(int[] subset) {
        assert Sorts.isSorted(subset);
        
        int result = 0;
        int i=0;
        int sel = subset.length-1;
        for (int s : subset) {
            result += sums[s][i][sel];
            i = s+1;
            sel--;
        }
        return result;
    }
    
    int[] getSubsetWithIndex(int idx, int m) {
    	int[] result = new int[m];
        int counter = 0;
        int nextElementToView = 0;
        while(counter < m){
			if (idx < c[n - 1 - nextElementToView][m - 1 - counter]) {
				result[counter] = nextElementToView;
				counter++;
			} else {
				idx -= c[n - 1 - nextElementToView][m - 1 - counter];
			}
            nextElementToView++;
        }
        return result;
    }
    
    /** Index of subset with an element excluded */
    int indexOf(final int[] subset, final int excludedElement) {
        assert Sorts.isSorted(subset);
        
        int result = 0;
        int i=0;
        int sel = subset.length-2;
        for (int s : subset) {
            if (s == excludedElement) continue;
            result += sums[s][i][sel];
            i = s+1;
            sel--;
        }
        
        return result;
    }
    
    /**
     * Calculate index of subset that contains 0, midpoint and no other points
     * from subset.
     */
    public int indexOfComplement(int[] subset, int midPoint) {
        int result = 0;
        int i=0;
        int k=0;
        boolean midPointFound = false;
        int sel = n - subset.length + 1;
        for (int s = 0; s < n; s++) {
            if (k < subset.length && subset[k] == s) {
                k++;
                if (s != 0 && s != midPoint) continue;
                if (s == midPoint) midPointFound = true;
            }
            result += sums[s][i][sel];
            i = s+1;
            sel--;
        }
        return midPointFound ? result : -1;
    }
    
    
    //precompute all the jumps to speed up index calculation
    private int computeSumOfBinomials(final int s, int i, final int sel) {
        int result = 0;
        for(; s-i > 0; i++) {
            result += c[n-1-i][sel];
        }
        return result;
    }
    
    public int getSize(int m) {
    	return c[n-1][m-1];
    }

    public int getMaxSize() {
        return IntStream.range(1, n).map(k -> c[n-1][k-1]).max().getAsInt();
    }
    
    static int[] removeFromArray(int[] s, int j) {
        int idx = Arrays.binarySearch(s, j);
        int[] result = new int[s.length - 1];
        System.arraycopy(s, 0, result, 0, idx);
        System.arraycopy(s, idx+1, result, idx, s.length-idx-1);
        return result;
    }
}