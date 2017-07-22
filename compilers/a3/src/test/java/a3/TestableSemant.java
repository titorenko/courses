package a3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import a1.CoolLexer;
import a2.CoolParser;
import a2.CoolTokenLexer;

import java_cup.runtime.Symbol;

public class TestableSemant {
	private InputStream is;

	public TestableSemant(InputStream is) {
		this.is = is;
	}

	public TestableSemant(String input) {
		this.is = new ByteArrayInputStream(input.getBytes());
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
	
	private String getAST() throws Exception {
		CoolTokenLexer lexer = new CoolTokenLexer(new InputStreamReader(new ByteArrayInputStream(getLexems())));
		CoolParser parser = new CoolParser(lexer);
		Symbol result = parser.parse();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		((a2.TreeNode) result.value).dump_with_types(new PrintStream(os), 0);
		return os.toString();
	}

	public String semant() throws Exception {
		String sresult = semantML();
		return sresult.replaceAll("\n|\r", "").replaceAll("\\s+", " ");
	}
	
	public String semantML() throws Exception {
		String ast = getAST();
		ByteArrayInputStream astIs = new ByteArrayInputStream(ast.getBytes());
		ASTLexer lexer = new ASTLexer(astIs);
		ASTParser parser = new ASTParser(lexer);
		programc result = (programc) parser.parse().value;
		result.semant(true);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		((Program) result).dump_with_types(new PrintStream(os), 0);
		return os.toString();
	}
}
