package a1;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import java_cup.runtime.Symbol;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import a1.AbstractSymbol;
import a1.TokenConstants;


public class TestStrings {
	
	@Test
	public void testVanillaString() {
		TestableLexer lexer = new TestableLexer("\"hello\"");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.STR_CONST, token.sym);
		assertEquals("hello", ((AbstractSymbol)token.value).str);
	}
	
	@Test
	public void testUnclosedString() {
		TestableLexer lexer = new TestableLexer("\"hello");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("Unterminated string constant", token.value);
	}
	
	@Test
	public void testStringTooLong() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 1024; i++) {
			buffer.append(".");
		}
		assertEquals(TokenConstants.STR_CONST, new TestableLexer("\""+buffer.toString()+"\"").next_token().sym);
		
		buffer.append(".");
		TestableLexer lexer = new TestableLexer("\""+buffer.toString()+"\"");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("String constant too long", token.value);
	}
	
	@Test
	public void testStringWithNullChar() {
		TestableLexer lexer = new TestableLexer("\""+new String(new byte[1])+"\"");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("String contains null character", token.value);
	}
	
	@Test
	public void testStringWithNewLineAndTab() {
		TestableLexer lexer = new TestableLexer("\"hello\\nall\\t!\"");
		Symbol token = lexer.next_token();
		assertEquals("hello\nall\t!", ((AbstractSymbol)token.value).str);
	}
	
	@Test
	public void testStringWithBackspaceAndFormFeed() {
		TestableLexer lexer = new TestableLexer("\"\\b\\f\"");
		assertEquals("\b\f", ((AbstractSymbol)lexer.next_token().value).str);
	}
	
	@Test
	public void testSimpleEscapting() {
		TestableLexer lexer = new TestableLexer("\"\\0\"");
		assertEquals("0", ((AbstractSymbol)lexer.next_token().value).str);
				
		lexer = new TestableLexer("\"\\c\"");
		assertEquals("c", ((AbstractSymbol)lexer.next_token().value).str);
	}
	
	@Test
	public void testNullEscaping() {
		TestableLexer lexer = new TestableLexer("\"\\\u0000\"");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("String contains escaped null character.", token.value);
		assertEquals(TokenConstants.EOF, lexer.next_token().sym);
	}
	
	@Test
	public void testBadMultilineString() {
		TestableLexer lexer = new TestableLexer("\"hello\nall\"");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.ERROR, token.sym);
		assertEquals("Unterminated string constant", token.value);
	}
	
	@Test
	public void testGoodMultilineString() {
		TestableLexer lexer = new TestableLexer("\"hello\\\nall\"");
		Symbol token = lexer.next_token();
		assertEquals(TokenConstants.STR_CONST, token.sym);
		assertEquals("hello\nall", ((AbstractSymbol)token.value).str);
	}
	
	@Test
	public void testQuoteEscaping() {
		String str = "\"\\\"this is still inside the string\"";
		Symbol token = new TestableLexer(str).next_token();
		assertEquals(TokenConstants.STR_CONST, token.sym);
		assertEquals("\"this is still inside the string", ((AbstractSymbol)token.value).str);
	}
	
	@Test
	public void testLongStringsWithSlashes() throws IOException {
		InputStream is = getClass().getResourceAsStream("/longstring_escapedbackslashes.cool");
		TestableLexer lexer = new TestableLexer(IOUtils.toString(is));
		assertEquals(TokenConstants.STR_CONST, lexer.next_token().sym);
		assertEquals(TokenConstants.ERROR, lexer.next_token().sym);
	}
}