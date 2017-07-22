package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import common.Array;

public class Deque<Item> implements Iterable<Item> {

    private static final int INITIAL_CAPACITY = 8;
    private Item[] items;
    private int head;
    private int tail;

    public Deque() {
        this(INITIAL_CAPACITY);
    }

    Deque(int capacity) {
        assertPowerOfTwo(capacity);
        this.items = Array.newArray(capacity);
    }

    private void assertPowerOfTwo(int capacity) {
        if (capacity == 1) throw new IllegalArgumentException("Unsupported capacity: "+capacity);
        while (capacity > 1) {
            if (capacity % 2 != 0) throw new IllegalArgumentException("Unsupported capacity: "+capacity);
            capacity = capacity / 2;
        }
    }

    public boolean isEmpty() {
        return tail == head;
    }

    public int size() {
        return (tail - head) & (items.length - 1);
    }

    public void addFirst(Item item) {
        head = (head - 1) & (items.length - 1);
        if (head == tail) doubleCapacity();
        items[head] = item;
    }


    public void addLast(Item item) {
        items[tail] = item;
        tail = (tail + 1) & (items.length - 1);
        if (head == tail) doubleCapacity();
    }
    
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item result = items[head];
        items[head] = null;
        head = (head + 1) & (items.length - 1);
        if (shouldShrink()) halveCapacity(); 
        return result;
    }


    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        tail = (tail - 1) & (items.length - 1);
        Item result = items[tail];
        items[tail] = null;
        if (shouldShrink()) halveCapacity(); 
        return result;
    }

    private boolean shouldShrink() {
        int size = size();
        return size <= items.length >> 2 && size > INITIAL_CAPACITY;
    }

    private void doubleCapacity() {
        assert head == tail;
        final int size = items.length;
        final int newSize = size << 1;
        if (newSize < 0) throw new RuntimeException("Can not double capacity to double of "+size);
        final int headLength = size - head;
        final Item[] newItems = Array.newArray(newSize);
        System.arraycopy(items, head, newItems, 0, headLength);
        System.arraycopy(items, 0, newItems, headLength, head);
        this.items = newItems;
        this.head = 0;
        this.tail = size;
    }
    
    private void halveCapacity() {
        assert head != tail;
        final int capacity = items.length;
        final int newCapacity = capacity >> 1;
        final int size = size();
        final Item[] newItems = Array.newArray(newCapacity);
        if (head < tail) {
            System.arraycopy(items, head, newItems, 0, size);
        } else {
            System.arraycopy(items, head, newItems, 0, capacity-head);
            System.arraycopy(items, 0, newItems, capacity - head, tail);
        }        
        this.items = newItems;
        this.head = 0;
        this.tail = size;
    }
    
    int estimateMemoryUsage(int itemSize) {
        return 16 + 4*2 + 24 + items.length * (4 + itemSize);
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        int idx = 0;
        final int size = size(); 
        
        @Override
        public boolean hasNext() {
            return idx < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item result = items[(head+idx) & (items.length-1)];
            idx++;
            return result;
        }
    }
}