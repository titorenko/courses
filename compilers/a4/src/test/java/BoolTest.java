

import static org.junit.Assert.*;

import org.junit.Test;

public class BoolTest {
	
	@Test
	public void testBools() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/bool.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut.contains("COOL program successfully executed"));
	}
}
