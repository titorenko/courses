package week11;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Compute TSP using O(n^2 * 2^n) dynamic programming algorithm.
 * We use optimal encoding for subsets using binomial coefficients. Implementation is parallel.
 * It should be possible to optimise this implementation to run x2 faster if we run TSP
 * for half of the length and then glue best clockwise and counterclockwise tours.
 * 
 */
public class ExactTSP {
   
    private final float[][] adj;
    private final int n;
    private final Subsets subsets;

    public ExactTSP(float[][] adj) {
        this.adj = adj;
        this.n = adj.length;
        this.subsets = new Subsets(n);
    }

    public float computeLengthOfMinimumTour() {
        float[][] aprev = initSubstructureArray();
        float[][] acur = initSubstructureArray();
        acur[0][0] = Float.MAX_VALUE / 2;
        int half = (n+1) / 2 + 1;
        for (int m = 2; m <= half; m++) {
            subsets.subsets(m).forEach(tuple -> {
                final int[] s = tuple[0];
                final int idx = tuple[1][0];
                for (int ji = 1; ji < s.length; ji++) {
                    final int j = s[ji];
                    int sMinusJ = subsets.indexOf(s, j);
                    float min = Float.MAX_VALUE;
                    final float[] lastHops = adj[j];
                    for(int k : s) {
                        if (k == j) continue;
                        float candidate = aprev[k][sMinusJ] + lastHops[k];
                        if (candidate < min) min = candidate;
                    }
                    acur[j][idx] = min;
                }
            });
            if (m < half || n % 2 == 0) copyFromCurToPrev(acur, aprev, m);//odd length have to use different paths
        }
        
        //glue the clockwise and counterclockwise paths trying all the midpoints
        double min = IntStream.range(1, n).mapToDouble(midPoint -> 
        	subsets.subsets(half).mapToDouble(tuple -> {
        		final int[] ss = tuple[0];
                final int s = tuple[1][0];
                int sneg = subsets.indexOfComplement(ss, midPoint);
                if (sneg == -1) return Float.MAX_VALUE;
				float candidate = acur[midPoint][s] + aprev[midPoint][sneg];
				return candidate;
			}).min().getAsDouble()
		).min().getAsDouble();
        return (float) min;
    }

    private void copyFromCurToPrev(float[][] acur, float[][] aprev, int m) {
        for (int i = 0; i < n; i++) {
            System.arraycopy(acur[i], 0, aprev[i], 0, subsets.getSize(m));
        }
    }

    private float[][] initSubstructureArray() {
        int maxSubsetSize = subsets.getMaxSize();
        float[][] result = new float[n][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new float[maxSubsetSize];
            Arrays.fill(result[i], Float.MAX_VALUE/2);
        }
        result[0][0]=0;
        return result;
    }

    public static void main(String[] args) {
	    long start = System.currentTimeMillis();
	    ExactTSP tsp = new ExactTSP(InputParser.parseResource("/tsp.txt"));
	    float tourLength = tsp.computeLengthOfMinimumTour();
	    long duration = System.currentTimeMillis() - start;
	    System.out.println("Length: "+tourLength);
	    System.out.println("Computed min tour in "+duration+" millis");
    }   
}
