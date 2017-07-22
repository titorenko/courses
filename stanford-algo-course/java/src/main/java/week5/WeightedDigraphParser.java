package week5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import week5.WeightedDigraph.Edge;

public class WeightedDigraphParser {
    public static enum Mode {
        WEEK5, WEEK10
    }

    private Mode mode;
    
    public WeightedDigraphParser(Mode mode) {
        this.mode = mode;
    }
    
	public WeightedDigraph fromFile(File file) throws IOException {
		return fromReader(new FileReader(file));
	}
	
	public WeightedDigraph fromResource(URL resource) throws IOException {
		return fromReader(new InputStreamReader(resource.openStream()));
	}

	private WeightedDigraph fromReader(Reader reader) throws IOException {
		List<Edge>[] adj = null;
		try (BufferedReader br = new BufferedReader(reader)) {
			int n = Integer.parseInt(br.readLine().split(" ")[0]);
			adj = WeightedDigraph.newAdj(n);
			String line = null;
			while ((line = br.readLine()) != null) {
				List<Edge> edges = parseLine(line);
				for (Edge edge : edges) {
					adj[edge.from].add(edge);
				}
			}
		}
		return new WeightedDigraph(adj);
	}
	
	private List<Edge> parseLine(String s) {
	    if (mode == Mode.WEEK5) {
	        return parseLineWeek5(s);
	    } else if (mode == Mode.WEEK10) {
	        return parseLineWeek10(s);
	    }
	    throw new UnsupportedOperationException("Do not know how to parse in mode: "+mode);
	}
	
	private List<Edge> parseLineWeek10(String s) {
		String[] tokens = s.split("\\s+");
		final int from = toVertex(tokens[0]);
		final int to = toVertex(tokens[1]);
		final int weight = Integer.parseInt(tokens[2]);
		return Collections.singletonList(new Edge(from, to, weight));
	}
	
	private List<Edge> parseLineWeek5(String s) {
        String[] tokens = s.split("\\s+");
        final int from = toVertex(tokens[0]);
        List<Edge> result = new ArrayList<Edge>(tokens.length-1);
        for (int i = 1; i < tokens.length; i++) {
            String[] split = tokens[i].split(",");
            int to = toVertex(split[0]);
            int weight = Integer.parseInt(split[1]);
            result.add(new Edge(from, to, weight));
        }
        return result;
    }

    private int toVertex(String token) {
        return Integer.parseInt(token)-1;
    }
		
}