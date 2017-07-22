package week10;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import week5.WeightedDigraph;
import week5.WeightedDigraph.Edge;

public class InputParserTest {
    @Test
    public void testParseFile1() {
        WeightedDigraph g1 = InputParser.assignment1();
        assertEquals(47978, g1.getEdgeCount());
        List<Edge> from0 = g1.getAdj()[0];
        Edge first = from0.get(0);
        assertEquals(0, first.from);
        assertEquals(13, first.to);
        assertEquals(6, first.weight);
    }
}
