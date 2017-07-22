package week4;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class DirectedGraphTest {
	
	static DirectedGraph g1;
	static DirectedGraph g2;

	@BeforeClass
	public static void init() throws IOException {
		g1 = DirectedGraph.fromFile(new File("data/graphs/scc.txt"));
		g2 = DirectedGraph.fromFile(new File("data/graphs/sccSmall.txt"));
	}
	
	@Test
	public void testDimensionsOfEx4() throws IOException {
		assertEquals(875714, g1.getVertexCount());
		assertEquals(5105043, g1.getEdgeCount());
	}	
	
	@Test
	public void testDimensionsOfEx4Reversed() throws IOException {
		DirectedGraph r1 = g1.reverse();
		assertEquals(875714, r1.getVertexCount());
		assertEquals(5105043, r1.getEdgeCount());
	}		
	
	@Test
	public void testDimenstionsOfSmall() throws IOException {
		assertEquals(9, g2.getVertexCount());
		assertEquals(11, g2.getEdgeCount());
	}
	
	public static void main(String[] args) throws IOException {
		init();
		System.out.println(g2.toDotNotation());
		System.out.println(g2.reverse().toDotNotation());
	}
}
