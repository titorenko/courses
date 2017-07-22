package week5;

import java.util.Arrays;

public class NaivePriorityQueue implements IntPriorityQueue {
	private final int[] elements;
	private final boolean[] isSet;
	private int size;

	NaivePriorityQueue(int capacity, int initialValue) {
		this.elements = new int[capacity];
		this.isSet = new boolean[capacity];
		this.size = capacity;
		
		Arrays.fill(elements, initialValue);
		Arrays.fill(isSet, true);
	}
	
	public void decreaseKey(int v, int key) {
		if (key < elements[v]) elements[v] = key;
	}

	public boolean isEmpty() {
		return size <= 0;
	}

	public int[] poll() {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < elements.length; i++) {
			if (!isSet[i]) continue;
			if (elements[i] < min) {
				min = elements[i];
				index = i;
			}
		}
		isSet[index] = false;
		size--;
		return new int[]{index, min};
	}

	@Override
	public int size() {
		return size;
	}
}