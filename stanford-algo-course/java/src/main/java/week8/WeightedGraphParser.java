package week8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

public class WeightedGraphParser {
	
	public static WeightedGraph fromFile(File file) throws IOException {
		return fromReader(new FileReader(file));
	}
	
	public static WeightedGraph fromResource(URL resource) {
	    try {
	        return fromReader(new InputStreamReader(resource.openStream()));
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

	private static WeightedGraph fromReader(Reader reader) throws IOException {
		List<Edge>[] adj = null;
		try (BufferedReader br = new BufferedReader(reader)) {
			int n = Integer.parseInt(br.readLine().split("\\s+")[0]);
			adj = WeightedGraph.newAdj(n);
			String line = null;
			while ((line = br.readLine()) != null) {
			    String[] tokens = line.split("\\s+");
		        final int from = Integer.parseInt(tokens[0])-1;
		        final int to = Integer.parseInt(tokens[1]) - 1;
		        final int weight = Integer.parseInt(tokens[2]);
				adj[from].add(new Edge(from, to, weight));
				adj[to].add(new Edge(to, from, weight));
			}
		}
		return new WeightedGraph(adj);
	}
}