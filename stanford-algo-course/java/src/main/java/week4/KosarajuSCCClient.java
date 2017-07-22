package week4;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

import week4.KosarajuSCC.SCCVisitor;

public class KosarajuSCCClient {
	
	private final int topComponents;
	private final PriorityQueue<Integer> bestSizes;
	private final KosarajuSCC kscc;
	
	public KosarajuSCCClient(DirectedGraph g) {
		this(g, 5);
	}
	
	/**
	 * @param g - Directed graph to search for strongly connected components
	 * @param topComponents - how many strongly connected with most vertices to output
	 */
	public KosarajuSCCClient(DirectedGraph g, int topComponents) {
		this.kscc = new KosarajuSCC(g, new ComponentSizeCalculator());
		this.topComponents = topComponents;
		this.bestSizes = new PriorityQueue<>(topComponents);
	}
	
	public int[] run() {
		kscc.run();
		return getComponentSizes();
	}
	
	private int[] getComponentSizes() {
		int n = Math.min(topComponents, bestSizes.size());
		int[] result = new int[n];
		for (int i = n-1; i>=0; i--) {
			result[i] = bestSizes.poll();
		}
		return result;
	}
	
	class ComponentSizeCalculator implements SCCVisitor {
		
		private int currentComponentSize;
		
		@Override
		public void componentStart() {
			currentComponentSize = 0;
		}
		
		@Override
		public void visit(int vertex) {
			currentComponentSize++;
		}
		
		@Override
		public void componentEnd() {
			if (bestSizes.size() < topComponents) {
				bestSizes.add(currentComponentSize);
			} else if (bestSizes.peek() < currentComponentSize) {
				bestSizes.poll();
				bestSizes.add(currentComponentSize);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		DirectedGraph g = DirectedGraph.fromFile(new File("data/graphs/scc.txt"));
		KosarajuSCCClient scc = new KosarajuSCCClient(g);
		System.out.println("Read file & inverted in: "+(System.currentTimeMillis()-start));
		for (int i = 0; i < 1000; i++) {
			start = System.currentTimeMillis();
			int[] sizes = scc.run();
			System.out.println("Component sizes: "+Arrays.toString(sizes));
			System.out.println("Ran Kosaraju in: "+(System.currentTimeMillis()-start));
		}
	}
}