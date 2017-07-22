package week4;

import java.util.ArrayList;
import java.util.List;
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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 3, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Benchmark4x4PuzzleSolver {
    
    @State(Scope.Benchmark)
    public static class BenchmarkState {
    	String[] testSet = {"puzzle4x4-hard2.txt"};
        List<int[][]> boards = loadBoards();
		
        private List<int[][]> loadBoards() {
        	List<int[][]> result = new ArrayList<>();
        	for (String file : testSet) {
        		result.add(InputData.loadArray("src/main/resources/8puzzle/"+file));
			}
			return result;
		}
    }
    
    
    @Benchmark
    public void benchmarkSolver(BenchmarkState state, Blackhole bh) {
    	for (int[][] board: state.boards) {
    		Solver4x4 solver = new Solver4x4(board);
            bh.consume(solver.moves());	
		}        
    }
    
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(".*" + Benchmark4x4PuzzleSolver.class.getSimpleName()+".*")
            //.addProfiler(LinuxPerfAsmProfiler.class)
            .addProfiler(StackProfiler.class)
            .build();
        new Runner(opt).run();
    }
}