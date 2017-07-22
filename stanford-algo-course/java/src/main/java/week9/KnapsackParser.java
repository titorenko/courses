package week9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

class KnapsackParser {
	
	public static Knapsack fromFile(File file) throws IOException {
		return fromReader(new FileReader(file));
	}
	
	public static Knapsack fromResource(URL resource) {
	    try {
	        return fromReader(new InputStreamReader(resource.openStream()));
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

	private static Knapsack fromReader(Reader reader) throws IOException {
		try (BufferedReader br = new BufferedReader(reader)) {
		    String[] line0 = br.readLine().split("\\s+");
			int capacity = Integer.parseInt(line0[0]);
			int nItems = Integer.parseInt(line0[1]);
			KnapsackItem[] items = new KnapsackItem[nItems];
			for (int i = 0; i < items.length; i++) {
			    String line = br.readLine();
			    String[] tokens = line.split("\\s+");
                final int value = Integer.parseInt(tokens[0]);
                final int weight = Integer.parseInt(tokens[1]);
                items[i] = new KnapsackItem(weight, value);
            }
			return new Knapsack(capacity, items);
		}
	}
}