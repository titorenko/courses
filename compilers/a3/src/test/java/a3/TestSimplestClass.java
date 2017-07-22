package a3;
import static org.junit.Assert.*;

import org.junit.Test;


public class TestSimplestClass {
	@Test
	public void testSimpleClass1() throws Exception {
		String result = new TestableSemant(
					"class Main { " +
					"main(): Object {{1;} };" +
					"};").semant();
		assertFalse(result, result.contains("no_type"));
	}
	
	
	@Test
	public void testSimpleArif() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/simpleArif.cl"));
		String result = ts.semantML();
		assertFalse(result, result.contains("no_type"));
	}
	
	
	@Test
	public void testBasic() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/basic.cl"));
		String result = ts.semantML();
		assertFalse(result, result.contains("no_type"));
		assertFalse(result, result.contains("SELF_TYPE"));
	}	
	
	@Test
	public void testIsVoid() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/isvoid.cl"));
		String result = ts.semant();
		assertTrue(result, result.contains("_object x : Bool"));
	}	
	
}
