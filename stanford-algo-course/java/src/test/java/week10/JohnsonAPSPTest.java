package week10;

import org.junit.Assert;
import org.junit.Test;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.WeightedDigraph;
import week5.WeightedDigraph.Edge;
import week5.WeightedGraphBuilder;

import static org.junit.Assert.assertEquals;

public class JohnsonAPSPTest {
    
    @Test
    public void testAttachVirtualSource() {
        WeightedGraphBuilder g = new WeightedGraphBuilder(2).addEdge(0, 1, 1);
        WeightedDigraph g2 = new JohnsonAPSP(g.build()).attachVirtualSource();
        Edge[] edges = g2.getEdges();
        assertEquals(3, edges.length);
        assertEquals("3 -> 1 [weight=0]", edges[1].toString());
        assertEquals("3 -> 2 [weight=0]", edges[2].toString());
    }
    
    @Test
    public void compareWihtBellmanFord() throws NegaticeCycleFoundException {
        int test = 12;//should be equal for all < n
        WeightedDigraph g = InputParser.assignment3();
        JohnsonAPSP alg = new JohnsonAPSP(g);
        int[][] shortestPaths = alg.solve();
        Assert.assertArrayEquals(shortestPaths[test], new BellmanFord(g, test).find());
    }
    
    @Test(expected=NegaticeCycleFoundException.class)
    public void checkNegativeCycleDetection() throws NegaticeCycleFoundException {
        WeightedDigraph g = InputParser.assignment1();
        JohnsonAPSP alg = new JohnsonAPSP(g);
        alg.solve();
    }
}
