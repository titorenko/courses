package week6;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordNetTest {
    
    @Test
    public void testParsing() {
        WordNet wordNet = new WordNet("src/main/resources/wordnet/synsets.txt", "src/main/resources/wordnet/hypernyms.txt");
        assertFalse(wordNet.isNoun("xxx"));
        assertTrue(wordNet.isNoun("Aalto"));
    }
}
