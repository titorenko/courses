

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int head;
    private int tail;

    public RandomizedQueue() {
        this.items = newArray(16);
    }

    public boolean isEmpty() {
        return tail == head;
    }

    public int size() {
        return (tail - head) & (items.length - 1);
    }
    
    public void enqueue(Item item) {
        addFirst(item);
        int index = (head + StdRandom.uniform(size())) & (items.length - 1);
        swap(head, index);
    }

    private void swap(int i1, int i2) {
        Item x = items[i1];
        items[i1] = items[i2];
        items[i2] = x;
    }

    public Item dequeue() {
        return removeFirst();
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int idx = StdRandom.uniform(size());
        return items[(head+idx) & (items.length-1)];
    }

    private void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        head = (head - 1) & (items.length - 1);
        if (head == tail) doubleCapacity();
        items[head] = item;
    }


    private Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item result = items[head];
        items[head] = null;
        head = (head + 1) & (items.length - 1);
        if (shouldShrink()) halveCapacity(); 
        return result;
    }


    private boolean shouldShrink() {
        int size = size();
        return size > 2 && size <= items.length / 4;
    }

    private void doubleCapacity() {
        assert head == tail;
        final int size = items.length;
        final int newSize = size << 1;
        if (newSize < 0) 
            throw new RuntimeException("Can not double capacity to double of "+size);
        final int headLength = size - head;
        final Item[] newItems = newArray(newSize);
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
        final Item[] newItems = newArray(newCapacity);
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
    
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int idx = 0;
        private final Item[] shuffled;
        
        RandomizedIterator() {
            shuffled = newArray(size());
            for (int i = 0; i < shuffled.length; i++) {
                shuffled[i] = items[(head+i) & (items.length-1)];
            }
            StdRandom.shuffle(shuffled);
        }
        
        @Override
        public boolean hasNext() {
            return idx < shuffled.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffled[idx++];
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] newArray(int size) {
        return (T[]) new Object[size];
    }
}