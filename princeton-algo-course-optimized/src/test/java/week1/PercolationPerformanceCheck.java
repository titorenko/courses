package week1;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class PercolationPerformanceCheck {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int[] ns = new int[] { 200 };
            int t = 2000;
            for (int n : ns) {
                Stopwatch stopwatch = Stopwatch.createStarted();
                PercolationStats stats = new PercolationStats(n, t);
                double elapsedTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                System.out.println("In " + t + " tries on " + n + " grid " +
                        "mean is " + stats.mean() + ", computed in " + elapsedTime);
            }
        }
    }
}
