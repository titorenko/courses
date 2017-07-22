package week7;

public interface Scheduler {
    /**
     * Schedule the jobs by arranging them in correct 
     * sequence and return total cost
     */
    public long schedule(Job[] jobs);
    
    default long computeTotalCost(Job[] jobs) {
        long cost = 0;
        int lenght = 0;
        for (Job job : jobs) {
            lenght += job.getLength();
            cost += job.getWeight() * lenght;
        }
        return cost;
    }
}