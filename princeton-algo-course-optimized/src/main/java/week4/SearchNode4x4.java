package week4;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.base.Preconditions;

import common.MinPQ;

public class SearchNode4x4 implements Comparable<SearchNode4x4> {
    
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
    
    long board;
    private SearchNode4x4 prev;
    final short score;
    final byte nMoves;
    
    public SearchNode4x4(int[][] blocks) {
        int n = blocks.length;
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Preconditions.checkArgument(blocks[i][j] < (1 << n));
                set(idx, blocks[i][j]);
                idx++;
            }
        }
        this.nMoves = 0;
        this.score = (short) manhattan();
        this.prev = null;
    }
    
    public SearchNode4x4(long board, byte nMoves, short score, SearchNode4x4 prev) {
        this.board = board;
        this.nMoves = nMoves;
        this.score = score;
        this.prev = prev;
    }

    private void set(int idx, int value) {
        final long shifted = (long) value << (idx << 2);
        board = (board & rmasks[idx]) | shifted;
    }
    
    private int get(int idx) {
        return (int) ((board & masks[idx]) >>> (idx << 2)); 
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
    
    public void insertNextMoves(MinPQ<SearchNode4x4> q1) {
        final int zidx = getIndexOfZero();
        final int row0 = zidx / n;
        final int col0 = zidx % n;
        addNeighbor(row0, col0+1, row0, col0, q1);
        addNeighbor(row0, col0-1, row0, col0, q1);
        addNeighbor(row0-1, col0, row0, col0, q1);
        addNeighbor(row0+1, col0, row0, col0, q1);
    }

    private void addNeighbor(int i, int j, int row0, int col0, MinPQ<SearchNode4x4> q1) {
        if (i < 0 || i >= n || j < 0 || j >= n) return;

        final int from = i*n + j;
        final int to = row0*n + col0;
        long nb = swap(board, from, to);
        if (prev == null || prev.board != nb) {
            int delta = manhattanDeltaWithZero(i, j, row0, col0, get(from));
            q1.insert(new SearchNode4x4(nb, (byte) (nMoves+1), (short) (score + delta+1), this));
            Solver4x4.nInserts++;
        }
    }
    
    /** Calculate change in Manhattan score due to supplied
     * element moving onto zero position
     */
    private int manhattanDeltaWithZero(int i, int j, int row0, int col0, int value) {
        int vrow = (value - 1) / n;
        int vcol = (value - 1) % n;
        return Math.abs(vrow-row0) + Math.abs(vcol-col0) - Math.abs(vrow-i) - Math.abs(vcol-j); //compute directly to save on division
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
        return score == nMoves;
    }
    
    private static long swap(long board, int idx1, int idx2) {
        int tmp = get(board, idx1);
        long result = set(board, idx1, get(board, idx2));
        return set(result, idx2, tmp);
    }
    
    private static long set(long board, int idx, int value) {
        final long shifted = (long) value << (idx << 2);
        return (board & rmasks[idx]) | shifted;
    }
    
    private static int get(long board, int idx) {
        return (int) ((board & masks[idx]) >>> (idx << 2)); 
    }

    private int getIndexOfZero() {
        for (int idx0 = 0; idx0 < nn; idx0++) {
            if (get(idx0) == 0) return idx0;
        }
        throw new IllegalArgumentException("Board "+toString()+" does not contain empty square: "+Long.toHexString(board));
    }

    @Override
    public int compareTo(SearchNode4x4 other) {
        int diff = score - other.score;
        return diff == 0 ? other.nMoves - nMoves : diff;
    }

    public Iterable<Long> reconstruct() {
        Deque<Long> result = new ArrayDeque<>();
        SearchNode4x4 node = this;
        while (node != null) {
            result.addFirst(node.board);
            node = node.prev;
        }
        return result;
    }  
    
    public String solutionMoves() {
        char[] result = new char[nMoves];
        SearchNode4x4 node = this;
        for (int i = result.length-1; i >= 0; i--) {
            result[i] = getMove(node.prev, node);
            node = node.prev;
        }
        return new String(result);
    }

    private char getMove(SearchNode4x4 from, SearchNode4x4 to) {
        int from0 = from.getIndexOfZero();
        int to0 = to.getIndexOfZero();
        int diff = from0 - to0;
        if (diff == 1) return 'R';
        if (diff == -1) return 'L';
        if (diff == n) return 'D';
        if (diff == -n) return 'U';
        throw new IllegalStateException("Illegal move: "+from+" to "+to);
    }

    public SearchNode4x4 rollback(int nMoves) {
        SearchNode4x4 node = this;
        for (int i = 0; i < nMoves; i++) {
            node = node.prev;
        }
        return node;
        
    }  
}