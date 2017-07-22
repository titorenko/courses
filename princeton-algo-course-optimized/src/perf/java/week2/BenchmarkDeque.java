/*package week2;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@BenchmarkMode(Mode.AverageTime)
@Fork(2)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkDeque {
    
    @Param({"0", "50000"})
    public int removals;
    
    @Param({"100000"})
    public int headAdditions;

    @Param({"100000"})
    public int tailAdditions;
    
	
    @Benchmark
    public Object benchmarkJavaImpl() {
        java.util.Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < tailAdditions; i++) {
            q.addLast(i);
        }
        final int min = Math.min(removals, tailAdditions);
        for (int i = 0; i < min; i++) {
            q.removeFirst();
        }
        for (int i = 0; i < headAdditions; i++) {
            q.addFirst(i);
        }
        return q;
    }
    
    @Benchmark
    public Object benchmarkCustomImpl() {
        Deque<Integer> q = new Deque<>();
        for (int i = 0; i < tailAdditions; i++) {
            q.addLast(i);
        }
        final int min = Math.min(removals, tailAdditions);
        for (int i = 0; i < min; i++) {
            q.removeFirst();
        }
        for (int i = 0; i < headAdditions; i++) {
            q.addFirst(i);
        }
        return q;
    }
    
	//try running with -Xmx1g -XX:MaxInlineSize=0
	public static void main(String[] args) throws Exception {
		Options opt = new OptionsBuilder()
			.include(".*" + BenchmarkDeque.class.getSimpleName()+".*")
			//.addProfiler(StackProfiler.class)
			.build();
		new Runner(opt).run();
	}
}*/