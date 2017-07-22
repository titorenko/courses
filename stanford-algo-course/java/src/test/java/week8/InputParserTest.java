package week8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class InputParserTest {
	
	@Test
	public void testParsing() throws IOException {
		WeightedGraph wg = InputParser.parseAssignmentFile();
		assertEquals(500, wg.getVertexCount());
		Edge edge = wg.getAdj()[0].get(1);
		assertEquals(0, edge.from);
		assertEquals(2, edge.to);
		assertEquals(5250, edge.weight);
	}
}
