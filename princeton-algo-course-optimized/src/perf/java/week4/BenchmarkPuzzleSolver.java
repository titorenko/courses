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
@Warmup(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkPuzzleSolver {
    
    @State(Scope.Benchmark)
    public static class BenchmarkState {
    	String[] testSet = {"puzzle20.txt", "puzzle21.txt", "puzzle22.txt", "puzzle23.txt",
    			"puzzle24.txt", "puzzle25.txt", "puzzle26.txt", "puzzle27.txt",
    			"puzzle28.txt", "puzzle29.txt", "puzzle30.txt", "puzzle31.txt",
    			"puzzle34.txt", "puzzle37.txt", "puzzle39.txt", "puzzle41.txt",
    			"puzzle44.txt"};
        List<Board> boards = loadBoards();
		
        private List<Board> loadBoards() {
        	List<Board> result = new ArrayList<>();
        	for (String file : testSet) {
        		result.add(InputData.load("src/main/resources/8puzzle/"+file));
			}
			return result;
		}
    }
    
    
    @Benchmark
    public void benchmarkSolver(BenchmarkState state, Blackhole bh) {
    	for (Board board: state.boards) {
    		Solver solver = new Solver(board);
            bh.consume(solver.moves());	
		}
        
    }
    
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(".*" + BenchmarkPuzzleSolver.class.getSimpleName()+".*")
            //.addProfiler(LinuxPerfAsmProfiler.class)
            .addProfiler(StackProfiler.class)
            .build();
        new Runner(opt).run();
    }
}