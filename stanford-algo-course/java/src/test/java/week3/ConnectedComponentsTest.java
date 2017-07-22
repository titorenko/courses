package week3;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import common.InputData;

public class ConnectedComponentsTest {
	
	@Test
	public void testTrivialGraphWithOneComponent() {
		UndirectedGraph g = GraphBuilder.graph(2).addEdge(0, 1).graph();
		ConnectedComponents cc = new ConnectedComponents(g);
		int n = cc.compute();
		assertEquals(1, n);
	}
	
	@Test
	public void testTrivialGraphWithTwoComponents() {
		UndirectedGraph g = GraphBuilder.graph(4).addEdge(0, 1).addEdge(2, 3).graph();
		ConnectedComponents cc = new ConnectedComponents(g);
		int n = cc.compute();
		assertEquals(2, n);
	}
	
	@Test
	public void testOnGraphFromEx3() throws IOException {
		UndirectedGraph g = InputData.week3();
		ConnectedComponents cc = new ConnectedComponents(g);
		int n = cc.compute();
		assertEquals(1, n);
	}
}
