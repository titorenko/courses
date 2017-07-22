package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

class BitPatternFullyConnectedGraphParser {

	public static BitPatternFullyConnectedGraph fromResource(URL resource) {
		try {
	        return fromReader(new InputStreamReader(resource.openStream()));
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private static BitPatternFullyConnectedGraph fromReader(Reader reader) throws IOException {
		int[] vertices = null;
		int bits = -1;
		try (BufferedReader br = new BufferedReader(reader)) {
			String[] header = br.readLine().split("\\s+");
			int n = Integer.parseInt(header[0]);
			bits = Integer.parseInt(header[1]);
			vertices = new int[n];
			int v = 0;
			String line = null;
			while ((line = br.readLine()) != null) {
			    String[] tokens = line.split("\\s+");
			    vertices[v] = parseLine(tokens);
				v++;
			}
		}
		return new BitPatternFullyConnectedGraph(vertices, bits);
	}

	private static int parseLine(String[] tokens) {
		int result = 0;
		int factor = 1;
		for (int i = tokens.length - 1; i >=0; i--) {
			boolean isSet = (tokens[i].charAt(0) == '1');
			if (isSet) result += factor;
			factor = factor << 1;
		}
		return result;
	}

}
