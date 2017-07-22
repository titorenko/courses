package a3;


import java.io.InputStreamReader;

/** Static semantics driver class */
class Semant {

    /** Reads AST from from consosle, and outputs the new AST */
    public static void main(String[] args) {
	args = Flags.handleFlags(args);
	try {
	    ASTLexer lexer = new ASTLexer(new InputStreamReader(System.in));
	    ASTParser parser = new ASTParser(lexer);
	    Object result = parser.parse().value;
	    ((Program)result).semant();
	    ((Program)result).dump_with_types(System.out, 0);
	} catch (Exception ex) {
	    ex.printStackTrace(System.err);
	}
    }
}
