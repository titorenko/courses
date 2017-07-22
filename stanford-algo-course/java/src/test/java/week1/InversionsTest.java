package week1;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import optimized.Sorts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import common.InputData;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class InversionsTest {
	
	@Parameterized.Parameters(name = "sortInput {index} with {1} inversions")
	public static List<Object[]> testData() throws IOException {
		return Arrays.asList(new Object[][] {
			{ new int[] {3, 1, 2}, 2 },
			{ new int[] {1, 3, 5, 2, 4, 6}, 3 },
			{ new int[] {9, 12, 3, 1, 6, 8, 2, 5, 14, 13, 11, 7, 10, 4, 0}, 56 },
			{ InputData.week1(), 2407905288L}
        }); 
	}
	
	@Parameter
	public int[] input;
	
	@Parameter(value=1)
	public long expectedInversions;
	
	@Test 
	public void testThatSorted() {
		int[] toSort = input.clone();
		Sorts.sortAndCountInversions(toSort);
		assertTrue(Sorts.isSorted(toSort));
	}
	
	@Test 
	public void testInversionsComputedCorrectly() {
		int[] toSort = input.clone();
		long nInversions = Sorts.sortAndCountInversions(toSort);;
		assertEquals(expectedInversions, nInversions);
	}
}