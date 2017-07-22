package week4;

import java.util.ArrayList;
import java.util.List;

public class SolverStatsChecker {
    private static String[] testSet = {"puzzle20.txt", "puzzle21.txt", "puzzle22.txt", "puzzle23.txt",
            "puzzle24.txt", "puzzle25.txt", "puzzle26.txt", "puzzle27.txt",
            "puzzle28.txt", "puzzle29.txt", "puzzle30.txt", "puzzle31.txt",
            "puzzle34.txt", "puzzle37.txt", "puzzle39.txt", "puzzle41.txt",
            "puzzle44.txt", "puzzle4x4-hard2.txt"/*, "puzzle4x4-78.txt", /*"puzzle4x4-80.txt"*/};
    
    private static List<Board> loadBoards() {
        List<Board> result = new ArrayList<>();
        for (String file : testSet) {
            result.add(InputData.load("src/main/resources/8puzzle/"+file));
        }
        return result;
    }
    
    public static void main(String[] args) {
        List<Board> boards = loadBoards();
        int i = 0;
        int maxQ = 0;
        int delMin = 0;
        int nInserts = 0;
        int totalDuration = 0;
        int lookups = 0;
        for (Board board : boards) {
            Solver.maxQ = Solver.nDelMin = Solver.nInserts = Solver.nLookups = 0;
            long start = System.currentTimeMillis();
            Solver solver = new Solver(board);
            int moves = solver.moves();
            long duration = System.currentTimeMillis() - start;
            String msg = String.format("Board %s, inserts=%d, delMin=%d, maxq=%d, duration=%d, moves=%d, lookups=%d", testSet[i], Solver.nInserts, Solver.nDelMin, Solver.maxQ, duration, moves, Solver.nLookups);
            nInserts += Solver.nInserts;
            delMin += Solver.nDelMin;
            maxQ += Solver.maxQ;
            lookups += Solver.nLookups;
            totalDuration += duration; 
            System.out.println(msg);
            
            i++;
        }
        System.out.println("-------------------");
        String msg = String.format("Total: inserts=%d, delMin=%d, maxq=%d, lookups=%d, duration=%d", nInserts, delMin, maxQ, lookups, totalDuration);
        System.out.println(msg);
    }
}

