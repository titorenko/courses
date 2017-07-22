package week3;

import common.collections.queue.BoundedIntQueue;
import common.collections.queue.IntQueue;

public class ConnectedComponents {
	private final Graph g;
	private final boolean[] isVisited;
	private final IntQueue q;
	
	private int nComponents;

	public ConnectedComponents(Graph g) {
		this.g = g;
		this.isVisited = new boolean[g.getVertexCount()];
		this.nComponents = 0;
		this.q = new BoundedIntQueue(g.getVertexCount());
	}
	
	public int compute() {
		for (int i = 0; i < g.getVertexCount(); i++) {
			if(!isVisited[i]) {
				nComponents++;
				bfs(i);
			}		
		}
		return nComponents;
	}

	private void bfs(int i) {
		q.add(i);
		isVisited[i] = true;
		while(!q.isEmpty()) {
			int v1= q.poll();
			g.adj()[v1].forAll(v2 -> { 
				if (!isVisited[v2]) {
					isVisited[v2] = true;
					q.add(v2);				
				}
			});
		}
	}
}