package week11;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SubsetsTest {

    @Parameters(name = "{0}")
    public static Iterable<Object[]> ns() {
        return Arrays.asList(new Object[][] {
            {2}, {3}, {4}, {5}, {6}, {7}
        });
    }
    
    @Parameter
    public int n;
    
    @Test
    public void testSubsetGenerationFromN() {
        Subsets ss = new Subsets(n);
        for (int m = 1; m <= n; m++) {
            int[] actual =  ss.subsets(m).mapToInt(tuple -> toBitRepresentation(tuple[0])).toArray();
            Arrays.sort(actual);
            int[] expected = ExactTSPStraightforward.getSubsetsOfSize(n, m).toArray();
            assertArrayEquals("Failed for m="+m+", "
                    + "expected: "+Arrays.toString(expected)+", "
                    + "actual: "+Arrays.toString(actual), 
                    expected, actual);
        }
    }
    
    @Test
    public void testIndexComputation() {
        Subsets ss = new Subsets(n);
        for (int m = 1; m <= n; m++) {
            ss.subsets(m).forEach(tuple -> assertEquals(tuple[1][0], ss.indexOf(tuple[0])));
        }
    }
    
    @Test
    public void testExcludedIndex() {
        Subsets ss = new Subsets(n);
        for (int m = 1; m <= n; m++) {
            ss.subsets(m).forEach(tuple -> {
                for (int j : tuple[0]) {
                    int expected = ss.indexOf(Subsets.removeFromArray(tuple[0], j));
                    int actual = ss.indexOf(tuple[0], j);
                    assertEquals(expected, actual);
                }
            });    
        }
    }
    
    @Test
    public void testRemoveFromArray() {
        assertArrayEquals(new int[]{2, 3}, Subsets.removeFromArray(new int[] {1, 2, 3}, 1));
        assertArrayEquals(new int[]{1, 3}, Subsets.removeFromArray(new int[] {1, 2, 3}, 2));
        assertArrayEquals(new int[]{1, 2}, Subsets.removeFromArray(new int[] {1, 2, 3}, 3));
    }

    private int toBitRepresentation(int[] s) {
        int result = 0;
        for (int i = 0; i < s.length; i++) {
            result += 1 << s[i];
        }
        return result;
    }
    
}