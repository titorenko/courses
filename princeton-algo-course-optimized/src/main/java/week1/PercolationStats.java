package week1;

import java.util.stream.IntStream;

public class PercolationStats {

    private static class RunningStats {
        private int n;
        private double oldM, newM, oldS, newS;

        private void push(double x) {
            n++;
            if (n == 1) {
                newM = x;
                oldS = 0;
            } else {
                newM = oldM + (x - oldM) / n;
                newS = oldS + (x - oldM) * (x - newM);
            }
            oldM = newM;
            oldS = newS;
        }

        private int count() {
            return n;
        }

        private double mean() {
            return newM;
        }

        private double variance() {
            return newS / (n - 1);
        }

        private double stddev() {
            return Math.sqrt(variance());
        }

    }

    private final int n;
    private final int t;

    private final RunningStats results;

    /**
     * Perform t independent computational experiments on an n-by-n grid
     */
    public PercolationStats(int n, int t) {
        if (n < 1 || t < 1)
            throw new IllegalArgumentException("n and t should be > 1");
        this.n = n;
        this.t = t;
        this.results = runExperiments();
    }

    private RunningStats runExperiments() {
        RunningStats stats = new RunningStats();
        IntStream.range(0, t).parallel().mapToDouble((int t) -> runExperiment())
            .forEach((double x) -> stats.push(x));
        return stats;
    }

    private double runExperiment() {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int[] blocked = p.getRandomBlockedSite();
            p.open(blocked[0], blocked[1]);
        }
        return p.getThreshold();
    }

    public double mean() {
        return results.mean();
    }

    public double stddev() {
        return results.stddev();
    }

    public double confidenceLo() {
        return mean() - confidenceFactor();
    }

    public double confidenceHi() {
        return mean() + confidenceFactor();
    }

    private double confidenceFactor() {
        return 1.96 * stddev() / Math.sqrt(results.count());
    }

    public static void main(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException("Expected two parameters n and t");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats runner = new PercolationStats(n, t);
        System.out.println("mean\t\t\t = " + runner.mean());
        System.out.println(String.format("stddev\t\t\t = %f", runner.stddev()));
        String conf = String.format("%s confidence interval\t = %f, %f", "95%", runner.confidenceLo(),
                runner.confidenceHi());
        System.out.println(conf);
    }
}