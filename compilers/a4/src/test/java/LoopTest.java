import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class LoopTest {
	@Test
	public void testLoop() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/loop.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("12345COOL program successfully executed"));
	}
}
