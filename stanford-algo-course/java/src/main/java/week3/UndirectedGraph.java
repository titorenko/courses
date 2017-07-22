package week3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.io.Files;
import common.collections.IntArrayList;

/**
	Non-directed mutable graph with parallel edges
*/
public class UndirectedGraph implements Graph {
	
	final IntArrayList[] adj;
	
	UndirectedGraph(IntArrayList[] adj) {
		this.adj = adj;
	}
	
	@Override
	public IntArrayList[] adj() {
		return adj;
	}
	
	@Override
	public int getEdgeCount() {
		return Graph.super.getEdgeCount() >>> 1;
	}
	
	public Edge[] getEdges() {
		Edge[] result = new Edge[getEdgeCount()];
		int i = 0;
		for (int v1 = 0; v1 < adj.length; v1++) {
			for (int j = 0; j < adj[v1].size(); j++) {
				int v2 = adj[v1].get(j);
				if (v1 < v2) {
					result[i] = new Edge(v1, v2);
					i++;
				}
			}
		}
		return result;
	}

	public static UndirectedGraph fromFile(File file) throws IOException {
		List<String> lines = Files.readLines(file, Charset.defaultCharset());
		List<IntArrayList> adj = lines.stream().map(UndirectedGraph::parseLine).collect(Collectors.toList());
		return new UndirectedGraph(adj.toArray(new IntArrayList[0]));
	}
	
	private static IntArrayList parseLine(String line) {
		IntStream values = Arrays.stream(line.split("\t")).skip(1).mapToInt(
				(String token) -> Integer.parseInt(token) - 1);
		return new IntArrayList(values.toArray());
	}
	
	public static class Edge {
		final int v1;
		final int v2;
		
		public Edge(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
	}
}