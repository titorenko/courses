package week2;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayDeque;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class DequeIntegrationTest {
    
    @DataPoints
    public static int[] sizes() {
        return new int[] {0, 1, 10, 100, 10000};
    }

    
    @Theory
    public void dequeShouldHaveSameResultsAsJavaDeque(Integer additionsHead, Integer additionsTail, Integer removals) {
        Deque<Integer> deque = new Deque<>();
        java.util.Deque<Integer> reference = new ArrayDeque<>();
        for (int i = 0; i < additionsTail; i++) {
            deque.addLast(i);
            reference.addLast(i);
        }
        for (int i = 0; i < Math.min(removals, additionsTail); i++) {
            deque.removeFirst();
            reference.removeFirst();
        }
        for (int i = 0; i < additionsHead; i++) {
            deque.addFirst(i);
            reference.addFirst(i);
        }
        Integer[] expected = reference.toArray(new Integer[0]);
        Integer[] actual = DequeTest.toArray(deque);
        assertArrayEquals(expected, actual);
    }
}