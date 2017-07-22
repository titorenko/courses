package week5;


public enum DijkstraSolvers {
	JAVA((g, s) -> new DijkstraShortestPathJavaPQ(g, s)), 
	NAIVE((g, s) -> new DijkstraShortestPathNaivePQ(g, s)), 
	BINARY((g, s) -> new DijkstraShortestPathBinaryHeapPQ(g, s));

	private DijkstraFactory factory;

	private DijkstraSolvers(DijkstraFactory factory) {
		this.factory = factory;
	}

	public DijkstraShortestPath solverFor(WeightedDigraph g, int source) {
		return factory.getSolver(g, source);
	}
	
	@FunctionalInterface
	private static interface DijkstraFactory {
		DijkstraShortestPath getSolver(WeightedDigraph g, int source);
	}

	private static class DijkstraShortestPathBinaryHeapPQ extends DijkstraShortestPath {
		DijkstraShortestPathBinaryHeapPQ(WeightedDigraph g, int sourceVertex) {
			super(g, sourceVertex, (c, v) -> new BinaryHeapPriorityQueue(c, v));
		}
	}

	private static class DijkstraShortestPathNaivePQ extends DijkstraShortestPath {
		DijkstraShortestPathNaivePQ(WeightedDigraph g, int sourceVertex) {
			super(g, sourceVertex, (c, v) -> new NaivePriorityQueue(c, v));
		}
	}

	private static class DijkstraShortestPathJavaPQ extends DijkstraShortestPath {
		DijkstraShortestPathJavaPQ(WeightedDigraph g, int sourceVertex) {
			super(g, sourceVertex, (c, v) -> new JavaBasedPriorityQueue(c, v));
		}
	}
}