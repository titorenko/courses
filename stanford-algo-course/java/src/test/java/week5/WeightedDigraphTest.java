package week5;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import common.InputData;

public class WeightedDigraphTest {
	
	static WeightedDigraph g1 = InputData.week5();

	@Test
	public void testDimensionsOfEx5() throws IOException {
		assertEquals(200, g1.getVertexCount());
		assertEquals(3734, g1.getEdgeCount());
	}	
}