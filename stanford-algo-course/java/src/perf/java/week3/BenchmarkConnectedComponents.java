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

import common.InputData;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 3, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BenchmarkConnectedComponents {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
		UndirectedGraph g = InputData.week3();
	}
	
	@Benchmark
	public int benchmarkCcCompute(BenchmarkState state) {
		return new ConnectedComponents(state.g).compute();
	}
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + BenchmarkConnectedComponents.class.getSimpleName()+".*")
			.addProfiler(StackProfiler.class)
			.build();
		new Runner(opt).run();
	}
}
