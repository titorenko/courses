package a2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestAttributes {
	@Test
	public void testClassWithAttribute() throws Exception {
		TestableParser parser = new TestableParser("class A{  f: String; };");
		String result = parser.parseToString();
		assertEquals("programc list class_c A Object list attr f String no_expr (end_of_list) default.cool (end_of_list)", result);
	}
	
	@Test
	public void testClassWithAttributeAndAssign() throws Exception {
		TestableParser parser = new TestableParser("class A{  f: String <- \"\" ; };");
		String result = parser.parseToString();
		assertEquals("programc list class_c A Object list attr f String string_const (end_of_list) default.cool (end_of_list)", result);
	}
	
	
	
}
