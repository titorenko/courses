package week5;


/**
 * Priority queue for integers implemented as binary heap. Smallest elements are at the head of the queue.
 * Integers are prioritized based on their keys. This queue is quite specialized for use in Dijkstra shortest path search.
 */
public class BinaryHeapPriorityQueue implements IntPriorityQueue {
	//queue content, top bits are key (or weight) and bottoms bits are element ids
	private final long[] elements;
	//pointers into queue for quick lookup of given element
	private final int[] pointers;
	private int size;

	public BinaryHeapPriorityQueue(int capacity, int initialKeyValue) {
		this.elements = new long[capacity];
		this.pointers = new int[capacity];
		this.size = capacity;
		
		for (int i = 0; i < elements.length; i++) {
			set(i, pack(i, initialKeyValue));
		}
	}
	
	public void decreaseKey(final int element, final int key) {
		int k = pointers[element];
		if (key < weight(elements[k])) {
			elements[k] = pack(element, key);
			bubleUp(k, elements[k]);
		}
	}

	public boolean isEmpty() {
		return size <= 0;
	}
	
	public int size() {
		return size;
	}

	public int[] poll() {
		if (isEmpty()) 
			throw new IllegalStateException("Queue is empty");
		final long result = elements[0];
		size--;
		if (size > 0) {
			bubleDown(0, elements[size]);
		}
		return new int[] {vertex(result), weight(result)};
	}
	
	private void bubleUp(int k, final long v) {
		while (k > 0) {
			int parent = (k-1) >>> 1;
			if (elements[parent] < v) break;
			set(k, elements[parent]);
			k = parent;
		}
		set(k, v);
	}
	
	 /**
     * Inserts value v at position k, maintaining heap invariant by
     * demoting v down the tree repeatedly until it is less than or
     * equal to its children or is a leaf.
     *
     * @param k the position to fill
     * @param v value to insert
     */
	private void bubleDown(int k, final long v) {
		final int half = size >>> 1; // loop while a non-leaf
		while (k < half) {
			int child = (k << 1) + 1;//find smaller child index & element 
			final int right = child + 1;
			long c = elements[child];
			if (right < size && c > elements[right]) {
				c = elements[child = right];
			}
			if (v < c) break;
			set(k, c);
			k = child;
		}
		set(k, v);
	}
	
	private void set(final int index, final long ek) {
		elements[index] = ek;
		pointers[vertex(ek)] = index;
	}
	
    private static long pack(int v, int weight) {
        assert v >= 0;
        return ((long) weight << 32) | v;
    }

    private static int vertex(long edge) {
        return (int) (edge & 0xFFFF_FFFF);
    }

    private static int weight(long edge) {
        return (int) (edge >> 32);
    }
}