package a2;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestMethodDefs {
	@Test
	public void testClassWithMethod() throws Exception {
		TestableParser parser = new TestableParser("class A{  f() : Int { 0 }; };");
		String result = parser.parseToString();
		assertTrue(result.contains("method f list (end_of_list) Int int_const 0"));
	}
	
	@Test
	public void testClassWithStringConst() throws Exception {
		TestableParser parser = new TestableParser("class A{  f() : String { \"hi\" }; };");
		String result = parser.parseToString();
		assertTrue("Was: "+result, result.contains("method f list (end_of_list) String string_const hi"));
	}
	
	@Test
	public void testMethodWithArg() throws Exception {
		TestableParser parser = new TestableParser("class A{  f(i : Int) : Int { 0 }; };");
		String result = parser.parseToString();
		assertTrue(result.contains("method f list formalc i Int (end_of_list) Int int_const 0"));
	}
}
