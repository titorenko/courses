package week7;

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

@BenchmarkMode(Mode.AverageTime)
@Fork(2)
@Warmup(iterations = 3, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PrimsMSTBenchmark {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
	    WeightedGraph g = WeightedGraphParser.fromResource(PrimsMST.class.getResource("/prims_edges.txt"));
	}
	
	@Benchmark
	public long benchmarkPrimsMst(BenchmarkState state) {
        PrimsMST mst = new PrimsMST(state.g);
		return mst.computeMstCost();
	}
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + PrimsMSTBenchmark.class.getSimpleName()+".*")
			//.addProfiler(LinuxPerfAsmProfiler.class)
			.build();
		new Runner(opt).run();
	}
}