package week6;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

import common.InputData;

public class MedianMaintenanceTest {
	@Test
	public void testDiffenbach1() {
		IntStream stream = IntStream.of(4, 5, 6, 7, 8, 9, 10, 1, 2, 3);
		assertEquals(54, new MedianMaintenance().compute(stream));
	}
	
	@Test 
	public void testRoos() {
		IntStream stream = IntStream.of(10,	1, 9, 2, 8, 3, 7, 4, 6, 5);
		assertEquals(55, new MedianMaintenance().compute(stream));
	}
	
	@Test 
	public void testEx6() {
		assertEquals(1213, new MedianMaintenance().compute(InputData.week6b()));
	}
}
