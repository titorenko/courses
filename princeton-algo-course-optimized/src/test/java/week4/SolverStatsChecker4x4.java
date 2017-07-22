package week4;

import java.util.ArrayList;
import java.util.List;

public class SolverStatsChecker4x4 {
    private static String[] testSet = {
            "puzzle34.txt", "puzzle37.txt", "puzzle39.txt", 
            "puzzle4x4-hard2.txt",/*"puzzle4x4-78.txt", "puzzle4x4-80.txt"*/};
    
    private static List<int[][]> loadBoards() {
        List<int[][]> result = new ArrayList<>();
        for (String file : testSet) {
            result.add(InputData.loadArray("src/main/resources/8puzzle/"+file));
        }
        return result;
    }
    
    public static void main(String[] args) {
        List<int[][]> boards = loadBoards();
        int i = 0;
        int maxQ = 0;
        int delMin = 0;
        int nInserts = 0;
        for (int[][] board : boards) {
            Solver4x4.maxQ = Solver4x4.nDelMin = Solver4x4.nInserts = 0;
            long start = System.currentTimeMillis();
            Solver4x4 solver = new Solver4x4(board);
            solver.solve();
            int moves = solver.moves();
            long duration = System.currentTimeMillis() - start;
            String msg = String.format("Board %s, inserts=%d, delMin=%d, maxq=%d, duration=%d, moves=%d", testSet[i], Solver4x4.nInserts, Solver4x4.nDelMin, Solver4x4.maxQ, duration, moves);
            nInserts += Solver4x4.nInserts;
            delMin += Solver4x4.nDelMin;
            maxQ += Solver4x4.maxQ;
            System.out.println(msg);
            i++;
        }
        System.out.println("-------------------");
        String msg = String.format("Total: inserts=%d, delMin=%d, maxq=%d", nInserts, delMin, maxQ);
        System.out.println(msg);
    }
}
