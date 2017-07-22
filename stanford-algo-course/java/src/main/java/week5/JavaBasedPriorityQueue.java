package week5;

import java.util.Arrays;
import java.util.PriorityQueue;

class JavaBasedPriorityQueue implements IntPriorityQueue {
	
	private final PriorityQueue<VertexWithDistance> q;
	private VertexWithDistance[] elements;

	JavaBasedPriorityQueue(int capacity, int initialKeyValue) {
		this.elements = new VertexWithDistance[capacity];
		for (int i = 0; i < capacity; i++) {
			elements[i] = new VertexWithDistance(i, initialKeyValue);
		}
		this.q = new PriorityQueue<VertexWithDistance>(Arrays.asList(elements));
	}
	
	@Override
	public void decreaseKey(int element, int key) {
		if (key < elements[element].distance) {
			VertexWithDistance updated = new VertexWithDistance(element, key);
			q.remove(updated);
			q.add(updated);
			elements[element] = updated;
		}
	}

	@Override
	public boolean isEmpty() {
		return q.isEmpty();
	}

	@Override
	public int[] poll() {
		VertexWithDistance v = q.poll();
		return new int[]{v.vertex, v.distance};
	}
	
	@Override
	public int size() {
		return q.size();
	}
	
	private static final class VertexWithDistance implements Comparable<VertexWithDistance>{

		private int vertex;
		private int distance;

		public VertexWithDistance(int vertex, int distance) {
			this.vertex = vertex;
			this.distance = distance;
		}

		@Override
		public int compareTo(VertexWithDistance other) {
			return distance - other.distance;
		}
		
		@Override
		public int hashCode() {
			return vertex;
		}
		
		@Override
		public boolean equals(Object obj) {
			VertexWithDistance other = (VertexWithDistance) obj;
			return vertex == other.vertex;
		}
		
		@Override
		public String toString() {
			return vertex+" ["+distance+"]";
		}
	}
}