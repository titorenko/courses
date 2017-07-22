package week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class InputParser {

    public static float[][] assignment1() {
        return parseResource("/tsp.txt");
    }
    
    public static float[][] parseResource(String resourceName) {
        try {
            URL resource = InputParser.class.getResource(resourceName);
            InputStreamReader reader = new InputStreamReader(resource.openStream());
            float[][] result = parseTsp(reader);
            reader.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static float[][] parseTsp(InputStreamReader reader) throws IOException {
        Point[] points = parsePoints(reader);
        return toAdjacencyMatrix(points);
    }
    
    private static float[][] toAdjacencyMatrix(Point[] points) {
        int n = points.length;
        float[][] result = new float[n][];
        for (int i = 0; i < n; i++) result[i] = new float[n];
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                float dist = (float) points[i].distanceTo(points[j]);
                result[i][j] = dist;
                result[j][i] = dist;
            }
        }
        return result;
    }

    private static Point[] parsePoints(InputStreamReader reader) throws IOException {
        Point[] result = null;
        try (BufferedReader br = new BufferedReader(reader)) {
            int n = Integer.parseInt(br.readLine().trim());
            result = new Point[n];
            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                result[i] = parseLine(line);
                i++;
            }
        }
        return result;
    }

    private static Point parseLine(String line) {
        String[] tokens = line.split("\\s+");
        return new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
    }

    private static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double distanceTo(Point other) {
            double squared = square(x-other.x)+square(y-other.y);
            return Math.sqrt(squared);
        }

        private double square(double f) {
            return f*f;
        }
    }
}