package a2;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class WhileTest {
	@Test
	public void testSimpleEmptyClass() throws Exception {
		TestableParser parser = new TestableParser("class A {  f() : Object { while (not false) loop (\"Ooga booga\") pool  }; };");
		String result = parser.parseToString();
		assertTrue(result, result.contains("loop comp bool_const 0 string_const Ooga booga"));
	}
}
