

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private final int n;
    private final byte[] blocks; //n*n grid
    private final int row0; //row of zero element, to save on zero element search
    private final int col0; //col of zero element
    private int manhattan;

    /**
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j) */
    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = new byte[n*n];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[idx] = (byte) blocks[i][j];
                idx++;
            }
        }
        int idx0 = getIndexOfZero();
        this.row0 = idx0 / n;
        this.col0 = idx0 % n;
        this.manhattan = computeManhattan();
    }
    
    private int getIndexOfZero() {
        for (int idx0 = 0; idx0 < blocks.length; idx0++) {
            if (blocks[idx0] == 0) return idx0;
        }
        throw new IllegalArgumentException("Input array does not contain empty square");
    }

    private Board(Board other, int row0, int col0, int manhattan) {
        this(other, row0, col0);
        this.manhattan = manhattan;
    }
    
    private Board(Board other, int row0, int col0) {
        this.n = other.n;
        this.blocks = other.blocks.clone();
        this.row0 = row0;
        this.col0 = col0;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int score = 0;
        for (int i = 0; i < blocks.length; i++) {
            final int value = blocks[i];
            if (value != 0 && value != i+1) score++; 
        }
        return score;
    }

    public int manhattan() {
        assert manhattan == computeManhattan();
        return manhattan;
    }

    private int computeManhattan() {
        int score = 0;
        int row = 0;//maintain row and col explicitly to save on extra division and remainder operations
        int col = 0;
        for (int i = 0; i < blocks.length; i++) {
            score += manhattanDist(blocks[i], row, col); 
            col++;
            if (col == n) {
                col = 0;
                row++;
            }
        }
        return score;
    }

    private int manhattanDist(int value, int row2, int col2) {
        if (value == 0) return 0;
        int row1 = (value - 1) / n;
        int col1 = (value - 1) % n;
        return Math.abs(row1-row2)+Math.abs(col1-col2);
    }

    public boolean isGoal() {
        return manhattan == 0;
    }
    
    public Board twin() {
        int idx = 0;
        outer: for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {
                idx = i*n+j;
                if (blocks[idx] != 0 && blocks[idx+1] != 0) break outer;
            }
        }
        Board clone = new Board(this, this.row0, this.col0);
        clone.swap(idx, idx+1);
        clone.manhattan = clone.computeManhattan();
        return clone;
    }
    
    private void swap(int idx1, int idx2) {
        byte tmp = blocks[idx1];
        blocks[idx1] = blocks[idx2];
        blocks[idx2] = tmp;
    }
    
    @Override
    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) return false;
        final byte[] thisBlocks = blocks;
        final byte[] otherBlocks = ((Board) y).blocks;
        for (int i=0; i < blocks.length; i++)
            if (thisBlocks[i] != otherBlocks[i])
                return false;

        return true;
    }
    
    public Iterable<Board> neighbors() {
        final ArrayList<Board> neighbors = new ArrayList<>(4); 
        addNeighbor(row0, col0+1, neighbors);
        addNeighbor(row0, col0-1, neighbors);
        addNeighbor(row0-1, col0, neighbors);
        addNeighbor(row0+1, col0, neighbors);
        return neighbors;
    }

    private void addNeighbor(int i, int j, List<Board> boards) {
        if (i < 0 || i >= n || j < 0 || j >= n) return;

        final int from = i*n + j;
        final int to = row0*n + col0;
        int delta = manhattanDeltaWithZero(i, j, blocks[from]);
        Board clone = new Board(this, i, j, manhattan + delta);
        clone.swap(from, to);
        boards.add(clone);
    }

    /** Calculate change in Manhattan score due to supplied
     * element moving onto zero position
     */
    private int manhattanDeltaWithZero(int i, int j, short value) {
        int vrow = (value - 1) / n;
        int vcol = (value - 1) % n;
        return Math.abs(vrow-row0) + Math.abs(vcol-col0) - Math.abs(vrow-i) - Math.abs(vcol-j); //compute directly to save on division
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(n);
        result.append("\n");
        for (int i = 0; i < blocks.length; i++) {
            if (i > 0 && i % n == 0) result.append("\n");
            result.append(' ');
            result.append(blocks[i]);
        }
        result.append("\n");
        return result.toString();
    }
}