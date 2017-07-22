

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloWorldTest {
	
	@Test
	public void testPrintsHello() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/hello_world.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut.contains("Hello, World."));
		assertTrue(stdOut.contains("COOL program successfully executed"));
	}
	
	@Test
	public void testSimpleAttr() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/simple-attr.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("hello"));
		assertTrue(stdOut.contains("COOL program successfully executed"));
	}
}
