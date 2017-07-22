package a1;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import a1.TokenConstants;


public class TestUnrecongnizedSymbol {
	@Test
	public void testWithUnsupported() {
		TestableLexer lexer = new TestableLexer("&");
		assertEquals(TokenConstants.ERROR, lexer.next_token().sym);
		
		lexer = new TestableLexer("|");
		assertEquals(TokenConstants.ERROR, lexer.next_token().sym);
	}
}
