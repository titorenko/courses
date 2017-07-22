package a1;
import static org.junit.Assert.*;
import java_cup.runtime.Symbol;

import org.junit.Ignore;
import org.junit.Test;

import a1.AbstractSymbol;
import a1.TokenConstants;


public class TestDigits {
	
	@Test
	public void testOneDigit() {
		TestableLexer lexer = new TestableLexer("0");
		Symbol symbol = lexer.next_token();
		assertEquals(TokenConstants.INT_CONST, symbol.sym);
		assertTrue(symbol.value instanceof AbstractSymbol);
		assertEquals("0", ((AbstractSymbol)symbol.value).str);
	}
	
	@Test
	public void testSeveralDigits() {
		TestableLexer lexer = new TestableLexer("314");
		Symbol symbol = lexer.next_token();
		assertEquals(TokenConstants.INT_CONST, symbol.sym);
		assertTrue(symbol.value instanceof AbstractSymbol);
		assertEquals("314", ((AbstractSymbol)symbol.value).str);
	}
	
	@Test
	@Ignore
	public void testEndsWithALetter() {
		TestableLexer lexer = new TestableLexer("314x");
		Symbol symbol = lexer.next_token();
		assertFalse(TokenConstants.INT_CONST == symbol.sym);
	}
}