/*
 *  The scanner definition for COOL.
 */
package a1;
import java_cup.runtime.Symbol;
import java.util.HashMap;
import java.util.Map;


public class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 256;
	private final int YY_EOF = 257;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

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
	}

	private boolean yy_eof_done = false;
	private final int ML_COMMENT = 1;
	private final int YYINITIAL = 0;
	private final int STR_ESC = 4;
	private final int COMMENT = 2;
	private final int STR = 3;
	private final int STR_ERR = 5;
	private final int yy_state_dtrans[] = {
		0,
		42,
		32,
		45,
		47,
		48
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NOT_ACCEPT,
		/* 48 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,258,
"25,4:8,26,7,26:2,5,4:18,26,4,24,4:5,1,3,2,23,14,6,19,18,8:10,11,12,13,16,17" +
",4,15,9:26,4:4,10,4,9:26,20,4,21,22,4:129,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,49,
"0,1,2,3,1:2,4,5,6,7,1:2,8,1:2,9,1:13,2,1:2,10,1:9,11,12,13,14,1,15,16")[0];

	private int yy_nxt[][] = unpackFromString(17,27,
"1,2,3,4,5,6,7,6,8,9,5,10,11,12,13,14,15,5,16,17,18,19,20,21,22,5,6,-1:29,23" +
",-1:27,24,-1:28,6,-1,6,-1:18,6,-1:6,25,-1:28,8,-1:26,9:3,-1:22,26,-1:9,27,-" +
"1:27,28,-1:9,1,44:4,-1,44,33,44:19,1,29,43,46:2,30,46,30,46:19,-1:3,31,-1:2" +
"4,44:4,-1,44,-1,44:19,1,34:23,35,34:2,1,36:4,-1,36,37,36:17,38,36,1,39:4,-1" +
",39,40,39:16,41,39:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	switch(yy_lexical_state) {
		case ML_COMMENT: 
			yybegin(YYINITIAL);
			return new Symbol(TokenConstants.ERROR, "EOF in comment");
		case STR: 	
			yybegin(YYINITIAL);
			return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
	}
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -3:
						break;
					case 3:
						{ return new Symbol(TokenConstants.MULT); }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.ERROR, yytext()); }
					case -6:
						break;
					case 6:
						{}
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.MINUS); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext())); }
					case -9:
						break;
					case 9:
						{ return handleIdentifierOrTypeOrKeyword(yytext()); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.COLON); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.SEMI); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.LT); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.COMMA); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.AT); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.EQ); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.DIV); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.DOT); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.NEG); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.PLUS); }
					case -22:
						break;
					case 22:
						{ yybegin(STR); string_buf.setLength(0); }
					case -23:
						break;
					case 23:
						{ multiLineCommentNesting++; yybegin(ML_COMMENT); }
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.ERROR, "Unmatched *)");}
					case -25:
						break;
					case 25:
						{yybegin(COMMENT);}
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.LE); }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.DARROW); }
					case -29:
						break;
					case 29:
						{}
					case -30:
						break;
					case 30:
						{}
					case -31:
						break;
					case 31:
						{ multiLineCommentNesting--; if (multiLineCommentNesting <= 0) yybegin(YYINITIAL); }
					case -32:
						break;
					case 32:
						{}
					case -33:
						break;
					case 33:
						{yybegin(YYINITIAL);}
					case -34:
						break;
					case 34:
						{ Symbol result = appendToStr(yytext()); if (result !=null) return result; }
					case -35:
						break;
					case 35:
						{ yybegin(YYINITIAL); return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));}
					case -36:
						break;
					case 36:
						{ Symbol result = appendToStrEscaped(yytext()); if (result !=null) return result; }
					case -37:
						break;
					case 37:
						{ Symbol result = appendToStrEscaped(yytext()); if (result !=null) return result; }
					case -38:
						break;
					case 38:
						{ yybegin(STR_ERR); return new Symbol(TokenConstants.ERROR, "String contains escaped null character."); }
					case -39:
						break;
					case 39:
						{ }
					case -40:
						break;
					case 40:
						{ yybegin(YYINITIAL); }
					case -41:
						break;
					case 41:
						{ yybegin(YYINITIAL); }
					case -42:
						break;
					case 43:
						{}
					case -43:
						break;
					case 44:
						{}
					case -44:
						break;
					case 46:
						{}
					case -45:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
