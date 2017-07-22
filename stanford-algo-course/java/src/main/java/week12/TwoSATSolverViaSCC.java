package week12;

import java.util.Arrays;
import java.util.stream.IntStream;

import optimized.Sorts;
import week4.DirectedGraph;
import week4.KosarajuSCC;
import week4.KosarajuSCC.SCCVisitor;
import common.collections.IntArrayList;

/**
 * Solve 2-SAT problem by exploiting equivalence between 2-SAT and strongly
 * connected components.
 */
public class TwoSATSolverViaSCC {
	
	private final DirectedGraph g;

	TwoSATSolverViaSCC(TwoSATProblem twoSat) {
		twoSat = new TwoSATSimplifier(twoSat).simplify();
		this.g = toEquivalentDigraph(twoSat);
	}
	
	private DirectedGraph toEquivalentDigraph(TwoSATProblem twoSat) {
		int n = twoSat.getVariableCount();
		IntArrayList[] adj = DirectedGraph.newAdj(n*2+1);//ignore 0 index, have one node for simple var and another for negation
		for (int i = 0; i < twoSat.getClauseCount(); i++) {
			int from = twoSat.leftVars[i] + n;
			int to = twoSat.rightVars[i] + n;
			adj[negate(to, n)].add(from);
			adj[negate(from, n)].add(to);
		}
		return new DirectedGraph(adj);
	}
	
	int negate(int vertex, int n) {
		int canonical = vertex - n;
		int negated = -canonical;
		return negated+n;
	}
	
	boolean isSolvable() {
		int n = (g.getVertexCount() - 1) >> 1;
		SolvabilityDetector detector = new SolvabilityDetector(n);
		KosarajuSCC kscc = new KosarajuSCC(g, detector);
		kscc.run();
		return detector.isSolvable();
	}
	
	static class SolvabilityDetector implements SCCVisitor {

		private boolean isSolvable = true;
		private final IntArrayList currentVertices = new IntArrayList();
		private final int n;
		
		SolvabilityDetector(int n) {
			this.n = n;
		}
		
		boolean isSolvable() {
			return isSolvable;
		}
		
		@Override
		public void componentStart() {
			currentVertices.clear();
		}
		
		@Override
		public void visit(int vertex) {
			currentVertices.add(vertex);
		}
		
		@Override
		public void componentEnd() {
			if (currentVertices.size() < 4 || !isSolvable) return;
			isSolvable = isSolvable && !containsOppositeVariables();
		}

		private boolean containsOppositeVariables() {
			final int[] buffer = currentVertices.buffer;
			int size = currentVertices.size();
			Arrays.sort(buffer, 0, size);
			int oppositeVertexSum = n << 1;
			return findIntegersWithGivenSum(buffer, 0, size, oppositeVertexSum);
		}

		private static boolean findIntegersWithGivenSum(final int[] a, int from, int to, final int sum) {
			assert Sorts.isSorted(a, from, to);
			int i = 0;
			int j = to - 1;
			int left = a[i];
			int right = a[j];
			while(i < j) {
				final int s = left + right;
				if (s < sum) {
					left = a[i++];
				} else if (s > sum) {
					right = a[j--];
				} else {
					return true;
				}
			}
			return false;
		}
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		TwoSATProblem[] assignments = InputParser.assignments().toArray(TwoSATProblem[]::new);
		long duration = System.currentTimeMillis() - start;
		System.out.println("Parsed input in "+duration+" millis");
		start = System.currentTimeMillis();
		IntStream.range(0, 6).forEach(i -> {;
			boolean isSolvable = new TwoSATSolverViaSCC(assignments[i]).isSolvable();
			System.out.println("Assignment "+i+": "+isSolvable);
		});
		duration = System.currentTimeMillis() - start;
		System.out.println("Total solution time: "+duration);
	}
}