package week8;

public class BitPatternFullyConnectedGraph {

	private int[] vertices;
	private int bits;

	public BitPatternFullyConnectedGraph(int[] vertices, int bits) {
		this.bits = bits;
		this.vertices = vertices;
	}

	public int getVertexLabel(int v) {
		return vertices[v];
	}
	
	public int getVertexCount() {
	    return vertices.length;
	}
	
	public int getWeight(int v1, int v2) {
		int xor = vertices[v1] ^ vertices[v2];
		int setBits = 0;
		for (int i = 0; i < bits; i++) {
			if ((xor & 1) == 1) setBits++;
			xor = xor >>> 1;
		}
		return setBits;
	}
	
}