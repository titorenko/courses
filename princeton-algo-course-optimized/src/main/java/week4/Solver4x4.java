package week4;

import common.MinPQ;

public class Solver4x4 {
    
    private SearchNode4x4 solution;

    private MinPQ<SearchNode4x4> q1;
    
    public static int nInserts = 0;
    public static int maxQ = 0;
    public static int nDelMin = 0;
    
    public Solver4x4(int[][] blocks) {
        this.q1 = new MinPQ<>(blocks.length*blocks.length);
        q1.insert(new SearchNode4x4(blocks));
    }
    
    public Solver4x4(SearchNode4x4 startingNode) {
        this.q1 = new MinPQ<>();
        q1.insert(startingNode);
    }

    public void solve() {
        solve(-1);
    }
    
    private void solve(int queueBound) {
        int nQueueShrinks = 0;
        int maxMove = 0;
        while(true) {
            SearchNode4x4 node = q1.delMin();
            nDelMin++;
            if (node.isGoal()) {
                solution = node;
                break;
            } else {
                node.insertNextMoves(q1);
            }
            if (queueBound != -1 && q1.size() > queueBound) {
                q1 = q1.shrink();
                nQueueShrinks++;
                System.out.print("."+maxMove);
            }
            if (q1.size() > maxQ) maxQ = q1.size();
            if (node.nMoves > maxMove) {
                maxMove = node.nMoves;
            }
        }
        System.out.println("\nInserts: "+nInserts+", del "+nDelMin+", queue size "+", max size "+maxQ+", queue shrunk: "+nQueueShrinks);
    }


    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        return isSolvable() ? solution.nMoves : -1;
    }

    public  Iterable<Long> solution() {
        return isSolvable() ? solution.reconstruct() : null;
    }

    public static void main(String[] args) {
        int[][] input = InputData.loadArray("src/main/resources/8puzzle/puzzle4x4-80.txt");
        Solver4x4 solver = new Solver4x4(input);
        solver.solve(12_000_000);
        System.out.println(solver.solution.nMoves+" move solution is: "+solver.solution.solutionMoves());
        SearchNode4x4 intermediate = solver.solution.rollback(60);
        System.out.println("Intermediate moves:  "+intermediate.solutionMoves());
        SearchNode4x4 clone = new SearchNode4x4(intermediate.board, (byte) 0, (short) intermediate.manhattan(), null);
        Solver4x4 solver2 = new Solver4x4(clone);
        solver2.solve();
        System.out.println("Sub-solution in "+solver2.solution.nMoves+" moves is: "+solver2.solution.solutionMoves());
        System.out.println("----------------");
        System.out.println("Suboptimal solution: "+solver.solution.solutionMoves());
        System.out.println("Optimized solution : "+intermediate.solutionMoves()+solver2.solution.solutionMoves());
    }
}
//12_000_000: UULULLDDRDRRUUULLDDLDRRUURULLDDDRUULDDRURUULLLDDDRRRUUULLDLURDLDDRRRUULULDDRURULLL
// optimized: UULULLDDRDRRUUULLDDLDRRUURULLDDDRUULLDRDRURUULLLDDDRRRUUULLLDDDRRRUULULDDRURULLL
//8_000_000:  UUULDRDLUULLDDDRRUUULLDDDRRUULLDDRRURULLDRRDLURULURDLULDLDRRULDRRULULLDDRDRRULUULDLU (non-opt)
//4_000_000:  LLLURULURDRDRULDDRULLDRULULDDRUUURDDLLUURRDRULDRDLLURDLULDRULURRDDDRULLDLURDRUULDLURUL
//2_400_000:  LLURRULURDLULLDDDRURULULDDRRULLDRRDRULURDLLDRRULULDDLURRRULURDDLUULLDDRDRUUULDDRULUL (optimizable by 2)
//1_200_000:  LLLUURULDRURRDDDLLUURURDDLDLULURRRDLDLUURRDDLLLURDLUURULDRURDDLUURRDLDLUURDRDLDLULUU (non opt)
//800_000:    LLLURDLUUURRRDDLULLURRDRULDLDRULURDRDLDLLUURRRULDDDRUULDDLLUUURRDDDLLURURDDLULURUL (non opt)
