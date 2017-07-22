package a1;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import a1.TokenConstants;


public class TestPunctuationAndOperators {
	
	@Test
	public void testSimpleCases() {
		assertEquals(TokenConstants.SEMI,  new TestableLexer(";").next_token().sym);
		assertEquals(TokenConstants.COLON,  new TestableLexer(":").next_token().sym);
		assertEquals(TokenConstants.ASSIGN,  new TestableLexer("<-").next_token().sym);
		assertEquals(TokenConstants.COMMA,  new TestableLexer(",").next_token().sym);
		assertEquals(TokenConstants.AT,  new TestableLexer("@").next_token().sym);
		assertEquals(TokenConstants.DARROW,  new TestableLexer("=>").next_token().sym);
		assertEquals(TokenConstants.DIV,  new TestableLexer("/").next_token().sym);
		assertEquals(TokenConstants.DOT,  new TestableLexer(".").next_token().sym);
		assertEquals(TokenConstants.EQ,  new TestableLexer("=").next_token().sym);
		assertEquals(TokenConstants.LBRACE,  new TestableLexer("{").next_token().sym);
		assertEquals(TokenConstants.RBRACE,  new TestableLexer("}").next_token().sym);
		assertEquals(TokenConstants.LPAREN,  new TestableLexer("(").next_token().sym);
		assertEquals(TokenConstants.RPAREN,  new TestableLexer(")").next_token().sym);
		assertEquals(TokenConstants.LT,  new TestableLexer("<").next_token().sym);
		assertEquals(TokenConstants.LE,  new TestableLexer("<=").next_token().sym);
		assertEquals(TokenConstants.MINUS,  new TestableLexer("-").next_token().sym);
		assertEquals(TokenConstants.NEG,  new TestableLexer("~").next_token().sym);
		assertEquals(TokenConstants.MULT,  new TestableLexer("*").next_token().sym);
		assertEquals(TokenConstants.PLUS,  new TestableLexer("+").next_token().sym);
		assertEquals(TokenConstants.NOT,  new TestableLexer("not").next_token().sym);
		assertEquals(TokenConstants.ISVOID,  new TestableLexer("isvoid").next_token().sym);
	}
}
