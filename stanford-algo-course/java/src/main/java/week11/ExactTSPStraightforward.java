package week11;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Solve TSP via exact dynamic programming algorithm. Max problem size is 33: 
 * sizeof(int)+1, or maybe 32 due to sign bit magic.
 * 
 * Use simple bit encoding for subsets. Can be optimized not to have 0 vertex.
 */
public class ExactTSPStraightforward {
    
    private final float[][] adj;
    private final int n;

    public ExactTSPStraightforward(float[][] adj) {
        this.adj = adj;
        this.n = adj.length;
    }
    
    public float computeLengthOfMinimumTour() {
        final float[][] a = initSubstructureArray();
        for (int m = 2; m <= n; m++) {
            IntStream subsets = getSubsetsOfSize(n, m);
            subsets.parallel().forEach(s -> {
                getSetBits(s).filter(j -> j > 0).forEach(j -> {
                    int sMinusJ = s ^ (1 << j);
                    IntStream ks = getSetBits(s).filter(k -> k != j);
                    a[j][s] = (float) ks.mapToDouble(k -> a[k][sMinusJ]+adj[j][k]).min().getAsDouble();
                });
            });
        }
        //close the loop
        int allVertexSet = (1 << n) - 1;
        double result = IntStream.range(1, n).mapToDouble(j -> a[j][allVertexSet] + adj[0][j]).min().getAsDouble();
        return (float) result;
    }

    private IntStream getSetBits(int i) {
        return IntStream.range(0, n).filter(b -> ((i >> b) & 1) > 0);
    }
    
    static IntStream getSubsetsOfSize(int n, int m) {
        int maxSubsetSize = 1 << n;
        return IntStream.range(0, maxSubsetSize).filter(i -> i % 2 == 1 && hasBitCount(i, m));
    }

    private static boolean hasBitCount(int i, int bitCount) {
        int c; // c accumulates the total bits set in i
        for (c = 0; i > 0; c++) 
          i &= i - 1; // clear the least significant bit set
        return c == bitCount;
    }

    private float[][] initSubstructureArray() {
        int maxSubsetSize = 1 << n;
        float[][] result = new float[n][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new float[maxSubsetSize];
            result[i][0] = Float.MAX_VALUE/2;
        }
        Arrays.fill(result[0], Float.MAX_VALUE/2);
        result[0][1]=0;
        return result;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExactTSPStraightforward tsp = new ExactTSPStraightforward(InputParser.parseResource("/tsp.txt"));//26442.73
        float tourLength = tsp.computeLengthOfMinimumTour();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Length: "+tourLength);
        System.out.println("Computed min tour in "+duration+" millis");
    }    
}