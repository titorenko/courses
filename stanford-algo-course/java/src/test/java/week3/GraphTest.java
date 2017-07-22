package week3;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import common.InputData;

public class GraphTest {
	
	private UndirectedGraph g1;
	private UndirectedGraph g2;
	private UndirectedGraph g3;

	@Before 
	public void init() throws IOException {
		this.g1 = UndirectedGraph.fromFile(new File("data/graphs/testCase1a.txt"));
		this.g2 = UndirectedGraph.fromFile(new File("data/graphs/testCase2a.txt"));
		this.g3 = InputData.week3();
	}

	@Test
	public void testGraph3Dimensions() throws IOException {
		assertEquals(200, g3.getVertexCount());
		assertEquals(2517, g3.getEdgeCount());
	}
	
	@Test
	public void testMinCutOnSmall() throws IOException {
		assertEquals(2, new KargerMinimumCut(g1).calculate());
		assertEquals(1, new KargerMinimumCut(g2).calculate());
	}
	
	
	@Test
	public void testMinCutOnEx3() throws IOException {
		assertEquals(17, new KargerMinimumCut(g3).calculate(0.1));
	}		  
}
