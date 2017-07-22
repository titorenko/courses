package week9;

import java.net.URL;

public class InputParser {

    public static Knapsack assignment1() {
        return parseResource("/knapsack1.txt");
    }

    public static Knapsack assignment2() {
        return parseResource("/knapsack_big.txt");
    }

    public static Knapsack parseResource(String resourceName) {
        URL resource = InputParser.class.getResource(resourceName);
        return KnapsackParser.fromResource(resource);
    }

}
