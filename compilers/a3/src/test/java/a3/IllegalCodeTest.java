package a3;
import org.junit.Test;

import a3.FatalValidationException;


public class IllegalCodeTest {
	@Test(expected=FatalValidationException.class)
	public void testBadEq() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/badeq.cl"));
		String result = ts.semantML();
		System.out.println(result);
	}
	
	@Test(expected=FatalValidationException.class)
	public void testBadArith() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/badarith1.cl"));
		String result = ts.semantML();
		System.out.println(result);
	}
	
	@Test(expected=FatalValidationException.class)
	public void testDuplicateBranch() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/caseduplicate.cl"));
		String result = ts.semantML();
		System.out.println(result);
	}
	
	
	@Test(expected=FatalValidationException.class)
	public void testAttrOverride() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/attroverride.cl"));
		String result = ts.semantML();
		System.out.println(result);
	}
	
	@Test(expected=FatalValidationException.class)
	public void testBadStaticDispatch() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/badstaticdispatch.cl"));
		String result = ts.semantML();
		System.out.println(result);
	}
}
