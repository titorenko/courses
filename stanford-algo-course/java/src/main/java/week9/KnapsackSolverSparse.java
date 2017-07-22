package week9;


/**
 * Solve knapsack by only updating and storing what has changed
 */
public class KnapsackSolverSparse implements KnapsackSolver {
    
    public int solve(final Knapsack ks) {
        IntToIntTreeMap bst = new IntToIntTreeMap();
        bst.add(0, 0);
        for (KnapsackItem item : ks.items) {
            bst.forEachDescending((key, value) -> {
                final int takeWeight = key + item.weight;
                final int takeValue = value + item.value;
                if (takeWeight > ks.capacity) return;
                final int skipValue = bst.floorValue(takeWeight);
                if (takeValue > skipValue ) {
                    bst.removeTailsWithSmallerValues(takeWeight, takeValue);
                    bst.add(takeWeight, takeValue);
                }
            });
        }
        return bst.lastValue();
    }
    
    public static void main(String[] args) {
        Knapsack ks = InputParser.parseResource("/knapsack_big.txt");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new KnapsackSolverSparse().solve(ks);
        }
        System.out.println("Duration: "+(System.currentTimeMillis()- start));
        System.out.println(new KnapsackSolverSparse().solve(ks));
    }
}