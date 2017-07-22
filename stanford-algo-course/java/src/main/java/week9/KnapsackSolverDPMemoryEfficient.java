package week9;


/**
 * Solve knapsack problem using dynamic programming approach.
 * Use memory efficiently. Reconstruction should be possible 
 * by repeating the process backwards.
 */
public class KnapsackSolverDPMemoryEfficient implements KnapsackSolver {
    
    public int solve(final Knapsack ks) {
        final int[] currentRow = new int[ks.capacity+1];
        for (KnapsackItem item : ks.items) {
            final int value = item.value;
            for (int w = ks.capacity, remainingWeight=ks.capacity-item.weight; remainingWeight >= 0; w--, remainingWeight--) {
                final int takeValue = value + currentRow[remainingWeight];
                if (takeValue > currentRow[w]) currentRow[w] = takeValue;
            }
        }
        return currentRow[ks.capacity];
    }
    
    public static void main(String[] args) {
        Knapsack ks = InputParser.parseResource("/knapsack_big.txt");
        long start = System.currentTimeMillis();
        System.out.println(new KnapsackSolverDPMemoryEfficient().solve(ks));
        System.out.println("Duration: "+(System.currentTimeMillis()- start));
    }
}