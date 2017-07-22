package week4;

import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {
    
    @Test
    public void testSolver() {
        Board input04 = InputData.input04();
        Solver solver = new Solver(input04);
        assertTrue(solver.isSolvable());
        assertEquals(4, solver.moves());
    }
    
    
    @Test
    public void testPuzzle34() {
        Board input = InputData.load("src/main/resources/8puzzle/puzzle34.txt");
        Solver solver = new Solver(input);
        assertTrue(solver.isSolvable());
        assertEquals(34, solver.moves());
    }
}
