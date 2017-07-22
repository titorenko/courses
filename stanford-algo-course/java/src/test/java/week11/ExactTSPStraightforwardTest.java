package week11;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ExactTSPStraightforwardTest {
    
    @Test
    public void testSubsetGeneration() {
        int[] ss = ExactTSPStraightforward.getSubsetsOfSize(4, 2).toArray();
        assertArrayEquals(new int[] {3, 5, 9}, ss);
    }
}
