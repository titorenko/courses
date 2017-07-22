

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import a3.TestableSemant;


public class TestableCgen {
	private InputStream is;

	public TestableCgen(InputStream is) {
		this.is = is;
	}

	public TestableCgen(String input) {
		this.is = new ByteArrayInputStream(input.getBytes());
	}
	
	String cgen() throws Exception {
		TestableSemant semant = new TestableSemant(is);
		String semantResult = semant.semant();
		
	    ASTLexer lexer = new ASTLexer(new StringReader(semantResult));
	    ASTParser parser = new ASTParser(lexer);
	    Object result = parser.parse().value;
	    
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    ((Program)result).cgen(new PrintStream(os));
	    return os.toString();
	}
	
	String run() throws Exception {
		String code = cgen();
		return new SpimRunner().run(code);
	}
	
	
	
}
