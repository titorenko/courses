package a1;
import static org.junit.Assert.assertEquals;
import java_cup.runtime.Symbol;

import org.junit.Test;

import a1.TokenConstants;


public class TestComments {
	
	@Test
	public void testOneLineMLComment() {
		TestableLexer lexer = new TestableLexer("(* this is a comment *)");
		Symbol symbol = lexer.next_token();
		assertEquals(TokenConstants.EOF, symbol.sym);
		
		lexer = new TestableLexer("(* this is a comment *)12");
		assertEquals(TokenConstants.INT_CONST, lexer.next_token().sym);
		
		lexer = new TestableLexer("1(* this is a comment *)");
		assertEquals(TokenConstants.INT_CONST, lexer.next_token().sym);
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
	
	@Test
	public void testMultiLineComment() {
		TestableLexer lexer = new TestableLexer("(* this is a \n ** comment *)");
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
	
	@Test
	public void testCommentsWithKeywords() {
		assertEquals(TokenConstants.EOF,  new TestableLexer("(* case of *)").next_token().sym);
		assertEquals(TokenConstants.EOF,  new TestableLexer("-- case of *)").next_token().sym);
	}
	
	@Test
	public void testNestedMLComment() {
		TestableLexer lexer = new TestableLexer("(* this is (* oops inside \n comment *) \n a comment *)");
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
		lexer = new TestableLexer("(* this is (* oops inside \n comment *) \n a comment *)1");
		assertEquals(TokenConstants.INT_CONST, lexer.next_token().sym);
	}
	
	@Test
	public void testEofUnbalancedMLComment() {
		TestableLexer lexer = new TestableLexer("(* ooops, what is that sound, a tractor?...");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("EOF in comment", token.value);
	}
	
	@Test
	public void testUnbalancedClosingComment() {
		TestableLexer lexer = new TestableLexer("(* good comment *)*)1");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("Unmatched *)", token.value);
		assertEquals(TokenConstants.INT_CONST, lexer.next_token().sym);
	}
	
	@Test
	public void testStandaloneOneLineComment() {
		TestableLexer lexer = new TestableLexer("--a comment");
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
	
	@Test
	public void testPrefixedOneLineComment() {
		TestableLexer lexer = new TestableLexer("1--a comment");
		assertEquals(TokenConstants.INT_CONST, lexer.next_token().sym);
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
	
	@Test
	public void testMLCommentInsideComment() {
		TestableLexer lexer = new TestableLexer("--ml: (* comment");
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
	
	@Test
	public void testOneLineInsideMLComment() {
		TestableLexer lexer = new TestableLexer("(* -- neither is this *)0");
		assertEquals(TokenConstants.INT_CONST, lexer.next_token().sym);
	}
	
	@Test
	public void testCommentWithStrangeSymbols() {
		String comment = new String(new char[] {0x0A, 0x0B, 0x0C, 0x0D});
		TestableLexer lexer = new TestableLexer("(* "+comment+" *)");
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
}