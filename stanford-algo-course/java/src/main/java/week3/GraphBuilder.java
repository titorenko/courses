package week3;

import week4.DirectedGraph;

import common.collections.IntArrayList;


public class GraphBuilder {
	
	private IntArrayList[] adj;

	public GraphBuilder(int nVertices) {
		this.adj = DirectedGraph.newAdj(nVertices);
	}

	public static GraphBuilder graph(int nVertices) {
		return new GraphBuilder(nVertices);
	}
	
	public GraphBuilder addEdge(int source, int dest) {
		adj[source].add(dest);
		return this;
	}
	
	public UndirectedGraph graph() {	
		return new UndirectedGraph(adj);
	}
	
	public DirectedGraph digraph() {
		return new DirectedGraph(adj);
	}
}