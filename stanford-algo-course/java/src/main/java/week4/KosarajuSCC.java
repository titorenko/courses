package week4;

import gnu.trove.stack.TIntStack;
import gnu.trove.stack.array.TIntArrayStack;

import java.util.Arrays;

/**
 * Kosaraju's algorithm for finding strongly connected components in directed graph
 */
public class KosarajuSCC {

	public static interface SCCVisitor {
		public static final SCCVisitor DUMMY = new SCCVisitor() {
			public void componentStart() {}

			public void visit(int vertex) {}

			public void componentEnd() {}
			
		};
		void componentStart();
		void visit(int vertex);
		void componentEnd();
	}

	private final DirectedGraph g;
	private final DirectedGraph gRev;
	
	private final boolean[] isExplored;
	private final TIntStack finishingTimes;
	private final SCCVisitor sccVisitor;
	
	
	public KosarajuSCC(DirectedGraph g, SCCVisitor sccVisitor) {
		this.g = g;
		this.gRev = g.reverse();
		this.finishingTimes = new TIntArrayStack(g.getVertexCount());
		this.isExplored = new boolean[g.getVertexCount()];
		this.sccVisitor = sccVisitor;
	}
	
	public void run() {
		Arrays.fill(isExplored, false);
		firstPass();
		Arrays.fill(isExplored, false);
		secondPass();
	}

	void firstPass() {
		for (int i = g.getVertexCount()-1; i >= 0; i--) {
			dfsFirstPass(i);
		}
	}
	
	private void dfsFirstPass(int i) {
		if (isExplored[i]) return;
		isExplored[i] = true;
		gRev.adj[i].forAll(this::dfsFirstPass);
		finishingTimes.push(i);
	}

	void secondPass() {
		while(finishingTimes.size() > 0) {
			int i = finishingTimes.pop();
			if (!isExplored[i]) {
				sccVisitor.componentStart();
				dfsSecondPass(i);
				sccVisitor.componentEnd();
			}			
		}
	}
	
	private void dfsSecondPass(int i) {
		if (isExplored[i]) return;
		isExplored[i] = true;
		sccVisitor.visit(i);
		for (int v = 0; v < g.adj[i].size(); v++) {
			dfsSecondPass(g.adj[i].get(v)); 			
		}
	}
	
	int[] getFinishingTimes() {
		return finishingTimes.toArray();
	}
}