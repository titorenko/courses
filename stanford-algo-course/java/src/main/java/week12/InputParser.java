package week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import common.PrimitivesParser;

/**
 * The file format is as follows. In each instance, the number of variables and the number 
 * of clauses is the same, and this number is specified on the first line of the file.
 * Each subsequent line specifies a clause via its two literals, with a number denoting 
 * the variable and a "-" sign denoting logical "not". For example, the second line of 
 * the first data file is "-16808 75250", which indicates the clause ¬x16808 ∨ x75250. 
 *
 * We convert this representation into directed graph with vertex for each x_i and each ¬x_i,
 * so the number of vertices is 2*n.
 * Moreover for each clause x1 v x2 we add edge between ¬x2 and x1 + edge between ¬x1 and x2,
 * here edges are thought of as implications.
 * This is done to apply KosarajuSCC, if it finds any SCC with 4 or more vertices then the 
 * problem is not solvable.
 */
public class InputParser {
	
	public static Stream<TwoSATProblem> assignments() {
		int nFiles = 6;
		return IntStream.rangeClosed(1, nFiles).mapToObj(i -> parseResource("/2sat"+i+".txt"));
    }

	static TwoSATProblem parseResource(String resourceName) {
		try {
            URL resource = InputParser.class.getResource(resourceName);
            InputStreamReader reader = new InputStreamReader(resource.openStream());
            TwoSATProblem result = parseTwoSat(reader);
            reader.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	/**
	 * Returns 2xn array with first array containing left part of disjunction
	 * and second part containing right part of disjunction. All variables 
	 * are referred to by positive indexes, so they are shifted by n*/
	private static TwoSATProblem parseTwoSat(Reader reader) throws IOException {
		try (BufferedReader br = new BufferedReader(reader)) {
			int n = Integer.parseInt(br.readLine());
			TwoSATProblem result = new TwoSATProblem(n, n);
			String line = null;
			while ((line = br.readLine()) != null) {
				int[] pair = parseLine(line);
				result.addClause(pair[0], pair[1]);
			}
			return result;
		}
	}

	private static int[] parseLine(String line) {
		int splitIdx = line.indexOf(" ");
		int left = PrimitivesParser.parseInt(line, 0, splitIdx);
		int right = PrimitivesParser.parseInt(line, splitIdx+1, line.length());
	    return  new int[] {left, right};
	}
	
}