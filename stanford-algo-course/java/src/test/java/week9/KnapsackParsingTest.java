package week9;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class KnapsackParsingTest {
    @Test
    public void testSmallSackParsing() throws IOException {
        Knapsack kp = InputParser.assignment1();
        assertEquals(10000, kp.capacity);
        assertEquals(100, kp.getItemCount());
        assertEquals(16808, kp.items[0].value);
        assertEquals(250, kp.items[0].weight);
    }
}
