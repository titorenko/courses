package week9;

/**
 * Solve knapsack problem using dynamic programming approach.
 * Straightforward implementation.
 */
public class KnapsackSolverDPVanilla implements KnapsackSolver {
    
    public int solve(final Knapsack ks) {
        final int[][] table = initTable(ks);
        for (int i = 1; i <= ks.getItemCount(); i++) {
            final KnapsackItem item = ks.items[i-1];
            for (int w = 1; w <= ks.capacity; w++) {
                int skipValue = table[i-1][w];
                int value = w < item.weight ? 0 : item.value;
                int remainingWeight = w - item.weight;
                int takeValue = remainingWeight < 0 ? 0: value + table[i-1][remainingWeight];
                table[i][w] = Math.max(skipValue, takeValue);
            }
        }
        return table[ks.getItemCount()][ks.capacity];
    }

    private int[][] initTable(Knapsack ks) {
        int[][] table = new int[ks.getItemCount()+1][];
        for (int i = 0; i < table.length; i++) {
            table[i] = new int[ks.capacity+1];
        }
        return table;
    }
}