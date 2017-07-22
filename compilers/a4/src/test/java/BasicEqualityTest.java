

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicEqualityTest {
	
	@Test
	public void testBasicEquality() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/basicequality.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("COOL program successfully executed"));
	}
}
