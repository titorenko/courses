package week2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.Test;

public class DequeTest {
    
    @Test(expected=NoSuchElementException.class)
    public void testRemoveFromEmpty() {
        new Deque<Double>().removeFirst();
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testRemoveLastFromEmpty() {
        new Deque<Double>().removeLast();
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testEmptyIterator() {
        new Deque<Double>().iterator().next();
    }
    
    @Test
    public void testOneElement() {
        Deque<Integer> deque = new Deque<Integer>();
        
        deque.addFirst(1);
        assertEquals(1, deque.removeFirst().intValue());
        deque.addFirst(1);
        assertEquals(1, deque.removeLast().intValue());
        
        deque.addLast(1);
        assertEquals(1, deque.removeFirst().intValue());
        deque.addLast(1);
        assertEquals(1, deque.removeLast().intValue());
        
        assertTrue(deque.isEmpty());
    }
    
    @Test
    public void testAddFirstRemoveFirst() {
        Deque<Integer> deque = new Deque<Integer>();
        assertEquals(0, deque.size());
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(2, deque.size());
        assertEquals(2, deque.removeFirst().intValue());
        assertEquals(1, deque.removeFirst().intValue());
        assertEquals(0, deque.size());
    }
    
    @Test
    public void testAddFirstRemoveLast() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(1, deque.removeLast().intValue());
        assertEquals(2, deque.removeLast().intValue());
    }
    
    @Test
    public void testAddRemoveMixed() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(1, deque.removeFirst().intValue());
        assertEquals(2, deque.removeFirst().intValue());
    }
    
    
    @Test
    public void testIterator() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(0);
        deque.addLast(2);
        assertArrayEquals(new Integer[]{0, 1, 2}, toArray(deque));
    }
    
    @Test
    public void testIteratorOnSmallInitialCapacity() {
        Deque<Integer> deque = new Deque<Integer>(2);
        deque.addLast(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertArrayEquals(new Integer[]{3, 2, 1}, toArray(deque));
    }
    
    @Test
    public void testIndexingOverArrayBounds() {
        Deque<Integer> deque = new Deque<Integer>(2);
        deque.addLast(1);
        assertEquals(1, deque.removeFirst().intValue());
        deque.addLast(1);
        assertEquals(1, deque.removeFirst().intValue());
    }
    
    @Test
    public void testCapacityGrowthFromHead() {
        int n = 17;
        Deque<Integer> deque = new Deque<Integer>(2);
        for (int i = 0; i < n; i++) {
            deque.addFirst(i);
            Integer[] expected = IntStream.iterate(i, (int x) -> x - 1).limit(i+1).boxed().toArray(Integer[]::new);
            assertArrayEquals(expected, toArray(deque));
        }
        assertEquals(n, deque.size());
    }
    
    @Test
    public void testCapacityGrowthFromTail() {
        int n = 17;
        Deque<Integer> deque = new Deque<Integer>(2);
        for (int i = 0; i < n; i++) {
            deque.addLast(i);
            Integer[] expected = IntStream.iterate(0, (int x) -> x+1).limit(i+1).boxed().toArray(Integer[]::new);
            assertArrayEquals(expected, toArray(deque));
        }
        assertEquals(n, deque.size());
    }
    
    @Test
    public void testShrinking() {
        Deque<Integer> deque = new Deque<Integer>(2);
        int n = 32;
        for (int i = 0; i < n; i++) deque.addFirst(i);
        int memory1 = deque.estimateMemoryUsage(4);
        for (int i = n-1; i >= 0; i--) {
            deque.removeFirst();
            Integer[] expected = IntStream.iterate(i-1, (int x) -> x-1).limit(i).boxed().toArray(Integer[]::new);
            assertArrayEquals(expected, toArray(deque));
        }
        int memory2 = deque.estimateMemoryUsage(4);
        assertTrue(memory2 < memory1);
    }
    
    static Integer[] toArray(Deque<Integer> deque) {
        List<Integer> items = new ArrayList<>();
        for (Iterator<Integer> iterator = deque.iterator(); iterator.hasNext();) {
            Integer i = iterator.next();
            items.add(i);
            
        }
        return items.toArray(new Integer[0]);
    }
}