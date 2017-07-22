import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class AssignmentTest {
	@Test
	public void testAssignment() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/assignment-val.cl"));
		String stdOut = cgen.run();
		System.out.println(stdOut);
		assertTrue(stdOut, stdOut.contains("x: 3"));
		assertTrue(stdOut, stdOut.contains("y: 4"));
		assertTrue(stdOut, stdOut.contains("4COOL program successfully executed"));
	}
}
