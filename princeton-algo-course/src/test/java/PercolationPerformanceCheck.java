public class PercolationPerformanceCheck {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int[] ns = new int[] { 50, 100, 200 };
            int t = 1000;
            for (int n : ns) {
                Stopwatch stopwatch = new Stopwatch();
                PercolationStats stats = new PercolationStats(n, t);
                double elapsedTime = stopwatch.elapsedTime();
                System.out.println("In " + t + " tries on " + n + " grid "
                        + "mean is " + stats.mean() + ", computed in "
                        + elapsedTime);
            }
        }
    }
}
