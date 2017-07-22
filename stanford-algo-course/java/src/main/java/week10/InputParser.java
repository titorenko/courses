package week10;

import java.io.IOException;
import java.net.URL;

import week5.WeightedDigraph;
import week5.WeightedDigraphParser;
import week5.WeightedDigraphParser.Mode;

public class InputParser {

    public static WeightedDigraph assignment1() {
        return parseResource("/shortest_path1.txt");
    }
    
    public static WeightedDigraph assignment2() {
        return parseResource("/shortest_path2.txt");
    }
    
    public static WeightedDigraph assignment3() {
        return parseResource("/shortest_path3.txt");
    }
    
    public static WeightedDigraph assignmentLarge() {
        return parseResource("/shortest_path_large.txt");
    }

    public static WeightedDigraph parseResource(String resourceName) {
        try {
            URL resource = InputParser.class.getResource(resourceName);
            return new WeightedDigraphParser(Mode.WEEK10).fromResource(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}