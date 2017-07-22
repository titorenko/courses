package a2;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestExpressions {
	
	@Test
	public void testBooleans() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { not true }; };").parseToString();
		assertTrue("was: "+result, result.contains("comp bool_const 1"));
		
		result = new TestableParser("class A{  f() : Bool { not false}; };").parseToString();
		assertTrue("was: "+result, result.contains("comp bool_const 0"));
	}
	
	@Test
	public void testCompoundExpression() throws Exception {
		String result = new TestableParser("class A{  f() : Bool {{ not true }}; };").parseToString();
		assertTrue("was: "+result, result.contains("comp bool_const 1"));
	}
	
	@Test
	public void testSimpleParen() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { (true) }; };").parseToString();
		assertTrue("was: "+result, result.contains("bool_const 1"));
	}
	
	@Test
	public void testSimpleLet() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { let c : Bool in true }; };").parseToString();
		assertTrue("was: "+result, result.contains("let c Bool"));
	}
	
	@Test
	public void testLetWithAssign() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { let c : Bool <- true in c }; };").parseToString();
		assertTrue("was: "+result, result.contains("let c Bool"));
	}
	
	@Test
	public void testLetWithMultiAssign() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { let c : Bool <- true, i: Int <- 0, z: String in c }; };").parseToString();
		assertTrue("was: "+result, result.contains("let c Bool"));
	}
	
	@Test
	public void testMethodCall() throws Exception {
		String result = new TestableParser("class A{  f() : SELF_TYPE { out_string(\"Hello\") }; };").parseToString();
		assertTrue("was: "+result, result.contains("dispatch"));
	}
	
	@Test
	public void testMethodCallWithType() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { (new C)@A.method5() }; };").parseToString();
		assertTrue("was: "+result, result.contains("static_dispatch"));
	}
	
	@Test
	public void testMethodCallOnId() throws Exception {
		String result = new TestableParser("class A{  f() : SELF_TYPE { self.out_string(\"Hello\") }; };").parseToString();
		assertTrue("was: "+result, result.contains("dispatch"));
	}
	
	@Test
	public void testSimpleIf() throws Exception {
		String result = new TestableParser("class A{  f() : SELF_TYPE { if (true) then false else true fi }; };").parseToString();
		assertTrue("was: "+result, result.contains("cond"));
	}
	
	@Test
	public void testBlock() throws Exception {
		String result = new TestableParser("class A{  f() : Bool { {true; false; } }; };").parseToString();
		assertTrue("was: "+result, result.contains("block list bool_const 1 bool_const 0"));
	}
	
	@Test
	public void testAssignment() throws Exception {
		String result = new TestableParser("class A{  bar():Object{foo <- 3 }; };").parseToString();
		assertTrue("was: "+result, result.contains("assign"));
		
	}
	
}
