package week5;

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

import common.InputData;



@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DijkstraBenchmark {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
		WeightedDigraph g = InputData.week5();
	}
	
	@Benchmark
	public int[] benchmarkDijkstraWithJavaPQ(BenchmarkState state) {
		return DijkstraSolvers.JAVA.solverFor(state.g, 0).find();
	}
	
	@Benchmark
	public int[] benchmarkDijkstraWithNaivePQ(BenchmarkState state) {
		return DijkstraSolvers.NAIVE.solverFor(state.g, 0).find();
	}

	@Benchmark
	public int[] benchmarkDijkstraWithBinaryPQ(BenchmarkState state) {
		return DijkstraSolvers.BINARY.solverFor(state.g, 0).find();
	}
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + DijkstraBenchmark.class.getSimpleName()+".*")
			//.addProfiler(StackProfiler.class)
			.build();
		new Runner(opt).run();
	}
}