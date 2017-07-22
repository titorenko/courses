package common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import week3.UndirectedGraph;
import week4.DirectedGraph;
import week5.WeightedDigraph;

import com.google.common.io.CharStreams;

public class InputData {
	
	public static int[] week1() {
		return parseResourceToIntArray("/IntegerArray.txt");
	}
	
	public static UndirectedGraph week3() {
		try {
			return UndirectedGraph.fromFile(new File("data/graphs/kargerMinCut.txt"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static DirectedGraph week4() {
		return loadDiGraph("data/graphs/scc.txt");
	}
	
	public static DirectedGraph week4small() {
		return loadDiGraph("data/graphs/sccSmall.txt");
	}
	
	public static DirectedGraph week4tc1() {
		return loadDiGraph("data/graphs/scc-tc1.txt");
	}
	
	public static WeightedDigraph week5() {
		try {
			return WeightedDigraph.fromResource(InputData.class.getResource("/dijkstraData.txt"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static long[] week6a() {
		try {
			return parseResourceToLongArray("/2sum.txt");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static IntStream week6b() {
		return Arrays.stream(parseResourceToIntArray("/median.txt"));
	}

	private static DirectedGraph loadDiGraph(String name) {
		try {
			return DirectedGraph.fromFile(new File(name));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static int[] parseResourceToIntArray(String resource) {
		try {
			return getLines(resource).mapToInt(Integer::parseInt).toArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static long[] parseResourceToLongArray(String resource) throws IOException {
		return getLines(resource).mapToLong(Long::parseLong).toArray();
	}
	
	private static Stream<String> getLines(String resource) throws IOException {
		InputStream is = InputData.class.getResourceAsStream(resource);
		List<String> lines = CharStreams.readLines(new InputStreamReader(is));
		return lines.stream();
	}
}