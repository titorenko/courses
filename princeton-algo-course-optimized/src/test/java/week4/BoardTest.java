package week4;

import java.util.Iterator;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    private Board board = InputData.input04();
    
    @Test
    public void testBoardInitialState() {
        assertEquals(3, board.dimension());
        assertFalse(board.isGoal());
    }
    
    @Test
    public void testBoardGoal() {
        Board board = InputData.solved();
        assertTrue(board.isGoal());
    }
    
    @Test
    public void testHamming() {
        assertEquals(4, board.hamming());
    }
    
    @Test
    public void testManhattan() {
        assertEquals(4, board.manhattan());
    }
    
    @Test
    public void testEquals() {
        assertEquals(board, InputData.input04());
        assertNotEquals(board, board.twin());
    }
    
    @Test
    public void testToString() {
        String expected = "3\n 0 1 3\n 4 2 5\n 7 8 6\n";
        assertEquals(expected, board.toString());
    }
    
    @Test
    public void testNeighbours() {
        Iterable<Board> neighbors = board.neighbors();
        int count = 0;
        for (Iterator<Board> iterator = neighbors.iterator(); iterator.hasNext();) {
            iterator.next();
            count++;
        }
        assertEquals(2, count);
    }
    
    @Test
    public void testNeighboutHamming1() {
        Board n1 = board.neighbors().iterator().next();
        assertEquals(3, n1.hamming());
    }
}