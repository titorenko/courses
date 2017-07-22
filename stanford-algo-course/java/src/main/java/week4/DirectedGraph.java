package week4;

import java.io.File;
import java.io.IOException;

import week3.Graph;

import common.collections.IntArrayList;

public class DirectedGraph implements Graph {
	public final IntArrayList[] adj;
	
	public DirectedGraph(IntArrayList[] adj) {
		this.adj = adj;
	}
	
	public IntArrayList[] adj() {
		return adj;
	}
	
	public DirectedGraph reverse() {
		IntArrayList[] adjT = newAdj(adj.length);
		for (int v1 = 0; v1 < adj.length; v1++) {
			final int v1f = v1;
			adj[v1f].forAll(v2 -> adjT[v2].add(v1f));		
		}
		return new DirectedGraph(adjT);
	}

	public static IntArrayList[] newAdj(int n) {
		IntArrayList[] adj = new IntArrayList[n];
		for (int i = 0; i < n; i++) {
			adj[i] = new IntArrayList();
		}
		return adj;
	}
	
	public String toDotNotation() {
		StringBuffer result = new StringBuffer();
		result.append("digraph G {\n");
		for (int v1 = 0; v1 < adj.length; v1++) {
			for (int i = 0; i < adj[v1].size(); i++) {
				result.append(String.format("%d -> %d;\n", v1+1, adj[v1].get(i)+1));
			}
		}
		result.append("}");
		return result.toString();
	}

	public static DirectedGraph fromFile(File file) throws IOException {
		return DigraphParser.fromFile(file);
	}

	public DirectedGraph toUndirectedGraph() {
		return union(reverse());
	}

	public DirectedGraph union(DirectedGraph other) {
		int n = Math.max(getVertexCount(), other.getVertexCount());
		IntArrayList[] adj = newAdj(n);
		for (int i = 0; i < adj.length; i++) {
			if (i < getVertexCount()) adj[i].addAll(adj[i]);
			if (i < other.getVertexCount()) adj[i].addAll(other.adj[i]);
		}
		return new DirectedGraph(adj);
	}
}