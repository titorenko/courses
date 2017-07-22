package week11;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputParserTest {
    @Test
    public void testParseFile1() {
        float[][] am = InputParser.assignment1();
        assertEquals(25, am.length);
        assertEquals(am[10][1], am[1][10], 1E-10);//symmetric
    }
}
