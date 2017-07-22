package a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import a1.CoolLexer;

import java_cup.runtime.Symbol;


public class TestableParser {
	
	private InputStream is;
	private CoolParser parser;

	public TestableParser(InputStream is) {
		this.is = is;
	}
	
	public TestableParser(String input) {
		this.is = new ByteArrayInputStream(input.getBytes());
	}
	
	public String parseToString() throws Exception {
		Symbol symbol = parse();
		TreeNode value = (TreeNode) symbol.value;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		value.dump(new PrintStream(os), 0);
		String result = os.toString();
		return result.replaceAll("\n|\r", "").replaceAll("\\s+", " ");
	}
	
	public Symbol parse() throws Exception {
		CoolTokenLexer tokenLexer = new CoolTokenLexer(new InputStreamReader(new ByteArrayInputStream(getLexems())));
		this.parser = new CoolParser(tokenLexer);
		return parser.parse();
	}
	
	public int getNumberOfErrors() {
		return parser.omerrs; 
	}
	
	private byte[] getLexems() throws IOException {
		CoolLexer lexer = new CoolLexer(is);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Symbol s;
		while ((s = lexer.next_token()).sym != TokenConstants.EOF) {
			a1.Utilities.dumpToken(new PrintStream(os), lexer.get_curr_lineno(), s);
		}
		byte[] result = os.toByteArray();
		os.close();
		return result;
	}
}
