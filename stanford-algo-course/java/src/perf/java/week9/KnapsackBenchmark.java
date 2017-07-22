package week9;

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
import org.openjdk.jmh.profile.LinuxPerfAsmProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5, time = 20, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 20, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class KnapsackBenchmark {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
	    Knapsack ks = InputParser.assignment1();
	}
	
	@Benchmark
	public int benchmarkVanillaKS(BenchmarkState state) {
	    KnapsackSolver v = new KnapsackSolverDPVanilla();
		return v.solve(state.ks);
	}
	
	@Benchmark
    public int benchmarkMemoryEfficientKS(BenchmarkState state) {
        KnapsackSolver v = new KnapsackSolverDPMemoryEfficient();
        return v.solve(state.ks);
    }
	
	@Benchmark
    public int benchmarkSparseKS(BenchmarkState state) {
        KnapsackSolver v = new KnapsackSolverSparse();
        return v.solve(state.ks);
    }
	
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + KnapsackBenchmark.class.getSimpleName()+".*")
			.addProfiler(LinuxPerfAsmProfiler.class)
			.build();
		new Runner(opt).run();
	}
}