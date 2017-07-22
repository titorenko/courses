

import static org.junit.Assert.*;

import org.junit.Test;

public class DispatchTest {
	
	@Test
	public void testDynamicDispatch() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/dispatch-override-dynamic.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut.contains("base\nderived\nderived"));
	}
	
	@Test
	public void dispatchToVoid() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/dispatch-void-dynamic.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut.contains("Dispatch to void."));
	}
	
	@Test
	public void testStaticDispatch() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/dispatch-override-static.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("base\nbase\nbase\nderived"));
	}
	
	@Test
	public void testSelfDispatch() throws Exception {
		TestableCgen cgen = new TestableCgen(TestableCgen.class.getResourceAsStream("/new-self-dispatch.cl"));
		String stdOut = cgen.run();
		assertTrue(stdOut, stdOut.contains("derived"));
	}
}
