package week7;

import com.google.common.base.Preconditions;

public class Job {
    
    private final int length;
    private final int weight;
    
    public Job(int weight, int length) {
        Preconditions.checkArgument(length > 0);
        Preconditions.checkArgument(weight > 0);
        this.length = length;
        this.weight = weight;
    }
    
    public int getLength() {
        return length;
    }
    
    public int getWeight() {
        return weight;
    }
}
