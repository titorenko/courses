import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CallsTest {
	@Test
	public void testCalls() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/calls.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("15"));
		assertTrue(stdOut, stdOut.contains("COOL program successfully executed"));
	}
}
