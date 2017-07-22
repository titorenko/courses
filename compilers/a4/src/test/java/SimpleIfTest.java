

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleIfTest {
	
	@Test
	public void testSimpleIf() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/simpleif.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("0110COOL program successfully executed"));
	}
}
