package a1;
import static org.junit.Assert.assertEquals;
import java_cup.runtime.Symbol;

import org.junit.Test;

import a1.TokenConstants;


public class TestBooleans {
	@Test
	public void testVanillaCase() {
		TestableLexer lexer = new TestableLexer("true");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.BOOL_CONST, token.sym);
		assertEquals(Boolean.TRUE, token.value);
		
		lexer = new TestableLexer("false");
		token = lexer.next_token();
		assertEquals(TokenConstants.BOOL_CONST, token.sym);
		assertEquals(Boolean.FALSE, token.value);
	}
	
	@Test
	public void testCaseInsensitivity() {
		assertEquals(TokenConstants.BOOL_CONST,  new TestableLexer("tRUE ").next_token().sym);
		assertEquals(TokenConstants.BOOL_CONST,  new TestableLexer("truE ").next_token().sym);
		assertEquals(TokenConstants.TYPEID,  new TestableLexer("True ").next_token().sym);
	}
}
