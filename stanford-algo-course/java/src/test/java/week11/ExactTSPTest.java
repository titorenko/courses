package week11;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExactTSPTest {
    @Parameters(name = "{0}")
    public static Iterable<Object[]> problems() {
        return Arrays.asList(new Object[][] {
        	{InputParser.parseResource("/tspTiny.txt"), 8637.407f},
            {InputParser.parseResource("/tspSmall.txt"), 8356.307f},
            {InputParser.parseResource("/tspBigger.txt"), 19269.936f}
        });
    }
    
    @Parameter(0)
    public float[][] adj;
    
    @Parameter(1)
    public float expectedTourLength;
    
    @Test
    public void testExactTSP() {
        ExactTSP tsp = new ExactTSP(adj);
        assertEquals(expectedTourLength, tsp.computeLengthOfMinimumTour(), 1E-2);
    }
    
    @Test
    public void testExactTSPSimple() {
        ExactTSPStraightforward tsp = new ExactTSPStraightforward(adj);
        assertEquals(expectedTourLength, tsp.computeLengthOfMinimumTour(), 1E-2);
    }
}
