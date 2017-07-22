package week8;

public class Edge implements Comparable<Edge> {
	public final int from;
	public final int to;
	public final int weight;

	public Edge(int from, int to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public boolean isForward() {
		return from < to;
	}

	@Override
	public int compareTo(Edge other) {
		return weight - other.weight;
	}
}