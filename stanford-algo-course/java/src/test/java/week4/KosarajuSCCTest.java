package week4;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import week3.GraphBuilder;
import week4.KosarajuSCC.SCCVisitor;
import common.InputData;

public class KosarajuSCCTest {
	
	private DirectedGraph g = InputData.week4small();

	@Test
	public void testFirstPass() {
		KosarajuSCC kosarajuSCC = new KosarajuSCC(g, SCCVisitor.DUMMY);
		kosarajuSCC.firstPass();
		assertArrayEquals(new int[]{6, 3, 0, 8, 5, 7, 1, 4, 2}, kosarajuSCC.getFinishingTimes());
	}
	
	
	@Test
	public void testComponentSmall() {
		DirectedGraph gs = GraphBuilder.graph(4).addEdge(0, 1).addEdge(1, 0).addEdge(2, 3).digraph();
		KosarajuSCCClient kosarajuSCC = new KosarajuSCCClient(gs);
		int[] sizes = kosarajuSCC.run();
		assertArrayEquals(new int[]{2, 1, 1}, sizes);
	}
	
	@Test
	public void testComponentSizes() {
		KosarajuSCCClient kosarajuSCC = new KosarajuSCCClient(g, 4);
		int[] sizes = kosarajuSCC.run();
		assertArrayEquals(new int[]{3, 3, 3}, sizes);
	}
	
	@Test
	public void testComponentSizesForTc1() {
		KosarajuSCCClient kosarajuSCC = new KosarajuSCCClient(InputData.week4tc1());
		int[] sizes = kosarajuSCC.run();
		assertArrayEquals(new int[]{6, 3, 2, 1}, sizes);
	}
}