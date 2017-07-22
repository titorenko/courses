package week7;

import org.junit.Test;

import static org.junit.Assert.*;

import week7.WeightedGraphParser.WeightedEdge;

public class WeightedEdgeTest {
    
    @Test
    public void testPackUnpackSmall() {
        long edge = WeightedEdge.pack(42, 314);
        assertEquals(42, WeightedEdge.vertex(edge));
        assertEquals(314, WeightedEdge.weight(edge));
    }
    
    @Test
    public void testPackUnpackNegative() {
        long edge = WeightedEdge.pack(42, -314);
        assertEquals(42, WeightedEdge.vertex(edge));
        assertEquals(-314, WeightedEdge.weight(edge));
    }
}
