package sorts;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import optimized.InsertionSort;
import optimized.Sorts;

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
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import common.InputData;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SortsBenchmark {
	@State(Scope.Benchmark)
	public static class BenchmarkState {
		int[] input = InputData.week1();
	}

	@Benchmark
    public void benchmarkInsertionSort(BenchmarkState state) {
        int[] toSort = state.input.clone();
        InsertionSort.sort(toSort);
    }
    
    @Benchmark
    public void benchmarkInPlaceMergeSort(BenchmarkState state) {
        int[] toSort = state.input.clone();
        Sorts.inPlaceMergeSort(toSort);
    }
    
    @Benchmark
    public void benchmarkInPlaceMergeSort2(BenchmarkState state) {
        int[] toSort = state.input.clone();
        Sorts.inPlaceMergeSort2(toSort);
    }
	
	@Benchmark
	public void benchmarkMergeSort(BenchmarkState state) {
		int[] toSort = state.input.clone();
		Sorts.mergeSort(toSort);
	}
	
	
	@Benchmark
	public void benchmarkInversionCountingMergeSort(BenchmarkState state) {
		int[] toSort = state.input.clone();
		Sorts.sortAndCountInversions(toSort);
	}

	@Benchmark
	public void benchmarkConcurrentSort(BenchmarkState state) {
		int[] toSort = state.input.clone();
		Sorts.parallelMergeSort(toSort);
	}

	@Benchmark
	public void benchmarkJavaEmbeddedSort(BenchmarkState state) {
		int[] toSort = state.input.clone();
		Arrays.sort(toSort);
	}
	
	@Benchmark
	public void benchmarkJavaEmbeddedParallelSort(BenchmarkState state) {
		int[] toSort = state.input.clone();
		Arrays.parallelSort(toSort);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
			.include(".*" + SortsBenchmark.class.getSimpleName() + ".*")
			.forks(1)
			.warmupIterations(5)
			.measurementIterations(5)
			.measurementTime(TimeValue.milliseconds(200))
			.build();
		new Runner(opt).run();
	}
}