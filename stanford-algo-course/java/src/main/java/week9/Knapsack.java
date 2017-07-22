package week9;



public class Knapsack {
    final int capacity;
    final KnapsackItem[] items;
    
    public Knapsack(int capacity, KnapsackItem[] items) {
        this.capacity = capacity;
        this.items = items;
    }

    public int getItemCount() {
        return items.length;
    }
}