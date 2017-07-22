/*
 *  The scanner definition for COOL.
 */
 
package a1;

import java_cup.runtime.Symbol;
import java.util.HashMap;
import java.util.Map;

%%

%{
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    
    Map<String, Integer> keywords = new HashMap<String, Integer>();

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
	
	private int multiLineCommentNesting = 0;
    public int get_curr_lineno() {
		return yyline+1;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
		filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
		return filename;
    }
    
    Symbol handleIdentifierOrTypeOrKeyword(String text) {
    	if (keywords.containsKey(text.toLowerCase())) {
    		return new Symbol(keywords.get(text.toLowerCase()));
    	}
    	boolean isLowerCase = text.charAt(0)>='a' && text.charAt(0) <= 'z';
    	if (isLowerCase) { 
	    	if (text.equalsIgnoreCase("true") || text.equalsIgnoreCase("false")) {
	    		return new Symbol(TokenConstants.BOOL_CONST, Boolean.parseBoolean(text));
	    	} else {
	    		return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(text));
	    	}
	    } else {
	    	return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(text));
	    }
    }
    
    Symbol appendToStr(String nextChar) {
    	if (nextChar.charAt(0) == 0) {
    		yybegin(STR_ERR);
    		return new Symbol(TokenConstants.ERROR, "String contains null character");
    	}
    	
    	if (nextChar.equals("\n")) {
    		yybegin(YYINITIAL); 
    		return new Symbol(TokenConstants.ERROR,  "Unterminated string constant");
    	} else if (nextChar.equals("\\")) {
    		yybegin(STR_ESC);
    	} else {
    		string_buf.append(nextChar);
    	}
    	
    	if(string_buf.length() >= MAX_STR_CONST) {
    		yybegin(STR_ERR);
    		return new Symbol(TokenConstants.ERROR, "String constant too long");
    	}
    	return null;
    }
    
    Symbol appendToStrEscaped(String nextChar) {
    	if (nextChar.equals("n")) {
    		string_buf.append("\n");
    	} else if (nextChar.equals("t")) {
    		string_buf.append("\t");
    	} else if (nextChar.equals("f")) {
    		string_buf.append("\f");
    	} else if (nextChar.equals("b")) {
    		string_buf.append("\b");
    	}  else {
    		string_buf.append(nextChar);
    	}
    	yybegin(STR);
    	if(string_buf.length() >= MAX_STR_CONST) {
    		yybegin(STR_ERR);
    		return new Symbol(TokenConstants.ERROR, "String constant too long");
    	}
    	return null;
    }
%}

%init{
    keywords.put("class", TokenConstants.CLASS);
    keywords.put("inherits", TokenConstants.INHERITS);
    keywords.put("new", TokenConstants.NEW);
    keywords.put("case", TokenConstants.CASE);
    keywords.put("of", TokenConstants.OF);
    keywords.put("esac", TokenConstants.ESAC);
    keywords.put("if", TokenConstants.IF);
    keywords.put("then", TokenConstants.THEN);
    keywords.put("else", TokenConstants.ELSE);
    keywords.put("fi", TokenConstants.FI);
    keywords.put("while", TokenConstants.WHILE);
    keywords.put("loop", TokenConstants.LOOP);
    keywords.put("pool", TokenConstants.POOL);
    keywords.put("let", TokenConstants.LET);
    keywords.put("in", TokenConstants.IN);
    keywords.put("isvoid", TokenConstants.ISVOID);
    keywords.put("not", TokenConstants.NOT);
%init}

%eofval{
	switch(yy_lexical_state) {
		case ML_COMMENT: 
			yybegin(YYINITIAL);
			return new Symbol(TokenConstants.ERROR, "EOF in comment");
		case STR: 	
			yybegin(YYINITIAL);
			return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
			
	}
    return new Symbol(TokenConstants.EOF);
%eofval}

%public
%class CoolLexer
%cup
%full 
%line

%state ML_COMMENT
%state COMMENT
%state STR
%state STR_ESC
%state STR_ERR

W=[ \n\f\r\t\x0B]+
%%

<YYINITIAL,ML_COMMENT>"(""*" { multiLineCommentNesting++; yybegin(ML_COMMENT); }
<ML_COMMENT>"*"")" { multiLineCommentNesting--; if (multiLineCommentNesting <= 0) yybegin(YYINITIAL); }
<ML_COMMENT>. {}
<ML_COMMENT>[\n\r] {}
<YYINITIAL>"*"")" { return new Symbol(TokenConstants.ERROR, "Unmatched *)");}

<YYINITIAL>"--" {yybegin(COMMENT);}
<COMMENT>.* {}
<COMMENT>\n {yybegin(YYINITIAL);}

<YYINITIAL>[0-9]+ { return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext())); }
<YYINITIAL>[A-Za-z][A-Za-z0-9_]* { return handleIdentifierOrTypeOrKeyword(yytext()); }

<YYINITIAL>":"  { return new Symbol(TokenConstants.COLON); }
<YYINITIAL>";"  { return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>"<-" { return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>","  { return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>"@"  { return new Symbol(TokenConstants.AT); }
<YYINITIAL>"=>" { return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>"/"  { return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"."  { return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"="  { return new Symbol(TokenConstants.EQ); }
<YYINITIAL>"{"  { return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>"}"  { return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>"("  { return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>")"  { return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"<"  { return new Symbol(TokenConstants.LT); }
<YYINITIAL>"<=" { return new Symbol(TokenConstants.LE); }
<YYINITIAL>"-"  { return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>"~"  { return new Symbol(TokenConstants.NEG); }
<YYINITIAL>"*"  { return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"+"  { return new Symbol(TokenConstants.PLUS); }

<YYINITIAL>\"   { yybegin(STR); string_buf.setLength(0); }
<STR>\"  		{ yybegin(YYINITIAL); return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));}
<STR>[^\"]		{ Symbol result = appendToStr(yytext()); if (result !=null) return result; }

<STR_ESC>[\x00] { yybegin(STR_ERR); return new Symbol(TokenConstants.ERROR, "String contains escaped null character."); }
<STR_ESC>.  	{ Symbol result = appendToStrEscaped(yytext()); if (result !=null) return result; }
<STR_ESC>\n  	{ Symbol result = appendToStrEscaped(yytext()); if (result !=null) return result; }

<STR_ERR>\"  	{ yybegin(YYINITIAL); }
<STR_ERR>\n  	{ yybegin(YYINITIAL); }
<STR_ERR>.   	{ }

<YYINITIAL>{W} {}

.  { return new Symbol(TokenConstants.ERROR, yytext()); }
