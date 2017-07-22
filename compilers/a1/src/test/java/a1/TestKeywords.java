package a1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import a1.TokenConstants;


public class TestKeywords {
	
	@Test
	public void testSimpleKeywords() {
		assertEquals(TokenConstants.CLASS,  new TestableLexer("class ").next_token().sym);
		assertFalse(TokenConstants.CLASS == new TestableLexer("classx").next_token().sym);
		
		assertEquals(TokenConstants.INHERITS,  new TestableLexer("inherits ").next_token().sym);
		
		assertEquals(TokenConstants.CASE,  new TestableLexer("case ").next_token().sym);
		assertEquals(TokenConstants.OF,  new TestableLexer("of ").next_token().sym);
		assertEquals(TokenConstants.ESAC,  new TestableLexer("esac ").next_token().sym);
		
		assertEquals(TokenConstants.IF,  new TestableLexer("if ").next_token().sym);
		assertEquals(TokenConstants.THEN,  new TestableLexer("then ").next_token().sym);
		assertEquals(TokenConstants.ELSE,  new TestableLexer("else ").next_token().sym);
		assertEquals(TokenConstants.FI,  new TestableLexer("fi ").next_token().sym);
		
		assertEquals(TokenConstants.WHILE,  new TestableLexer("while ").next_token().sym);
		assertEquals(TokenConstants.LOOP,  new TestableLexer("loop ").next_token().sym);
		assertEquals(TokenConstants.POOL,  new TestableLexer("pool ").next_token().sym);
		
		assertEquals(TokenConstants.LET,  new TestableLexer("let ").next_token().sym);
		assertEquals(TokenConstants.IN,  new TestableLexer("in ").next_token().sym);
		
		assertEquals(TokenConstants.NEW,  new TestableLexer("new ").next_token().sym);
	}
	
	@Test
	public void testKeywordCaseInsensitivity() {
		assertEquals(TokenConstants.ELSE,  new TestableLexer("Else ").next_token().sym);
		assertEquals(TokenConstants.ELSE,  new TestableLexer("eLse ").next_token().sym);
		assertEquals(TokenConstants.ELSE,  new TestableLexer("ELSE ").next_token().sym);
	}
	
	
}
