package week5;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import week5.WeightedDigraphParser.Mode;

import common.InputData;

public class WeightedDigraph {

    final List<Edge>[] adj;

    public WeightedDigraph(List<Edge>[] adj) {
        this.adj = adj;
    }

    public List<Edge>[] getAdj() {
        return adj;
    }

    public int getVertexCount() {
        return adj.length;
    }

    public int getEdgeCount() {
        return Arrays.stream(adj).mapToInt(List::size).sum();
    }

    public Stream<Edge> getEdgeStream() {
        return Arrays.stream(adj).flatMap(edges -> edges.stream());
    }

    public Edge[] getEdges() {
        return getEdgeStream().toArray(Edge[]::new);
    }

    public String toDotNotation() {
        StringBuffer result = new StringBuffer();
        result.append("digraph G {\n");
        for (int v1 = 0; v1 < adj.length; v1++) {
            for (int i = 0; i < adj[v1].size(); i++) {
                Edge e = adj[v1].get(i);
                result.append(e.toString());
                result.append(";\n");
            }
        }
        result.append("}");
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public static List<Edge>[] newAdj(int n) {
        List<Edge>[] adj = new List[n];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<Edge>();
        }
        return adj;
    }

    public static WeightedDigraph fromResource(URL resource) throws IOException {
        return new WeightedDigraphParser(Mode.WEEK5).fromResource(resource);
    }

    public static class Edge {
        public final int from;
        public final int to;
        public final int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        
        @Override
        public String toString() {
            return String.format("%d -> %d [weight=%d]", from + 1, to + 1, weight);
        }
    }

    public static void main(String[] args) {
        System.out.println(InputData.week5().toDotNotation());
    }
}
