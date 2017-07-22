package week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import common.PrimitivesParser;
import common.collections.IntArrayList;

class DigraphParser {
	public static DirectedGraph fromFile(File file) throws IOException {
		return fromReader(new FileReader(file));
	}
	
	public static DirectedGraph fromString(String string) throws IOException {
		return fromReader(new StringReader(string));
	}

	private static DirectedGraph fromReader(Reader reader) throws IOException {
		IntArrayList[] adj = null;
		try (BufferedReader br = new BufferedReader(reader)) {
			int n = Integer.parseInt(br.readLine());
			adj = DirectedGraph.newAdj(n);
			String line = null;
			while ((line = br.readLine()) != null) {
				int[] edge = parseLine(line);
				adj[edge[0]].add(edge[1]);
			}
		}
		return new DirectedGraph(adj);
	}
	
	private static int[] parseLine(String line) {
		int splitIdx = line.indexOf(" ");
		int source = PrimitivesParser.parsePositiveInt(line, 0, splitIdx);
		int dest = PrimitivesParser.parsePositiveInt(line, splitIdx+1, line.length());
	    return new int[] {source-1, dest-1};
	}
}