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
import week5.WeightedDigraph;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5, time = 150, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 150, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JohnsonBenchmark {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
	    WeightedDigraph g = InputParser.assignment3();
	}
	
	@Benchmark
	public int benchmarkJackson(BenchmarkState state) throws NegaticeCycleFoundException {
	    JohnsonAPSP j = new JohnsonAPSP(state.g);
		return j.solveForMin();
	}
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + JohnsonBenchmark.class.getSimpleName()+".*")
			.build();
		new Runner(opt).run();
	}
}