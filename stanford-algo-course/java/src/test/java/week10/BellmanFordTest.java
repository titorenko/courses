package week10;

import org.junit.Test;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.WeightedGraphBuilder;

import static org.junit.Assert.assertArrayEquals;

public class BellmanFordTest {
    @Test
    public void testDiffenbach1() throws NegaticeCycleFoundException {
        WeightedGraphBuilder builder = new WeightedGraphBuilder(4)
            .addEdge(0, 1, 3).addEdge(0, 2, 3)
            .addEdge(1, 2, 1).addEdge(1, 3, 2)
            .addEdge(2, 3, 50);
        BellmanFord bf = new BellmanFord(builder.build(), 0);
        assertArrayEquals(new int[] {0, 3, 3, 5}, bf.find());
    }
    
    @Test
    public void testDiffenbach2() throws NegaticeCycleFoundException {
        WeightedGraphBuilder builder = new WeightedGraphBuilder(4)
            .addEdge(0, 1, 3).addEdge(0, 2, 5)
            .addEdge(1, 2, 1).addEdge(1, 3, 2)
            .addEdge(2, 3, 50);
        BellmanFord bf = new BellmanFord(builder.build(), 0);
        assertArrayEquals(new int[] {0, 3, 4, 5}, bf.find());
    }
    
    @Test
    public void testGorkovenko1() throws NegaticeCycleFoundException {
        WeightedGraphBuilder builder = new WeightedGraphBuilder(4)
            .addEdge(0, 1, 8).addEdge(0, 2, 15)
            .addEdge(1, 0, 7).addEdge(1, 2, 4).addEdge(1, 3, 5)
            .addEdge(2, 0, 12)
            .addEdge(3, 2, 5);
        BellmanFord bf = new BellmanFord(builder.build(), 0);
        assertArrayEquals(new int[] {0, 8, 12, 13}, bf.find());
    }
    
    //TODO: add negative cycle test
}
