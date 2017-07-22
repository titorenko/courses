package a2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestSimpleClassDefs {
	
	@Test
	public void testSimpleEmptyClass() throws Exception {
		TestableParser parser = new TestableParser("class A{ };");
		String result = parser.parseToString();
		assertEquals("programc list class_c A Object list (end_of_list) default.cool (end_of_list)", result);
	}
	
	
}
