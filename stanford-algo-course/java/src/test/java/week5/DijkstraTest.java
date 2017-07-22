package week5;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertArrayEquals;

@RunWith(Parameterized.class)
public class DijkstraTest {
	
	@Parameters(name = "{0}")
	public static Iterable<Object[]> dijkstra() {
		return Arrays.asList(new Object[][] {
			{DijkstraSolvers.JAVA},
			{DijkstraSolvers.BINARY},
			{DijkstraSolvers.NAIVE}
		});
	}

	private DijkstraSolvers solver;
	
	public DijkstraTest(DijkstraSolvers solver) {
		this.solver = solver;
	}
	
	@Test
	public void testDiffenbach1() {
		WeightedGraphBuilder builder = new WeightedGraphBuilder(4)
			.addEdge(0, 1, 3).addEdge(0, 2, 3)
			.addEdge(1, 2, 1).addEdge(1, 3, 2)
			.addEdge(2, 3, 50);
		DijkstraShortestPath d = solver.solverFor(builder.build(), 0);
		assertArrayEquals(new int[] {0, 3, 3, 5}, d.find());
	}
	
	@Test
	public void testDiffenbach2() {
		WeightedGraphBuilder builder = new WeightedGraphBuilder(4)
			.addEdge(0, 1, 3).addEdge(0, 2, 5)
			.addEdge(1, 2, 1).addEdge(1, 3, 2)
			.addEdge(2, 3, 50);
		DijkstraShortestPath d = solver.solverFor(builder.build(), 0);
		assertArrayEquals(new int[] {0, 3, 4, 5}, d.find());
	}
	
	@Test
	public void testGorkovenko1() {
		WeightedGraphBuilder builder = new WeightedGraphBuilder(4)
			.addEdge(0, 1, 8).addEdge(0, 2, 15)
			.addEdge(1, 0, 7).addEdge(1, 2, 4).addEdge(1, 3, 5)
			.addEdge(2, 0, 12)
			.addEdge(3, 2, 5);
		DijkstraShortestPath d = solver.solverFor(builder.build(), 0);
		assertArrayEquals(new int[] {0, 8, 12, 13}, d.find());
	}
}
