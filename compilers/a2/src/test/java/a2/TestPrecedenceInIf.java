package a2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestPrecedenceInIf {
	@Test
	public void testMethodCallOnId() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { c.x() = c.z() }; };").parseToString();
		assertEquals("programc list class_c A Object list method f list (end_of_list) Bool eq dispatch object c x list (end_of_list) dispatch object c z list (end_of_list) (end_of_list) default.cool (end_of_list)", result);
	}
}
