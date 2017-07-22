package a3;
import org.junit.Test;

import a3.FatalValidationException;


public class LetTest {
	@Test(expected=FatalValidationException.class)
	public void testBadEq() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/letbadinit.cl"));
		ts.semantML();
	}
	
	@Test
	public void testSelfType() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/letselftype.cl"));
		String result = ts.semantML();
		System.out.println(result);
	}
}
