package week5;

import java.util.Arrays;

import week5.WeightedDigraph.Edge;
import common.InputData;

public class DijkstraShortestPath {
	
	private final int PLUS_INFINITY = Integer.MAX_VALUE;
	
	private final WeightedDigraph g;
	private final IntPriorityQueue q;
	
	/**
	 * Use dijkstra shortest path algorithm to find shortest path's
	 * to all verticies in the graph. Edge weights need to be non-negative
	 * for algorithm to work.
	 */
	public DijkstraShortestPath(WeightedDigraph g, int sourceVertex, QueueFactory qf) {
		this.g = g;
		this.q = qf.newQueue(g.getVertexCount(), PLUS_INFINITY);
		q.decreaseKey(sourceVertex, 0);
	}
	
	public int[] find() {
		final int[] result = new int[g.getVertexCount()];
		final boolean[] explored = new boolean[g.getVertexCount()];
		while(!q.isEmpty()) {
			final int[] head = q.poll();
			final int vertex = head[0];
			final int key = head[1];
			result[vertex] = key;
			explored[vertex] = true;
			for (Edge edge : g.adj[vertex]) {
				if (!explored[edge.to])
					q.decreaseKey(edge.to, key + edge.weight);
			}
		}
		return result;
	}
	
	@FunctionalInterface
	public static interface QueueFactory {
		IntPriorityQueue newQueue(int capacity, int initialKeyValue);
	}	
	
	public static void main(String[] args) {
		DijkstraShortestPath dijkstra = DijkstraSolvers.BINARY.solverFor(InputData.week5(), 0);
		int[] distances = dijkstra.find();
		int[] selections = new int[] { 
				distances[6], distances[36], distances[58], 
				distances[81], distances[98], distances[114], 
				distances[132], distances[164], distances[187], 
				distances[196]};
		System.out.println("Distance: "+Arrays.toString(selections));
	}
}

