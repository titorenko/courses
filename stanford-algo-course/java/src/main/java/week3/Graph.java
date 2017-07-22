package week3;

import java.util.Arrays;
import java.util.stream.IntStream;

import common.collections.IntArrayList;

public interface Graph {
	
	IntArrayList[] adj();
	
	default IntStream adj(int v1) {
		return adj()[v1].stream();
	}
	
	default int getVertexCount() {
		return adj().length;
	}
	
	default int getEdgeCount() {
		return Arrays.stream(adj()).mapToInt(a -> a.size()).sum();
	}
}
