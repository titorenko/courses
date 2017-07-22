import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class LamGCTest {
	@Test
	public void testLamGC() throws Exception {
		String stdOut = run("/lam-gc.cl");
		System.out.println(stdOut);
		assertTrue(stdOut, stdOut.contains("COOL\n"));
		
	}
	

	private String run(String rsrs) throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream(rsrs));
		return cgen.run();
	}
}
