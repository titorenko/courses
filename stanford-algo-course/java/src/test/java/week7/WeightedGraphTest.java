package week7;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeightedGraphTest {
    @Test
    public void testParsing() throws IOException {
        WeightedGraph g = WeightedGraphParser.fromResource(getClass().getResource("/prims_edges.txt"));
        assertEquals(500, g.getVertexCount());
        assertEquals(2184, g.getEdgeCount());
    }
    
    @Test
    public void testMST() throws IOException {
        WeightedGraph g = WeightedGraphParser.fromResource(getClass().getResource("/prims_test1.txt"));
        PrimsMST mst = new PrimsMST(g);
        assertEquals(2624, mst.computeMstCost());
    }
}