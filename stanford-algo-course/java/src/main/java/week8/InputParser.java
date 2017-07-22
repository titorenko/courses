package week8;

import java.io.IOException;
import java.net.URL;


public class InputParser {

	public static WeightedGraph parseAssignmentFile() throws IOException {
		return parseResource("/clustering1.txt");
	}

	public static WeightedGraph parseResource(String resourceName) throws IOException {
		URL resource = InputParser.class.getResource(resourceName);
		return WeightedGraphParser.fromResource(resource);
	}
	
	public static BitPatternFullyConnectedGraph parseSecondAssignmentFile() throws IOException {
		URL resource = InputParser.class.getResource("/clustering_big.txt");
		return BitPatternFullyConnectedGraphParser.fromResource(resource);
	}
}
