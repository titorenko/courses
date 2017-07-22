package optimized;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class InPlaceMergeSortMergingTest {
    private final InPlaceMergeSort s = new InPlaceMergeSort();
    
    @Parameterized.Parameters
    public static List<Object[]> testData() throws IOException {
        return Arrays.asList(new Object[][] {
            { new int[] {9, 7}},
            { new int[] {3, 5, 7, 2, 4, 9}},
            { new int[] {7, 11, 13, 14, 17, 1, 2, 3, 4, 13}},
            { new int[] {2, 4, 18, 18, 19, 19, 0, 7, 8, 14, 15, 16}}
        }); 
    }
    
    @Parameter
    public int[] input;
    
    @Test
    public void testMerge() {
        s.merge(input, 0, input.length / 2, input.length);
        assertTrue(Sorts.isSorted(input));
    }
}