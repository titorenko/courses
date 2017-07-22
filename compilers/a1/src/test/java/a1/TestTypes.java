package a1;
import static org.junit.Assert.assertEquals;
import java_cup.runtime.Symbol;

import org.junit.Test;

import a1.AbstractSymbol;
import a1.TokenConstants;


public class TestTypes {
	
	@Test
	public void testTypesAfterNew() {
		TestableLexer testableLexer = new TestableLexer("new Cube");
		assertEquals(TokenConstants.NEW, testableLexer.next_token().sym);
		Symbol token = testableLexer.next_token();
		assertEquals(TokenConstants.TYPEID, token.sym);
		assertEquals("Cube", ((AbstractSymbol)token.value).str);
	}
	
	@Test
	public void testTypesAfterInClassDeclaration() {
		TestableLexer testableLexer = new TestableLexer("class Cube inherits Object");
		assertEquals(TokenConstants.CLASS, testableLexer.next_token().sym);
		assertEquals(TokenConstants.TYPEID, testableLexer.next_token().sym);
		assertEquals(TokenConstants.INHERITS, testableLexer.next_token().sym);
		assertEquals(TokenConstants.TYPEID, testableLexer.next_token().sym);
	}
	
	@Test
	public void testTypesInVarDeclaration() {
		TestableLexer testableLexer = new TestableLexer("c: Cube");
		assertEquals(TokenConstants.OBJECTID, testableLexer.next_token().sym);
		assertEquals(TokenConstants.COLON, testableLexer.next_token().sym);
		assertEquals(TokenConstants.TYPEID, testableLexer.next_token().sym);
	}
	
	@Test
	public void testTypeRegression() {
		String input=" var : Int <- 0;";
		TestableLexer testableLexer = new TestableLexer(input);
		Symbol id = testableLexer.next_token();
		assertEquals(TokenConstants.OBJECTID, id.sym);
		assertEquals("var", ((AbstractSymbol)id.value).str);
	}
}
