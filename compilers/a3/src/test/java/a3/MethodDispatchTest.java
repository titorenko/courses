package a3;
import static org.junit.Assert.assertFalse;

import org.junit.Test;


public class MethodDispatchTest {
	
	@Test
	public void testSimpleClass1() throws Exception {
		String result = new TestableSemant(
					"class Main { " +
					"main(): Object {{ \"test\".length(); } };" +
					"};").semant();
		System.out.println(result);
		assertFalse(result, result.endsWith(": Int )\n"));
	}

}
