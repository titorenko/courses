package week9;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class KnapsackSolverTest {
   
    @Parameters(name = "{0}")
    public static Iterable<Object[]> solvers() {
        return Arrays.asList(new Object[][] {
            {new KnapsackSolverDPVanilla()},
            {new KnapsackSolverDPMemoryEfficient()},
            {new KnapsackSolverSparse()}
        });
    }
    
    private KnapsackSolver solver;
    
    public KnapsackSolverTest(KnapsackSolver solver) {
        this.solver = solver;
    }
    
    
    @Test
    public void testAssignment1() throws IOException {
        Knapsack ks = InputParser.assignment1();
        assertEquals(2493893, solver.solve(ks));
        //assertEquals(4243395, solver.solve(InputParser.assignment2()));
    }
    
    @Test
    public void testOnTestCase1() throws IOException {
        Knapsack ks = InputParser.parseResource("/knapsack_test1.txt");
        assertEquals(12248, solver.solve(ks));
    }
    
    @Test
    public void testOnTestCase2() throws IOException {
        Knapsack ks = InputParser.parseResource("/knapsack_test2.txt");
        assertEquals(44, solver.solve(ks));
    }
    
    @Test
    public void testOnTestCase3() throws IOException {
        Knapsack ks = InputParser.parseResource("/knapsack_test3.txt");
        assertEquals(19, solver.solve(ks));
    }
}