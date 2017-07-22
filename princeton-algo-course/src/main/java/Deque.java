

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] items;
    private int head;
    private int tail;

    public Deque() {
        this.items = newArray(16); //needs to be power of 2
    }

    public boolean isEmpty() {
        return tail == head;
    }

    public int size() {
        return (tail - head) & (items.length - 1);
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        head = (head - 1) & (items.length - 1);
        if (head == tail) doubleCapacity();
        items[head] = item;
    }


    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
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
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int idx = 0;
        private final int size = size(); 
        
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