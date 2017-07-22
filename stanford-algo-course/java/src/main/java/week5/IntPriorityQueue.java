package week5;

public interface IntPriorityQueue {

	void decreaseKey(final int element, final int key);

	boolean isEmpty();

	/** return tuple of (element, key) */
	int[] poll();

	int size();
}