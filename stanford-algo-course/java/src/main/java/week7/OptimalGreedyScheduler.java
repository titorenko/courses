package week7;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class OptimalGreedyScheduler implements Scheduler {
    @Override
    public long schedule(Job[] jobs) {
        Arrays.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job j1, Job j2) {
                return j2.getWeight() * j1.getLength() - j1.getWeight() * j2.getLength();
            }
        });
        return computeTotalCost(jobs);
    }
    
    public static void main(String[] args) throws IOException {
        Job[] jobs = InputParser.parseAssignmentJobsTxt();
        long nonopt = new NonOptimalGreedyScheduler().schedule(jobs);
        long opt = new OptimalGreedyScheduler().schedule(jobs);
        System.out.println("Non optimal: "+nonopt+", optimal: "+opt);
    }
}