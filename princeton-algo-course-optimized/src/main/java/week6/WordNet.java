package week6;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.In;

//Immutable
public class WordNet {

    private final Map<String, Integer> nounToId = new HashMap<>();
    private final String[][] synonyms;
    private final Digraph hypernyms;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.synonyms = parseSynsets(new File(synsets));
        this.hypernyms = parseHypernyms(new File(hypernyms));
    }

    private String[][] parseSynsets(File file) {
        List<String[]> result = new ArrayList<>();
        In in = new In(file);
        String line = null;
        int id = 0;
        while( (line= in.readLine()) != null) {
            String[] split = line.split(",");
            String[] syns = split[1].split(" ");
            result.add(syns);
            for (String noun: syns) {
                nounToId.put(noun, id);
            }
            id++;
        }
        return result.toArray(new String[0][]);
    }
    
    private Digraph parseHypernyms(File file) {
        String[] lines = new In(file).readAllStrings();
        int count = nounToId.size()+1;
        Digraph digraph = new Digraph(count);
        for (String line : lines) {
            String[] vertexs = line.split(",");
            int target = Integer.parseInt(vertexs[0]);
            for (int i = 1; i < vertexs.length; i++) {
                int source = Integer.parseInt(vertexs[i]);
                digraph.addEdge(source, target);
            }
        }
        return digraph;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounToId.containsKey(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return nounB;

    }

}
