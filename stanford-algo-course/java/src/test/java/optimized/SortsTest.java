package optimized;

import java.io.IOException;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import static java.util.stream.Collectors.*;

import static org.junit.Assert.assertTrue;

import week1.InversionsTest;

@RunWith(Parameterized.class)
public class SortsTest {
	
	@Parameterized.Parameters(name = "sortInput {index}")
	public static Collection<?> testData() throws IOException {
		return InversionsTest.testData().stream()
			.map(a -> new Object[]{a[0]}).collect(toList());
	}

	@Parameter
	public int[] input;

	@Test
	public void testMergeSort() {
		int[] toSort = input.clone();
		Sorts.mergeSort(toSort);
		assertTrue(Sorts.isSorted(toSort));
	}
	
	@Test
	@Ignore //not ready
    public void testInPlaceMergeSort() {
        int[] toSort = input.clone();
        Sorts.inPlaceMergeSort(toSort);
        assertTrue(Sorts.isSorted(toSort));
    }

	@Test
	public void testConcurrentMergeSort() {
		int[] toSort = input.clone();
		Sorts.parallelMergeSort(toSort);
		assertTrue(Sorts.isSorted(toSort));
	}
}
