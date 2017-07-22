package week8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class BitPatternFullyConnectedGraphTest {
	
	private static BitPatternFullyConnectedGraph g;

	@BeforeClass
	public static void readGraph() throws IOException {
		g = InputParser.parseSecondAssignmentFile();
	}
	
	@Test
	public void testParsing() throws IOException {
		assertEquals(Integer.parseInt("111000001101001111001111", 2),  g.getVertexLabel(0));
		assertEquals(Integer.parseInt("011001100101111110101101", 2),  g.getVertexLabel(1));
	}
	
	@Test
	public void testWeightComputation() throws IOException {
		int weight = g.getWeight(0, 1);
		assertEquals(9, weight);
	}
}
