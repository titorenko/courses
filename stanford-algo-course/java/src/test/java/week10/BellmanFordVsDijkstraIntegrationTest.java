package week10;

import org.junit.Assert;
import org.junit.Test;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.DijkstraShortestPath;
import week5.DijkstraSolvers;
import week5.WeightedDigraph;
import common.InputData;

public class BellmanFordVsDijkstraIntegrationTest {

    @Test
    public void testOnInput5() throws NegaticeCycleFoundException {
        WeightedDigraph g = InputData.week5();
        long start = System.currentTimeMillis();
        BellmanFord bf = new BellmanFord(g, 0);
        int[] bfResult = bf.find();
        System.out.println("Bellman-Ford done in "+(System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        DijkstraShortestPath d = DijkstraSolvers.BINARY.solverFor(g, 0);
        int[] dbr = d.find();
        System.out.println("Dijkstra done in "+(System.currentTimeMillis() - start));
        Assert.assertArrayEquals(dbr, bfResult);
    }
}
