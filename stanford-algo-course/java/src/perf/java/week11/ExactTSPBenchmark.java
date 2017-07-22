package week11;

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
@Warmup(iterations = 5, time = 150, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 150, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ExactTSPBenchmark {
    
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        float[][] adj = InputParser.parseResource("/tspBigger.txt");
    }
    
    @Benchmark
    public float benchmarkFasterTSP(BenchmarkState state) {
        ExactTSP tsp = new ExactTSP(state.adj);
        return tsp.computeLengthOfMinimumTour();
    }
    
    @Benchmark
    public float benchmarkSimpleTSP(BenchmarkState state) {
        ExactTSPStraightforward tsp = new ExactTSPStraightforward(state.adj);
        return tsp.computeLengthOfMinimumTour();
    }
    
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(".*" + ExactTSPBenchmark.class.getSimpleName()+".*")
            .addProfiler(LinuxPerfAsmProfiler.class)
            .build();
        new Runner(opt).run();
    }
}