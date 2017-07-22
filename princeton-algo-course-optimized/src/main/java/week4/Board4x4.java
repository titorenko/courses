package week4;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

public class Board4x4 {
    private static long[] masks = new long[] {
        0x0000_0000_0000_000FL,
        0x0000_0000_0000_00F0L,
        0x0000_0000_0000_0F00L,
        0x0000_0000_0000_F000L,
        0x0000_0000_000F_0000L,
        0x0000_0000_00F0_0000L,
        0x0000_0000_0F00_0000L,
        0x0000_0000_F000_0000L,
        0x0000_000F_0000_0000L,
        0x0000_00F0_0000_0000L,
        0x0000_0F00_0000_0000L,
        0x0000_F000_0000_0000L,
        0x000F_0000_0000_0000L,
        0x00F0_0000_0000_0000L,
        0x0F00_0000_0000_0000L,
        0xF000_0000_0000_0000L
    };
    private static long[] rmasks = new long[] {
        ~0x0000_0000_0000_000FL,
        ~0x0000_0000_0000_00F0L,
        ~0x0000_0000_0000_0F00L,
        ~0x0000_0000_0000_F000L,
        ~0x0000_0000_000F_0000L,
        ~0x0000_0000_00F0_0000L,
        ~0x0000_0000_0F00_0000L,
        ~0x0000_0000_F000_0000L,
        ~0x0000_000F_0000_0000L,
        ~0x0000_00F0_0000_0000L,
        ~0x0000_0F00_0000_0000L,
        ~0x0000_F000_0000_0000L,
        ~0x000F_0000_0000_0000L,
        ~0x00F0_0000_0000_0000L,
        ~0x0F00_0000_0000_0000L,
        ~0xF000_0000_0000_0000L
    };
    private static final int n = 4;
    private static final int nn = n*n;
    long blocks; //n*n grid

    /**
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j) */
    public Board4x4(int[][] blocks) {
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Preconditions.checkArgument(blocks[i][j] < (1 << n)); 
                set(idx, blocks[i][j]);
                idx++;
            }
        }
    }
    
    private Board4x4(long blocks) {
        this.blocks = blocks;
    }
    
    private void set(int idx, int value) {
        final long shifted = (long) value << (idx << 2);
        blocks = (blocks & rmasks[idx]) | shifted;
    }
    
    private int get(int idx) {
        return (int) ((blocks & masks[idx]) >>> (idx << 2)); 
    }

    private int getIndexOfZero() {
        for (int idx0 = 0; idx0 < nn; idx0++) {
            if (get(idx0) == 0) return idx0;
        }
        throw new IllegalArgumentException("Board "+toString()+" does not contain empty square: "+Long.toHexString(blocks));
    }
    
    public int dimension() {
        return n;
    }
    
    public int hamming() {
        int score = 0;
        for (int i = 0; i < nn; i++) {
            final int value = get(i);
            if (value != 0 && value != i+1) score++; 
        }
        return score;
    }
    
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    public int manhattan() {
        int score = 0;
        int row = 0;//maintain row and col explicitly to save on extra division and remainder operations
        int col = 0;
        for (int i = 0; i < nn; i++) {
            score += manhattanDist(get(i), row, col); 
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
    
    @Override
    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board4x4)) return false;
        return blocks == ((Board4x4) y).blocks;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(blocks);
    }
    
    public List<Board4x4> neighbors() {
        final ArrayList<Board4x4> neighbors = new ArrayList<>(4);
        final int zidx = getIndexOfZero();
        final int row0 = zidx / n;
        final int col0 = zidx % n;
        addNeighbor(row0, col0+1, row0, col0, neighbors);
        addNeighbor(row0, col0-1, row0, col0, neighbors);
        addNeighbor(row0-1, col0, row0, col0, neighbors);
        addNeighbor(row0+1, col0, row0, col0, neighbors);
        return neighbors;
    }

    private void addNeighbor(int i, int j, int row0, int col0, List<Board4x4> boards) {
        if (i < 0 || i >= n || j < 0 || j >= n) return;

        final int from = i*n + j;
        final int to = row0*n + col0;
        Board4x4 clone = new Board4x4(blocks);
        clone.swap(from, to);
        boards.add(clone);
    }
    
    private void swap(int idx1, int idx2) {
        int tmp = get(idx1);
        set(idx1, get(idx2));
        set(idx2, tmp);
    }

   /* private Board44(Board44 other, int row0, int col0, int manhattan) {
        this(other, row0, col0);
        this.manhattan = manhattan;
    }
    
    private Board44(Board44 other, int row0, int col0) {
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
    
    public Board44 twin() {
        int idx = 0;
        outer: for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {
                idx = i*n+j;
                if (blocks[idx] != 0 && blocks[idx+1] != 0) break outer;
            }
        }
        Board44 clone = new Board44(this, this.row0, this.col0);
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
        if (y == null || !(y instanceof Board44)) return false;
        final byte[] thisBlocks = blocks;
        final byte[] otherBlocks = ((Board44) y).blocks;
        for (int i=0; i < blocks.length; i++)
            if (thisBlocks[i] != otherBlocks[i])
                return false;

        return true;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(blocks);
    }
    
    public Iterable<Board44> neighbors() {
        final ArrayList<Board44> neighbors = new ArrayList<>(4); 
        addNeighbor(row0, col0+1, neighbors);
        addNeighbor(row0, col0-1, neighbors);
        addNeighbor(row0-1, col0, neighbors);
        addNeighbor(row0+1, col0, neighbors);
        return neighbors;
    }

    private void addNeighbor(int i, int j, List<Board44> boards) {
        if (i < 0 || i >= n || j < 0 || j >= n) return;

        final int from = i*n + j;
        final int to = row0*n + col0;
        int delta = manhattanDeltaWithZero(i, j, blocks[from]);
        Board44 clone = new Board44(this, i, j, manhattan + delta);
        clone.swap(from, to);
        boards.add(clone);
    }

    *//** Calculate change in Manhattan score due to supplied
     * element moving onto zero position
     *//*
    private int manhattanDeltaWithZero(int i, int j, short value) {
        int vrow = (value - 1) / n;
        int vcol = (value - 1) % n;
        return Math.abs(vrow-row0) + Math.abs(vcol-col0) - Math.abs(vrow-i) - Math.abs(vcol-j); //compute directly to save on division
    } */

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(n);
        result.append("\n");
        for (int i = 0; i < n*n; i++) {
            if (i > 0 && i % n == 0) result.append("\n");
            result.append(' ');
            result.append(get(i));
        }
        result.append("\n");
        return result.toString();
    }
    
    public static void main(String[] args) {
        int[][] input = InputData.loadArray("src/main/resources/8puzzle/puzzle4x4-78.txt");
        Board4x4 board = new Board4x4(input);
        System.out.println(Long.toHexString(board.blocks));
        System.out.println(board);
    }
}