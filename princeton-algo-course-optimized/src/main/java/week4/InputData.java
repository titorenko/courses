package week4;

import common.In;

public class InputData {
    public static Board input04() {
        return load("src/main/resources/8puzzle/puzzle04.txt");
    }
    
    public static Board solved() {
        return load("src/main/resources/8puzzle/solved.txt");
    }

    public static Board load(String fileName) {
        return new Board(loadArray(fileName));
    }
    
    public static int[][] loadArray(String fileName) {
        In in = new In(fileName);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        return blocks;
    }
}
