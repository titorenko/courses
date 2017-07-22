package week8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeightedGraph {

    List<Edge>[] adj;

    public WeightedGraph(List<Edge>[] adj) {
        this.adj = adj;
    }
    
    public int getVertexCount() {
        return adj.length;
    }
    
    public int getEdgeCount() {
        return Arrays.stream(adj).mapToInt(a -> a.size()).sum() >>> 1;
    }
    
    public List<Edge>[] getAdj() {
		return adj;
	}
    
    public Edge[] getEdges() {
    	int edgeCount = getEdgeCount();
    	Edge[] result = new Edge[edgeCount];
    	int idx = 0;
    	for (List<Edge> edges : adj) {
    		for (Edge edge : edges) {
    			if (edge.isForward()) {
    				result[idx] = edge;
    				idx++;
    			}
			}
		}
    	return result;
    }

    @SuppressWarnings("unchecked")
    static List<Edge>[] newAdj(int n) {
		List<Edge>[] adj = new List[n];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<Edge>();
        }
        return adj;
    }
}