package a3;
import static org.junit.Assert.*;

import org.junit.Test;


public class SimpleLegalTest {
	
	@Test
	public void testLegalIf() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/if.cl"));
		ts.semantML();
	}
	
	@Test
	public void testLegalCase() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/simplecase.cl"));
		String result = ts.semant();
		assertTrue(result.endsWith("_object s : String : Object : Object )"));
	}
	
	@Test
	public void testListClCase() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/list.cl"));
		String result = ts.semantML();
		System.out.println(result);
		//assertTrue(result.endsWith("_object s : String : Object : Object )"));
	}
	
}
