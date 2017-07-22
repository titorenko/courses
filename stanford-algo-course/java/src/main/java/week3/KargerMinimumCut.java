package week3;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

import week3.UndirectedGraph.Edge;

import common.InputData;

public class KargerMinimumCut {
	
	private final UndirectedGraph g;
	
	public KargerMinimumCut(UndirectedGraph g) {
		this.g = g;
	}
	
	public int calculate() {
		return calculate(Math.log(g.getVertexCount()));
	}

	/**
	 * Calculate min cut with probability of correct answer
	 * at least confidence/e
	 */
	public int calculate(double confidence) {
		int n = g.getVertexCount();
		int nIterations = (int) (n * n * confidence);
		System.out.println("Running Karger min-cut for "+nIterations+" iterations");
		final Edge[] edges = g.getEdges();
		return IntStream.range(0, nIterations).parallel().
			map(i -> minCutOneRound(edges)).min().getAsInt();
	}
	
	int minCutOneRound(Edge[] edges){
		final Random r = new Random();
		final UF uf = new UF(g.getVertexCount());
		final int m = edges.length;
		do {
			final int edgeIndex = r.nextInt(m);
			final Edge edge = edges[edgeIndex];
			uf.union(edge.v1, edge.v2);
		} while (uf.count() > 2);
		return countNonSelfLoops(edges, uf);
	}
	
	private int countNonSelfLoops(final Edge[] edges, final UF uf) {
		int count = 0;
		final int[] components = new int[g.getVertexCount()];
		for (int i = 0; i < g.getVertexCount(); i++) {
			components[i] = uf.find(i);
		}
		for (Edge e : edges) {
			if (components[e.v1] != components[e.v2]) count++;
		}
		return count;
	}

	public static void main(String[] args) throws IOException {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", 
				Runtime.getRuntime().availableProcessors() * 1.5 +"");
		UndirectedGraph g = InputData.week3();
		KargerMinimumCut minCutAlgo = new KargerMinimumCut(g);
		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			int minCut = minCutAlgo.calculate();
			long dur = System.currentTimeMillis() - start;
			System.out.println("Mincut is "+minCut+", computed in "+dur);
		}
	}
}