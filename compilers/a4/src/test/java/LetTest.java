import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class LetTest {
	@Test
	public void testSimpleLet() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/simplelet.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("2COOL program successfully executed"));
	}
}
