

import static org.junit.Assert.*;

import org.junit.Test;

public class NewTests {
	
	@Test
	public void testSelfType() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/new-st.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("x is 1\nx is 4\nx is 1\nx is 4"));
	}
	
	@Test
	public void testSimple() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/newbasic.cl"));
		String stdOut = cgen.run();
		System.out.println(stdOut);
		assertTrue(stdOut, stdOut.contains("Bool"));
	}
}
