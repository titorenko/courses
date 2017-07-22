package a3;
import static org.junit.Assert.*;

import org.junit.Test;

import a3.FatalValidationException;


public class TestClassTree {
	
	
	@Test
	public void testCLTree() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/basicclasstree.cl"));
		String result = ts.semantML();
		assertFalse(result, result.contains("no_type"));
	}
	
	@Test(expected=FatalValidationException.class)
	public void testSelfInParamList() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/self.cl"));
		ts.semantML();
	}
}
