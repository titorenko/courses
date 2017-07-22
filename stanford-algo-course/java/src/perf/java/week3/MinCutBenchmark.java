package week3;

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
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import week3.UndirectedGraph.Edge;

import common.InputData;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MinCutBenchmark {

	@State(Scope.Benchmark)
	public static class BenchmarkState {
		UndirectedGraph g = InputData.week3();
		Edge[] e = g.getEdges();
	}

	@Benchmark
	public int benchmarkMinCutTime(BenchmarkState state) {
		return new KargerMinimumCut(state.g).calculate();
	}
	
	@Benchmark
	@OutputTimeUnit(TimeUnit.MICROSECONDS)	
	public int benchmarkMinCutIteration(BenchmarkState state) {
		return new KargerMinimumCut(state.g).minCutOneRound(state.e);
	}

	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + MinCutBenchmark.class.getSimpleName()+".benchmarkMinCutIteration")
			.addProfiler(StackProfiler.class)
			.forks(1)
			.warmupIterations(3)
			.measurementIterations(3)
			.measurementTime(TimeValue.milliseconds(200))
			.build();
		new Runner(opt).run();
	}
}