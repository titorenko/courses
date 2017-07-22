package week8;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClusteringTest {
	
	@Test
	public void testGraphFromTestCase1() throws IOException {
		WeightedGraph g = InputParser.parseResource("/clustering_test1.txt");
		MaxSpaceGreedyClustering clustering = new MaxSpaceGreedyClustering(g);
		assertEquals(134365, clustering.findClusters(4));
	}
}