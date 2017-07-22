package week1;
import static org.junit.Assert.*;
import org.junit.Test;

public class PercolationStatsIntegrationTest {
    @Test
    public void testRunExpirements() {
        PercolationStats ps = new PercolationStats(200, 100);
        double mean = ps.mean();
        double stddev = ps.stddev();
        System.out.println("Mean = " + mean + ", stddev = " + stddev);
        assertTrue(mean > 0.585 && mean < 0.597);
        assertTrue(stddev > 0.005 && stddev < 0.02);
    }
}
