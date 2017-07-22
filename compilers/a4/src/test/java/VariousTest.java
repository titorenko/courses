

import static org.junit.Assert.*;

import org.junit.Test;

public class VariousTest {
	
	@Test
	public void testAbort() throws Exception {
		String stdOut = run("/abort.cl");
		assertTrue(stdOut.contains("Abort called from class Main"));
		assertFalse(stdOut.contains("COOL program successfully executed"));
	}
	
	@Test
	public void testSelf() throws Exception {
		String stdOut = run("/self.cl");
		assertTrue(stdOut, stdOut.contains("1COOL"));
	}
	
	@Test
	public void testSelfInit() throws Exception {
		String stdOut = run("/new-self-init.cl");
		assertTrue(stdOut, stdOut.contains("COOL"));
	}
	

	@Test
	public void testStr() throws Exception {
		String stdOut = run("/string-methods.cl");
		assertTrue(stdOut, stdOut.contains("this is a string\nis\n"));
	}
	
	@Test
	public void testMultiDispatch() throws Exception {
		String stdOut = run("/multiple-dispatch.cl");
		assertTrue(stdOut, stdOut.contains("13\n"));
		
	}
	

	private String run(String rsrs) throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream(rsrs));
		return cgen.run();
	}
}
