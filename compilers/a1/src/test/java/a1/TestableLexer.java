package a1;
import java.io.StringReader;

import a1.CoolLexer;

import java_cup.runtime.Symbol;


public class TestableLexer extends CoolLexer {
	
	TestableLexer(String input) {
		super(new StringReader(input));
	}
	 
	@Override
	public Symbol next_token()  {
		try {
			return super.next_token();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
