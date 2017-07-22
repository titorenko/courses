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
    private final int nSquared;

    /**
     * Perform t independent computational experiments on an n-by-n grid
     */
    public PercolationStats(int n, int t) {
        if (n < 1 || t < 1)
            throw new IllegalArgumentException("n and t should be > 1");
        this.n = n;
        this.nSquared = n * n;
        this.t = t;
        this.results = runExperiments();
    }

    private RunningStats runExperiments() {
        RunningStats stats = new RunningStats();
        for (int experiment = 0; experiment < t; experiment++) {
            stats.push(runExperiment());
        }
        return stats;
    }

    private double runExperiment() {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int[] blocked = getRandomBlockedSite(p);
            p.open(blocked[0], blocked[1]);
        }
        return getThreshold(p);
    }

    private int[] getRandomBlockedSite(Percolation p) {
        while (true) {
            int idx = StdRandom.uniform(nSquared) + 1;
            int[] coords = translateBack(idx);
            if (!p.isOpen(coords[0], coords[1]))
                return coords;
        }
    }

    private double getThreshold(Percolation p) {
        int openCount = getOpenSites(p);
        return (double) openCount / (double) (n * n);
    }

    private int getOpenSites(Percolation p) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.isOpen(i, j))
                    count++;
            }
        }
        return count;
    }

    private int[] translateBack(int idx) {
        int i = (idx - 1) / n + 1;
        int j = (idx - 1) % n + 1;
        return new int[] { i, j };
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
            throw new IllegalArgumentException(
                    "Expected two parameters n and t");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats runner = new PercolationStats(n, t);
        System.out.println("mean\t\t\t = " + runner.mean());
        System.out.println(String.format("stddev\t\t\t = %f", runner.stddev()));
        String conf = String.format("%s confidence interval\t = %f, %f", "95%",
                runner.confidenceLo(), runner.confidenceHi());
        System.out.println(conf);
    }
}