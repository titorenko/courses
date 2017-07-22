package week7;

import java.util.Arrays;
import java.util.Comparator;

public class NonOptimalGreedyScheduler implements Scheduler {
    @Override
    public long schedule(Job[] jobs) {
        Arrays.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job j1, Job j2) {
                int diff = getCost(j2) - getCost(j1);
                return diff == 0 ? j2.getWeight() - j1.getWeight() : diff; 
            }
            
            private int getCost(Job j) {
                return j.getWeight() - j.getLength();
            }
        });
        return computeTotalCost(jobs);
    }
}