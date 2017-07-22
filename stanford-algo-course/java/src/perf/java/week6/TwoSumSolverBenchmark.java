package week6;

import java.util.Arrays;
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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TwoSumSolverBenchmark {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
		long[] input = InputData.week6a();
	}
	
	@Benchmark
	public int benchmarkTwoSum(BenchmarkState state) {
		TwoSumOptimizedSolver solver = new TwoSumOptimizedSolver(state.input, -10000, 10000);
		return solver.solve();
	}
	
	@Benchmark
	public long[] benchmarkSort(BenchmarkState state) {
		final long[] copy = Arrays.copyOf(state.input, state.input.length);
		Arrays.parallelSort(copy);
		return copy;
	}
	
	@Benchmark
	public int benchmarkTwoSumHashed(BenchmarkState state) {
		TwoSumSolver solver = new TwoSumSolver(state.input, -10, 10);
		return solver.solve();
	}
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + TwoSumSolverBenchmark.class.getSimpleName()+".*")
			//.addProfiler(LinuxPerfAsmProfiler.class)
			//.addProfiler(StackProfiler.class)
			.build();
		new Runner(opt).run();
	}
}