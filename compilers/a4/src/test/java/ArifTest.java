import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ArifTest {
	@Test
	public void testAdd() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/add.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("4COOL program successfully executed"));
	}
	
	@Test
	public void testAddResults() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/addFunctionResults.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("3COOL program successfully executed"));
	}
	
	@Test
	public void testSimpleOps() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/simpleArif.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("143-1COOL program successfully executed"));
	}
	
	@Test
	public void testExp() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/exp.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("128\n729\n512"));
	}
	
	@Test
	public void testNeg() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/neg.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("worksworksCOOL program successfully executed"));
	}
}
