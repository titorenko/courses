package week10;

import org.junit.Test;

import static org.junit.Assert.*;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.WeightedDigraph;

public class FloydWarshallTest {
    
    @Test(expected=NegaticeCycleFoundException.class)
    public void checkNegativeCycleDetection() throws NegaticeCycleFoundException {
        WeightedDigraph g = InputParser.assignment1();
        FloydWarshall alg = new FloydWarshall(g);
        alg.solve();
    }
    
    @Test
    public void compareWithJohnson() throws NegaticeCycleFoundException {
        WeightedDigraph g = InputParser.assignment3();
        int[][] expected = new JohnsonAPSP(g).solve();
        int[][] actual = new FloydWarshall(g).solve();
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }
}