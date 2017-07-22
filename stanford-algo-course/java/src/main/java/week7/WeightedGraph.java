package week7;

import java.util.Arrays;

import common.collections.LongArrayList;

public class WeightedGraph {

    LongArrayList[] adj;

    public WeightedGraph(LongArrayList[] adj) {
        this.adj = adj;
    }
    
    public int getVertexCount() {
        return adj.length;
    }
    
    public int getEdgeCount() {
        return Arrays.stream(adj).mapToInt(a -> a.size()).sum() >>> 1;
    }

    static LongArrayList[] newAdj(int n) {
        LongArrayList[] adj = new LongArrayList[n];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LongArrayList();
        }
        return adj;
    }
}