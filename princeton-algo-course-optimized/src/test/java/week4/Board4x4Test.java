package week4;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Board4x4Test {
    
    private Board4x4 board;
    private Board genericBoard;
    
    @Before 
    public void init() {
        int[][] input = InputData.loadArray("src/main/resources/8puzzle/puzzle4x4-78.txt");
        board = new Board4x4(input);
        genericBoard = new Board(input);
    }
    
    @Test
    public void testBoardInitialState() {
        assertEquals(4, board.dimension());
        assertFalse(board.isGoal());
    }
    
    @Test
    public void testBoardGoal() {
        Board board = InputData.solved();
        assertTrue(board.isGoal());
    }
    
    @Test
    public void testHamming() {
        assertEquals(genericBoard.hamming(), board.hamming());
    }
    
    
    @Test
    public void testToString() {
        String expected = "4\n 0 15 14 13\n 12 11 10 9\n 8 7 6 5\n 4 3 2 1\n";
        assertEquals(expected, board.toString());
    }
    
    @Test
    public void testManhattan() {
        assertEquals(genericBoard.manhattan(), board.manhattan());
    }
    
  /*  @Test
    public void testEquals() {
        assertEquals(board, InputData.input04());
       // assertNotEquals(board, board.twin());
    }*/
    
    @Test
    public void testNeighbours() {
        List<Board4x4> neighbors = board.neighbors();
        assertEquals(2, neighbors.size());
    }
    
    @Test
    public void testNeighboutHamming1() {
        Board4x4 n1 = board.neighbors().get(0);
        Board n1g = genericBoard.neighbors().iterator().next();
        assertEquals(n1g.hamming(), n1.hamming());
    }
}