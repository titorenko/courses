package week10;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import week10.BellmanFord.NegaticeCycleFoundException;
import week5.DijkstraShortestPath;
import week5.DijkstraSolvers;
import week5.WeightedDigraph;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BellmanFordBenchmark {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
	    WeightedDigraph g = new JohnsonAPSP(InputParser.assignmentLarge()).attachVirtualSource();
	}
	
	@Benchmark
	public int[] benchmarkBellmanFord(BenchmarkState state) throws NegaticeCycleFoundException {
	    BellmanFord bf = new BellmanFord(state.g, 0);
		return bf.find();
	}
	
	@Benchmark
    public int[] benchmarkDijkstra(BenchmarkState state) throws NegaticeCycleFoundException {
        DijkstraShortestPath dk = DijkstraSolvers.BINARY.solverFor(state.g, 0);
        return dk.find();
    }
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + BellmanFordBenchmark.class.getSimpleName()+".*")
			.build();
		new Runner(opt).run();
	}
}