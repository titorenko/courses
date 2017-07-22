

import static org.junit.Assert.*;

import org.junit.Test;

public class CaseTest {
	
	@Test
	public void testSimpleCaseStatement() throws Exception {
		String stdOut = run("/case-order.cl");
		System.out.println(stdOut);
		assertTrue(stdOut.contains("main\n"));
	}
	
	private String run(String rsrs) throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream(rsrs));
		return cgen.run();
	}
}
