package week5;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import week5.DijkstraShortestPath.QueueFactory;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BinaryHeapPriorityQueueTest {
	
	private static final int size = 3;
	private static final int initialValue = 10;
	
	@Parameters
	public static Iterable<QueueFactory[]> queues() {
		QueueFactory[][] factories = new QueueFactory[][] {
			{(s, iv) -> new JavaBasedPriorityQueue(size, initialValue)},
			{(s, iv) -> new BinaryHeapPriorityQueue(size, initialValue)},
			{(s, iv) -> new NaivePriorityQueue(size, initialValue)}
		};
		return Arrays.asList(factories);
	}

	private QueueFactory qf;
	private IntPriorityQueue q;
	
	public BinaryHeapPriorityQueueTest(QueueFactory qf) {
		this.qf = qf;
	}
	
	@Before
	public void createQueue() {
		this.q = qf.newQueue(size, initialValue);
	}

	@Test
	public void checkBasicProperties() {
		assertEquals(size, q.size());
		assertFalse(q.isEmpty());
	}
	
	@Test
	public void simpleBubleUpRightTest() {
		q.decreaseKey(2, 5);
		assertEquals(2, q.poll()[0]);
	}
	
	@Test
	public void simpleBubleUpLeftTest() {
		q.decreaseKey(1, 5);
		assertEquals(1, q.poll()[0]);
	}
	
	@Test
	public void pollTest() {
		q.decreaseKey(2, 5);
		q.decreaseKey(1, 3);
		assertArrayEquals(new int[]{1, 3}, q.poll());
		assertArrayEquals(new int[]{2, 5}, q.poll());
		assertArrayEquals(new int[]{0, 10}, q.poll());
	}
}
