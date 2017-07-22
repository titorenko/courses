package week5;

import java.util.List;

import week5.WeightedDigraph.Edge;

public class WeightedGraphBuilder {
	
	private List<Edge>[] adj;

	public WeightedGraphBuilder(int nVertices) {
		this.adj = WeightedDigraph.newAdj(nVertices);
	}

	public WeightedGraphBuilder addEdge(int source, int dest, int weight) {
		adj[source].add(new Edge(source, dest, weight));
		return this;
	}
	
	public WeightedDigraph build() {	
		return new WeightedDigraph(adj);
	}
	
}