package week7;

import java.io.IOException;

import week5.BinaryHeapPriorityQueue;
import week7.WeightedGraphParser.WeightedEdge;

public class PrimsMST {
    
    private final WeightedGraph g;
    private final BinaryHeapPriorityQueue q;

    public PrimsMST(WeightedGraph g) {
        this.g = g;
        this.q = new BinaryHeapPriorityQueue(g.getVertexCount(), Integer.MAX_VALUE);
    }
    
    public long computeMstCost() {
        long cost = 0;
        q.decreaseKey(0, 0);
        final boolean[] explored = new boolean[g.getVertexCount()];
        while(!q.isEmpty()) {
            final int[] head = q.poll();
            final int vertex = head[0];
            final int key = head[1];
            explored[vertex] = true;
            cost += key;
            g.adj[vertex].forAll((long edge) -> {
                final int nextVertex = WeightedEdge.vertex(edge);
                if (!explored[nextVertex]) {
                    final int weight = WeightedEdge.weight(edge);
                    q.decreaseKey(nextVertex, weight);
                }
            });
        }
        return cost;
    }
    
    public static void main(String[] args) throws IOException {
        WeightedGraph g = WeightedGraphParser.fromResource(PrimsMST.class.getResource("/prims_edges.txt"));
        PrimsMST mst = new PrimsMST(g);
        System.out.println(mst.computeMstCost());
    }
}
