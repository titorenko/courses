package week5;

import org.junit.Test;

import common.InputData;

import static org.junit.Assert.assertArrayEquals;

public class IntegrationDijkstraTest {
	@Test
	public void integrationTestJavaVsBinary() {
		WeightedDigraph g = InputData.week5();
		DijkstraShortestPath dj = DijkstraSolvers.JAVA.solverFor(g, 0);
		int[] djr = dj.find();
		DijkstraShortestPath db = DijkstraSolvers.BINARY.solverFor(g, 0);
		int[] dbr = db.find();
		assertArrayEquals(djr, dbr);
	}
}
