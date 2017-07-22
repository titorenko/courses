package week6;

import gnu.trove.impl.HashFunctions;

/**
 * Simple long bag and no resizing. 
 * Slightly outperforms Trove's open addressing hash set.
 */
public class LongBag {
	private static final int MAX_LOAD = 4;

	private final long[][] buckets;
	private final byte[] bucketSizes;

	private int size = 0;

	private final byte shift;

	public LongBag(int capacityHint) {
		int power = (byte) (Math.log(capacityHint)/Math.log(2) + 1);
		int capacity = 1 << power;
		this.shift = (byte) (32 - power);
		this.buckets = new long[capacity][];
		this.bucketSizes = new byte[capacity];
	}
	
	public void add(long l) {
		final int bucketIdx = getBucketOf(l);
		final long[] bucket = buckets[bucketIdx];
		if (bucket == null) {
			long[] newBucket = new long[MAX_LOAD];
			buckets[bucketIdx] = newBucket;
			newBucket[0] = l;
			bucketSizes[bucketIdx] = 1;
			size++;
		} else {
			byte bucketSize = bucketSizes[bucketIdx];
			bucket[bucketSize] = l;
			bucketSizes[bucketIdx] = ++bucketSize;
			size++;
		}
	}

	private int getBucketOf(long l) {
		final int hash = HashFunctions.hash(l) & 0x7fffffff;
		return hash >> shift;
	}

	public void addAll(long[] longs) {
		for (long l : longs)
			add(l);
	}

	public boolean contains(final long l) {
		final int bucketIdx = getBucketOf(l);
		final long[] bucket = buckets[bucketIdx];
		if (bucket == null) return false;
		for (int i = 0; i < bucketSizes[bucketIdx]; i++) {
			if (bucket[i] == l)
				return true;
		}
		return false;
	}


	public int size() {
		return size;
	}

	public long[] toArray() {
		final long[] result = new long[size];
		int idx = 0;
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != null) {
				for (int j = 0; j < bucketSizes[i]; j++) {
					result[idx] = buckets[i][j];
					idx++;
				}
			}
		}
		return result;
	}
}