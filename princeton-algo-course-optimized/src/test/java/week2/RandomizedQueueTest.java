package week2;

import java.util.NoSuchElementException;

import org.junit.Test;

import common.Streams;

import static org.junit.Assert.*;

public class RandomizedQueueTest {
    @Test(expected=NullPointerException.class)
    public void testPutNull() {
        new RandomizedQueue<Double>().enqueue(null);
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testRemoveFromEmpty() {
        new RandomizedQueue<Double>().dequeue();
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testEmptyIterator() {
        new RandomizedQueue<Double>().iterator().next();
    }
    
    @Test
    public void testOneElement() {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        assertEquals(1, q.dequeue().intValue());
        
        assertTrue(q.isEmpty());
    }
    
    @Test
    public void testEnqueDeque() {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int n = 17;
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int x = q.dequeue();
            sum += x;
        }
        assertEquals((n-1)*n / 2, sum);
    }
    
    @Test
    public void testIterator() {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int n = 17;
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        int sum = Streams.fromIterable(q).reduce(0, (Integer i, Integer j) -> i+j);
        assertEquals((n-1)*n / 2, sum);
    }
    
    @Test
    public void testDequeuIsUniform() {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int n = 100;
        int m = 3;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                q.enqueue(i);
            }
        }
        int[] freq = new int[m];
        for (int i = 0; i < n*m/2; i++) {
            freq[q.dequeue()]++;
        }
        for (int i = 0; i < m; i++) {
            int f = freq[i];
            assertTrue("Frequency is out of range for "+i+": "+f, f < n*1.25/2 && f > n*0.75/2); 
        }
    }
    
    @Test
    public void testSample() {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int n = 100;
        int m = 3;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                q.enqueue(i);
            }
        }
        int[] freq = new int[m];
        for (int i = 0; i < n*m; i++) {
            freq[q.sample()]++;
        }
        for (int i = 0; i < m; i++) {
            int f = freq[i];
            assertTrue("Frequency is out of range for "+i+": "+f, f < n*1.25 && f > n*0.75); 
        }
    }
    
    @Test
    public void testTwoIteratorsGiveDifferentOrder() {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int n = 31;
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        int sum1 = Streams.fromIterable(q).reduce(0, (Integer i, Integer j) -> (i+j)*j);
        int sum2 = Streams.fromIterable(q).reduce(0, (Integer i, Integer j) -> (i+j)*j);
        assertFalse(sum1 == sum2);
    }

}