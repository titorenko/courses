package week9;

import java.util.Comparator;

import com.google.common.base.Preconditions;

public class KnapsackItem {
    public static Comparator<KnapsackItem> BY_WEIGHT_ASC = new Comparator<KnapsackItem>() {
        @Override
        public int compare(KnapsackItem ki1, KnapsackItem ki2) {
            return ki1.weight - ki2.weight;
        }
    };
    public static Comparator<KnapsackItem> BY_WEIGHT_DESC= new Comparator<KnapsackItem>() {
        @Override
        public int compare(KnapsackItem ki1, KnapsackItem ki2) {
            return ki2.weight - ki1.weight;
        }
    };
    final int weight;//should be integral
    final int value;
    
    public KnapsackItem(int weight, int value) {
        Preconditions.checkArgument(weight > 0);
        Preconditions.checkArgument(value > 0);
        this.weight = weight;
        this.value = value;
    }
    
}