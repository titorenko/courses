package a3;
import org.junit.Test;

import a3.FatalValidationException;


public class DoubleDeclarationTest {
	
	@Test(expected=FatalValidationException.class)
	public void testDoubleAttr() throws Exception {
		new TestableSemant(
					"class Main { a : Int; a : Int; " +
					"main(): Object {{1;} };" +
					"};").semant();
	}
	
	@Test(expected=FatalValidationException.class)
	public void testDoubleMethod() throws Exception {
		new TestableSemant(
					"class Main { " +
					"main(): Object {{1;} }; main(): Object {{1;} };" +
					"};").semant();
	}
}
