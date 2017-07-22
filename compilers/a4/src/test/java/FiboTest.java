

import static org.junit.Assert.*;

import org.junit.Test;

public class FiboTest {
	
	@Test
	public void testFibo() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/fibo.cl"));
		String stdOut = cgen.run();
		System.out.println(stdOut);
		assertTrue(stdOut.contains("610COOL program successfully executed"));
	}
}
