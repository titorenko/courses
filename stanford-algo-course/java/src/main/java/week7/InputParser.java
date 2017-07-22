package week7;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;

import common.InputData;

public class InputParser {
    
    public static Job[] parseAssignmentJobsTxt() throws IOException {
        return parseResource("/jobs.txt");
    }
    
    static Job[] parseResource(String resource) throws IOException {
        InputStream is = InputData.class.getResourceAsStream(resource);
        List<String> lines = CharStreams.readLines(new InputStreamReader(is));
        int n = Integer.parseInt(lines.get(0));
        Job[] result = lines.stream().skip(1).map(InputParser::parseJob).toArray(Job[]::new);
        Preconditions.checkArgument(n == result.length, 
                "Unexpected element count: "+result.length+", was expecting: "+n);
        return result;
    }
    
    private static Job parseJob(String line) {
        String[] tokens = line.split("\\s+");
        Preconditions.checkArgument(tokens.length == 2);
        int weight = Integer.parseInt(tokens[0]);
        int lenght = Integer.parseInt(tokens[1]);
        return new Job(weight, lenght);
    }
}
