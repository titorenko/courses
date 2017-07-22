// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////



import static java.lang.Math.max;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/** Defines simple phylum Program */
abstract class Program extends TreeNode {
	protected Program(int lineNumber) {
		super(lineNumber);
	}

	public abstract void dump_with_types(PrintStream out, int n);

	public abstract void semant();

	public abstract void cgen(PrintStream s);

}

/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
	protected Class_(int lineNumber) {
		super(lineNumber);
	}

	public abstract void dump_with_types(PrintStream out, int n);

	public abstract AbstractSymbol getName();

	public abstract AbstractSymbol getParent();

	public abstract AbstractSymbol getFilename();

	public abstract Features getFeatures();

}

/**
 * Defines list phylum Classes
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Classes extends ListNode {
	public final static Class elementClass = Class_.class;

	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}

	protected Classes(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}

	/** Creates an empty "Classes" list */
	public Classes(int lineNumber) {
		super(lineNumber);
	}

	/** Appends "Class_" element to this list */
	public Classes appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}

	public TreeNode copy() {
		return new Classes(lineNumber, copyElements());
	}
}

/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
	protected Feature(int lineNumber) {
		super(lineNumber);
	}

	public abstract void dump_with_types(PrintStream out, int n);

}

/**
 * Defines list phylum Features
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Features extends ListNode {
	public final static Class elementClass = Feature.class;

	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}

	protected Features(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}

	/** Creates an empty "Features" list */
	public Features(int lineNumber) {
		super(lineNumber);
	}

	/** Appends "Feature" element to this list */
	public Features appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}

	public TreeNode copy() {
		return new Features(lineNumber, copyElements());
	}
}

/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
	protected Formal(int lineNumber) {
		super(lineNumber);
	}

	public abstract void dump_with_types(PrintStream out, int n);

}

/**
 * Defines list phylum Formals
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Formals extends ListNode {
	public final static Class elementClass = Formal.class;

	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}

	protected Formals(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}

	/** Creates an empty "Formals" list */
	public Formals(int lineNumber) {
		super(lineNumber);
	}

	/** Appends "Formal" element to this list */
	public Formals appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}

	public TreeNode copy() {
		return new Formals(lineNumber, copyElements());
	}
}

/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
	protected Expression(int lineNumber) {
		super(lineNumber);
	}

	private AbstractSymbol type = null;

	public AbstractSymbol get_type() {
		return type;
	}

	public Expression set_type(AbstractSymbol s) {
		type = s;
		return this;
	}

	public abstract void dump_with_types(PrintStream out, int n);

	public void dump_type(PrintStream out, int n) {
		if (type != null) {
			out.println(Utilities.pad(n) + ": " + type.getString());
		} else {
			out.println(Utilities.pad(n) + ": _no_type");
		}
	}

	public void code(PrintStream s) {
		
	}

	public abstract String encode(Context ctx);

	public int nt() {
		return 0;
	}

}

/**
 * Defines list phylum Expressions
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Expressions extends ListNode {
	public final static Class elementClass = Expression.class;

	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}

	protected Expressions(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}

	/** Creates an empty "Expressions" list */
	public Expressions(int lineNumber) {
		super(lineNumber);
	}

	/** Appends "Expression" element to this list */
	public Expressions appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}

	public TreeNode copy() {
		return new Expressions(lineNumber, copyElements());
	}
}

/** Defines simple phylum Case */
abstract class Case extends TreeNode {
	protected Case(int lineNumber) {
		super(lineNumber);
	}

	public abstract void dump_with_types(PrintStream out, int n);


}

/**
 * Defines list phylum Cases
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Cases extends ListNode {
	public final static Class elementClass = Case.class;

	/** Returns class of this lists's elements */
	public Class getElementClass() {
		return elementClass;
	}

	protected Cases(int lineNumber, Vector elements) {
		super(lineNumber, elements);
	}

	/** Creates an empty "Cases" list */
	public Cases(int lineNumber) {
		super(lineNumber);
	}

	/** Appends "Case" element to this list */
	public Cases appendElement(TreeNode elem) {
		addElement(elem);
		return this;
	}

	public TreeNode copy() {
		return new Cases(lineNumber, copyElements());
	}
	
	public List<branch> getBranches() {
		List<branch> result = new ArrayList<branch>();
		for (TreeNode child : getChildren()) {
			result.add((branch) child);
	 	}
		return result;
	}

	public int nt() {
		int max = 0;
		for (branch tcase : getBranches()) {
			if (tcase.nt() > max) max = tcase.nt();
 		}
		return max;
	}

	public String encode(Context ctx) {
		String caseFinishLabel = ctx.nextLabel();
		StringBuffer result = new StringBuffer();
		List<branch> branches = getSortedBranches(ctx.getNode().getTable());
		for (branch branch : branches) {
			String nextCaseLabel = ctx.nextLabel();
			result.append(branch.encode(ctx, nextCaseLabel, caseFinishLabel));
			result.append(nextCaseLabel+":\n");
		}
		result.append("\tjal _case_abort\n");
		result.append(caseFinishLabel+":\n");
		return result.toString();
	}

	private List<branch> getSortedBranches(CgenClassTable tbl) {
		List<branch> branches = getBranches();
		List<branch> sorted = new ArrayList<branch>();
		while(branches.size() > 0) {
			branch b = getMostConcrete(branches, tbl);
			sorted.add(b);
			branches.remove(b);
		}
		return sorted;
	}

	private branch getMostConcrete(List<branch> branches, CgenClassTable tbl) {
		outer: for (branch candidate : branches) {
			for (branch test : branches) {
				if (candidate == test) continue;
				if (candidate.isSuperTypeOf(test, tbl)) continue outer;
			}
			return candidate;
		}
		throw new RuntimeException("Failed to select branch");
	}
}

/**
 * Defines AST constructor 'program'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class programc extends Program {
	public Classes classes;

	/**
	 * Creates "program" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for classes
	 */
	public programc(int lineNumber, Classes a1) {
		super(lineNumber);
		classes = a1;
	}

	public TreeNode copy() {
		return new programc(lineNumber, (Classes) classes.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "program\n");
		classes.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_program");
		for (Enumeration e = classes.getElements(); e.hasMoreElements();) {
			((Class_) e.nextElement()).dump_with_types(out, n + 1);
		}
	}

	/**
	 * This method is the entry point to the semantic checker. You will need to
	 * complete it in programming assignment 4.
	 * <p>
	 * Your checker should do the following two things:
	 * <ol>
	 * <li>Check that the program is semantically correct
	 * <li>Decorate the abstract syntax tree with type information by setting
	 * the type field in each Expression node. (see tree.h)
	 * </ol>
	 * <p>
	 * You are free to first do (1) and make sure you catch all semantic errors.
	 * Part (2) can be done in a second stage when you want to test the complete
	 * compiler.
	 */
	public void semant() {
		/* ClassTable constructor may do some semantic analysis */
		ClassTable classTable = new ClassTable(classes);

		/* some semantic analysis code may go here */

		if (classTable.errors()) {
			System.err
					.println("Compilation halted due to static semantic errors.");
			System.exit(1);
		}
	}

	/**
	 * This method is the entry point to the code generator. All of the work of
	 * the code generator takes place within CgenClassTable constructor.
	 * 
	 * @param s
	 *            the output stream
	 * @see CgenClassTable
	 * */
	public void cgen(PrintStream s) {
		// spim wants comments to start with '#'
		s.print("# start of generated code\n");

		CgenClassTable codegen_classtable = new CgenClassTable(classes, s);
		codegen_classtable.code();
		s.print("\n# end of generated code\n");
	}

}

/**
 * Defines AST constructor 'class_'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class class_c extends Class_ {
	public AbstractSymbol name;
	public AbstractSymbol parent;
	public Features features;
	public AbstractSymbol filename;

	/**
	 * Creates "class_" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for parent
	 * @param a2
	 *            initial value for features
	 * @param a3
	 *            initial value for filename
	 */
	public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			Features a3, AbstractSymbol a4) {
		super(lineNumber);
		name = a1;
		parent = a2;
		features = a3;
		filename = a4;
	}

	public TreeNode copy() {
		return new class_c(lineNumber, copy_AbstractSymbol(name),
				copy_AbstractSymbol(parent), (Features) features.copy(),
				copy_AbstractSymbol(filename));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "class_\n");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, parent);
		features.dump(out, n + 2);
		dump_AbstractSymbol(out, n + 2, filename);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_class");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, parent);
		out.print(Utilities.pad(n + 2) + "\"");
		Utilities.printEscapedString(out, filename.getString());
		out.println("\"\n" + Utilities.pad(n + 2) + "(");
		for (Enumeration e = features.getElements(); e.hasMoreElements();) {
			((Feature) e.nextElement()).dump_with_types(out, n + 2);
		}
		out.println(Utilities.pad(n + 2) + ")");
	}

	public AbstractSymbol getName() {
		return name;
	}
	
	public String getClassInitMethodName() {
		return getName()+CgenSupport.CLASSINIT_SUFFIX;
	}

	public AbstractSymbol getParent() {
		return parent;
	}

	public AbstractSymbol getFilename() {
		return filename;
	}

	public Features getFeatures() {
		return features;
	}
	
	public List<attr> getAttributes() {
		List<attr> result = new ArrayList<attr>();
		for (TreeNode f : features.getChildren()) {
			if (f instanceof attr) {
				result.add((attr) f);
			}
		}
		return result;
	}

	public List<method> getMethods() {
		List<method> result = new ArrayList<method>();
		for (TreeNode f : features.getChildren()) {
			if (f instanceof method) {
				result.add((method) f);
			}
		}
		return result;
	}

}

/**
 * Defines AST constructor 'method'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class method extends Feature {
	public AbstractSymbol name;
	public Formals formals;
	public AbstractSymbol return_type;
	public Expression expr;

	/**
	 * Creates "method" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for formals
	 * @param a2
	 *            initial value for return_type
	 * @param a3
	 *            initial value for expr
	 */
	public method(int lineNumber, AbstractSymbol a1, Formals a2,
			AbstractSymbol a3, Expression a4) {
		super(lineNumber);
		name = a1;
		formals = a2;
		return_type = a3;
		expr = a4;
	}

	public TreeNode copy() {
		return new method(lineNumber, copy_AbstractSymbol(name),
				(Formals) formals.copy(), copy_AbstractSymbol(return_type),
				(Expression) expr.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "method\n");
		dump_AbstractSymbol(out, n + 2, name);
		formals.dump(out, n + 2);
		dump_AbstractSymbol(out, n + 2, return_type);
		expr.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_method");
		dump_AbstractSymbol(out, n + 2, name);
		for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
			((Formal) e.nextElement()).dump_with_types(out, n + 2);
		}
		dump_AbstractSymbol(out, n + 2, return_type);
		expr.dump_with_types(out, n + 2);
	}

	public int getArgsNumber() {
		return formals.getLength();
	}
	
	public int nt() {
		return expr.nt();
	}
	
	public String encodeEntry() {
		int base = base();
		return 
		"\taddiu	$sp $sp -"+base+"\n"+
		"\tsw	$fp "+base+"($sp)\n"+
		"\tsw	$s0 "+(base-4)+"($sp)\n"+
		"\tsw	$ra "+(base-8)+"($sp)\n"+
		"\taddiu $fp $sp 4\n" +
		"\tmove $s0 $a0\n";
	}

	private int base() {
		return 12 + nt()*4;
	}
	
	
	public String encodeExit() {
		int base = base();
		return 
		"\tlw	$fp "+base+"($sp)\n"+
		"\tlw	$s0 "+(base-4)+"($sp)\n"+
		"\tlw	$ra "+(base-8)+"($sp)\n"+
		"\taddiu	$sp $sp "+(12+(getArgsNumber()+nt())*4)+"\n"+
		"\tjr	$ra\n";	
	}

	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append(ctx.getNode().getName()+"."+name+":\n");
		result.append(encodeEntry());
		result.append(expr.encode(ctx));
		result.append(encodeExit());
		return result.toString();
	}
	
	public List<formalc> getParams() {
		List<formalc> result = new ArrayList<formalc>();
		Enumeration<TreeNode> elements = formals.getElements();
		while (elements.hasMoreElements()) {
			result.add((formalc) elements.nextElement());
		}
		return result;
	}

	public String encodeParamGet(int paramIndex) {
		return "\tlw	$a0 "+(base()+paramIndex*4)+"($fp)\n";
	}

	public String encodeParamSet(int paramIndex) {
		return "\tsw	$a0 "+(base()+paramIndex*4)+"($fp)\n";
	}
}

/**
 * Defines AST constructor 'attr'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class attr extends Feature {
	public AbstractSymbol name;
	public AbstractSymbol type_decl;
	public Expression init;

	/**
	 * Creates "attr" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for type_decl
	 * @param a2
	 *            initial value for init
	 */
	public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			Expression a3) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
		init = a3;
	}

	public TreeNode copy() {
		return new attr(lineNumber, copy_AbstractSymbol(name),
				copy_AbstractSymbol(type_decl), (Expression) init.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "attr\n");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_attr");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump_with_types(out, n + 2);
	}
	
	private final static int zeroSymbolIndex = AbstractTable.inttable.addString("0").index;
	private final static int emptyStringIndex = AbstractTable.stringtable.addString("").index;

	public String encodeInit(CgenNode node, int idx) {
		int offset = 12 + idx*4;
		StringBuffer result = new StringBuffer();
		if (type_decl.equals(TreeConstants.Int) || type_decl.equals(TreeConstants.Str) || type_decl.equals(TreeConstants.Bool)) {
			String label = "initLabel_"+node.name+idx;
			result.append("\tlw	$a0 "+offset+"($s0)\n");
			result.append("\tbnez $a0 "+label+"\n");
			result.append("\tla $a0 "+type_decl+CgenSupport.PROTOBJ_SUFFIX+"\n");
			result.append("\tjal Object.copy\n");
			result.append("\tsw	$a0 "+offset+"($s0)\n");
			result.append(label+":");
		}
		if (init instanceof no_expr) return result.toString();
		result.append(init.encode(new Context(node)));
		result.append("\tsw	$a0 "+offset+"($s0)\n");
		return result.toString();
	}
	
	public String getDefaultValue() {
		if (type_decl.equals(TreeConstants.Int)) {
			return CgenSupport.INTCONST_PREFIX+zeroSymbolIndex;
		} else if (type_decl.equals(TreeConstants.Str)) {
			return CgenSupport.STRCONST_PREFIX+emptyStringIndex;
		} else if (type_decl.equals(TreeConstants.Bool)) {
			return CgenSupport.BOOLCONST_PREFIX+"0";
		} else {
			return "0";
		}
	}
	
	int nt() {
		return init.nt();
	}

	

}

/**
 * Defines AST constructor 'formal'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class formalc extends Formal {
	public AbstractSymbol name;
	public AbstractSymbol type_decl;

	/**
	 * Creates "formal" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for type_decl
	 */
	public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
	}

	public TreeNode copy() {
		return new formalc(lineNumber, copy_AbstractSymbol(name),
				copy_AbstractSymbol(type_decl));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "formal\n");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_formal");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
	}

}

/**
 * Defines AST constructor 'branch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class branch extends Case {
	public AbstractSymbol name;
	public AbstractSymbol type_decl;
	public Expression expr;

	/**
	 * Creates "branch" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for type_decl
	 * @param a2
	 *            initial value for expr
	 */
	public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			Expression a3) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
		expr = a3;
	}

	public boolean isSuperTypeOf(branch test, CgenClassTable tbl) {
		CgenNode thisClass = tbl.getClass(type_decl);
		CgenNode testClass = tbl.getClass(test.type_decl);
		return testClass.getParents().contains(thisClass);
	}

	public String encode(Context ctx, String nextCaseLabel, String caseFinishLabel) {
		StringBuffer result = new StringBuffer();
		List<CgenNode> types = getEligableTypes(ctx);
		String exprLabel = ctx.nextLabel();
		for (CgenNode cgenNode : types) {
			int classTag = cgenNode.getTable().getClassTag(cgenNode.name);
			result.append("\tbeq $s1 "+classTag+" "+exprLabel+"\n");
		}
		result.append("\tb "+nextCaseLabel+"\n");
		result.append(exprLabel+":\n");
		ctx.enterLet(name, peekTemp(ctx), setTemp(ctx));
		result.append(expr.encode(ctx));
		ctx.exitLet();

		result.append("\tb "+caseFinishLabel+"\n");
		return result.toString();
	}
	
	private List<CgenNode> getEligableTypes(Context ctx) {
		CgenClassTable table = ctx.getNode().getTable();
		return table.getClass(type_decl).getAllSubtypes();
		
	}

	public int nt() {
		return expr.nt();
	}

	public TreeNode copy() {
		return new branch(lineNumber, copy_AbstractSymbol(name),
				copy_AbstractSymbol(type_decl), (Expression) expr.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "branch\n");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
		expr.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_branch");
		dump_AbstractSymbol(out, n + 2, name);
		dump_AbstractSymbol(out, n + 2, type_decl);
		expr.dump_with_types(out, n + 2);
	}

}

/**
 * Defines AST constructor 'assign'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class assign extends Expression {
	public AbstractSymbol name;
	public Expression expr;

	/**
	 * Creates "assign" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for expr
	 */
	public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
		super(lineNumber);
		name = a1;
		expr = a2;
	}

	public TreeNode copy() {
		return new assign(lineNumber, copy_AbstractSymbol(name),
				(Expression) expr.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "assign\n");
		dump_AbstractSymbol(out, n + 2, name);
		expr.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_assign");
		dump_AbstractSymbol(out, n + 2, name);
		expr.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append(expr.encode(ctx));
		result.append(ctx.encodeSetter(name));
		return result.toString();
	}
	
	@Override
	public int nt() {
		return expr.nt();
	}

}

/**
 * Defines AST constructor 'static_dispatch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class static_dispatch extends Expression {
	public Expression expr;
	public AbstractSymbol type_name;
	public AbstractSymbol name;
	public Expressions actual;

	/**
	 * Creates "static_dispatch" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for expr
	 * @param a1
	 *            initial value for type_name
	 * @param a2
	 *            initial value for name
	 * @param a3
	 *            initial value for actual
	 */
	public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2,
			AbstractSymbol a3, Expressions a4) {
		super(lineNumber);
		expr = a1;
		type_name = a2;
		name = a3;
		actual = a4;
	}

	public TreeNode copy() {
		return new static_dispatch(lineNumber, (Expression) expr.copy(),
				copy_AbstractSymbol(type_name), copy_AbstractSymbol(name),
				(Expressions) actual.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "static_dispatch\n");
		expr.dump(out, n + 2);
		dump_AbstractSymbol(out, n + 2, type_name);
		dump_AbstractSymbol(out, n + 2, name);
		actual.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_static_dispatch");
		expr.dump_with_types(out, n + 2);
		dump_AbstractSymbol(out, n + 2, type_name);
		dump_AbstractSymbol(out, n + 2, name);
		out.println(Utilities.pad(n + 2) + "(");
		for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
			((Expression) e.nextElement()).dump_with_types(out, n + 2);
		}
		out.println(Utilities.pad(n + 2) + ")");
		dump_type(out, n);
	}


	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer(); 
		
		List<TreeNode> params = actual.getChildren();
		int index = 0;
		result.append("\taddiu	$sp $sp "+(-4*params.size())+"\n");;
		for (TreeNode p : params) {
			Expression param = (Expression) p;
			result.append(param.encode(ctx));
			int offset = (index+1) * 4; 
			result.append("\tsw	$a0 "+offset+"($sp)\n");
			index++;
			   
		}
		
		result.append(expr.encode(ctx));
		result.append(checkForNull(ctx));
		CgenNode target = ctx.getNode().getTable().getClass(type_name);
		int idx = target.getIndexOfMethod(name);
		result.append("\tla $t1 "+type_name+CgenSupport.DISPTAB_SUFFIX+"\n");
		result.append("\tlw	$t1 "+(idx*4)+"($t1)\n");
		result.append("\tjalr $t1\n");
		return result.toString();
	}
	
	private String checkForNull(Context ctx) {
		StringBuffer result = new StringBuffer(); 
		ctx.nextLabel();
		String dispatchNotNullLabel = ctx.nextLabel();
		result.append("\tbnez $a0 "+dispatchNotNullLabel+"\n");
		result.append("\tla	$a0 str_const"+ctx.getNode().filename.index+"\n");
		result.append("\tli	$t1 "+getLineNumber()+"\n");
		result.append("jal _dispatch_abort\n");
		result.append(dispatchNotNullLabel+":\n");
		return result.toString();
	}

	@Override
	public int nt() {
		int result = 0;
		for (TreeNode p : actual.getChildren()) {
			Expression param = (Expression) p;
			if (param.nt() > result) result = param.nt();
		}
		return result;
	}
}

/**
 * Defines AST constructor 'dispatch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class dispatch extends Expression {
	public Expression expr;
	public AbstractSymbol name;
	public Expressions actual;

	/**
	 * Creates "dispatch" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for expr
	 * @param a1
	 *            initial value for name
	 * @param a2
	 *            initial value for actual
	 */
	public dispatch(int lineNumber, Expression a1, AbstractSymbol a2,
			Expressions a3) {
		super(lineNumber);
		expr = a1;
		name = a2;
		actual = a3;
	}

	public TreeNode copy() {
		return new dispatch(lineNumber, (Expression) expr.copy(),
				copy_AbstractSymbol(name), (Expressions) actual.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "dispatch\n");
		expr.dump(out, n + 2);
		dump_AbstractSymbol(out, n + 2, name);
		actual.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_dispatch");
		expr.dump_with_types(out, n + 2);
		dump_AbstractSymbol(out, n + 2, name);
		out.println(Utilities.pad(n + 2) + "(");
		for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
			((Expression) e.nextElement()).dump_with_types(out, n + 2);
		}
		out.println(Utilities.pad(n + 2) + ")");
		dump_type(out, n);
	}


	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer(); 
		
		List<TreeNode> params = actual.getChildren();
		if (name.str.equals("substr")) {
			for (TreeNode p : params) {
				Expression param = (Expression) p;
				result.append(param.encode(ctx));
				result.append(push());
			}
		} else {
			int index = 0;
			result.append("\taddiu	$sp $sp "+(-4*params.size())+"\n");;
			for (TreeNode p : params) {
				Expression param = (Expression) p;
				result.append(param.encode(ctx));
				int offset = (index+1) * 4; 
				result.append("\tsw	$a0 "+offset+"($sp) #param"+index+"\n");
				index++;
				   
			}
		}
		
		CgenNode target = null;
		if (expr instanceof no_expr || expr.get_type().equals(TreeConstants.SELF_TYPE) ) {
			result.append("\tmove $a0 $s0\n");
			result.append(expr.encode(ctx));
			target = ctx.getNode();
		} else {
			result.append(expr.encode(ctx));
			target = ctx.getNode().getTable().getClass(expr.get_type());
		}
		result.append(checkForNull(ctx));
		int idx = target.getIndexOfMethod(name);
		result.append("\tlw	$t1 8($a0)\n");//dispatch table
		result.append("\tlw	$t1 "+(idx*4)+"($t1)\n");
		result.append("\tjalr $t1 #invoke "+name+": "+lineNumber+"\n");
		return result.toString();
	}
	
	private String checkForNull(Context ctx) {
		StringBuffer result = new StringBuffer(); 
		ctx.nextLabel();
		String dispatchNotNullLabel = ctx.nextLabel();
		result.append("\tbnez $a0 "+dispatchNotNullLabel+"\n");
		result.append("\tla	$a0 str_const"+ctx.getNode().filename.index+"\n");
		result.append("\tli	$t1 "+getLineNumber()+"\n");
		result.append("jal _dispatch_abort\n");
		result.append(dispatchNotNullLabel+":\n");
		return result.toString();
	}

	@Override
	public int nt() {
		int result = 0;
		for (TreeNode p : actual.getChildren()) {
			Expression param = (Expression) p;
			if (param.nt() > result) result = param.nt();
		}
		return result;
	}

}

/**
 * Defines AST constructor 'cond'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class cond extends Expression {
	public Expression pred;
	public Expression then_exp;
	public Expression else_exp;

	/**
	 * Creates "cond" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for pred
	 * @param a1
	 *            initial value for then_exp
	 * @param a2
	 *            initial value for else_exp
	 */
	public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
		super(lineNumber);
		pred = a1;
		then_exp = a2;
		else_exp = a3;
	}

	public TreeNode copy() {
		return new cond(lineNumber, (Expression) pred.copy(),
				(Expression) then_exp.copy(), (Expression) else_exp.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "cond\n");
		pred.dump(out, n + 2);
		then_exp.dump(out, n + 2);
		else_exp.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_cond");
		pred.dump_with_types(out, n + 2);
		then_exp.dump_with_types(out, n + 2);
		else_exp.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method is to be completed in
	 * programming assignment 5. (You may add or remove parameters as you wish.)
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
	}

	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append(pred.encode(ctx));
		result.append("\tlw	$a0 12($a0)\n");//unbox bool
		String thenLabel = ctx.nextLabel();
		String fiLabel = ctx.nextLabel();
		result.append("\tbgtz $a0 "+thenLabel+"\n");
		result.append(else_exp.encode(ctx));
		result.append("\tb "+fiLabel+"\n");
		result.append(thenLabel+":\n");
		result.append(then_exp.encode(ctx));
		result.append(fiLabel+":\n");
		return result.toString();
	}
	
	@Override
	public int nt() {
		return Math.max(pred.nt(), Math.max(then_exp.nt(), else_exp.nt()));
	}

}

/**
 * Defines AST constructor 'loop'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class loop extends Expression {
	public Expression pred;
	public Expression body;

	/**
	 * Creates "loop" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for pred
	 * @param a1
	 *            initial value for body
	 */
	public loop(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		pred = a1;
		body = a2;
	}

	public TreeNode copy() {
		return new loop(lineNumber, (Expression) pred.copy(),
				(Expression) body.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "loop\n");
		pred.dump(out, n + 2);
		body.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_loop");
		pred.dump_with_types(out, n + 2);
		body.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		String loopLabel = ctx.nextLabel();
		String poolLabel = ctx.nextLabel(); 
		
		result.append(loopLabel+":\n");
		result.append(pred.encode(ctx));
		result.append("\tlw	$a0 12($a0)\n");//unbox bool
		result.append("\tblez $a0 "+poolLabel+"\n");
		result.append(body.encode(ctx));
		result.append("\tb "+loopLabel+"\n");
		result.append(poolLabel+":\n");
		return result.toString();
	}
	
	@Override
	public int nt() {
		return Math.max(pred.nt(), body.nt());
	}
}

/**
 * Defines AST constructor 'typcase'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class typcase extends Expression {
	public Expression expr;
	public Cases cases;

	/**
	 * Creates "typcase" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for expr
	 * @param a1
	 *            initial value for cases
	 */
	public typcase(int lineNumber, Expression a1, Cases a2) {
		super(lineNumber);
		expr = a1;
		cases = a2;
	}

	public TreeNode copy() {
		return new typcase(lineNumber, (Expression) expr.copy(),
				(Cases) cases.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "typcase\n");
		expr.dump(out, n + 2);
		cases.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_typcase");
		expr.dump_with_types(out, n + 2);
		for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
			((Case) e.nextElement()).dump_with_types(out, n + 2);
		}
		dump_type(out, n);
	}


	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer(); 
		result.append(expr.encode(ctx));
		result.append(checkForNull(ctx));
		result.append(pushTemp(ctx));
		result.append("\tlw	$s1 0($a0)\n");
		result.append(cases.encode(ctx));
		ctx.tempNotNeeded();
		return result.toString();
	}
	
	private String checkForNull(Context ctx) {
		StringBuffer result = new StringBuffer(); 
		ctx.nextLabel();
		String dispatchNotNullLabel = ctx.nextLabel();
		result.append("\tbnez $a0 "+dispatchNotNullLabel+"\n");
		result.append("\tla	$a0 str_const"+ctx.getNode().filename.index+"\n");
		result.append("\tli	$t1 "+getLineNumber()+"\n");
		result.append("jal _case_abort2\n");
		result.append(dispatchNotNullLabel+":\n");
		return result.toString();
	}
	
	@Override
	public int nt() {
		return 1+Math.max(expr.nt(), cases.nt());
	}

}

/**
 * Defines AST constructor 'block'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class block extends Expression {
	public Expressions body;

	/**
	 * Creates "block" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for body
	 */
	public block(int lineNumber, Expressions a1) {
		super(lineNumber);
		body = a1;
	}

	public TreeNode copy() {
		return new block(lineNumber, (Expressions) body.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "block\n");
		body.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_block");
		for (Enumeration e = body.getElements(); e.hasMoreElements();) {
			((Expression) e.nextElement()).dump_with_types(out, n + 2);
		}
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method is to be completed in
	 * programming assignment 5. (You may add or remove parameters as you wish.)
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
	}

	@Override
	public String encode(Context ctx) {
		List<TreeNode> children = body.getChildren();
		StringBuffer result = new StringBuffer();
		for (TreeNode treeNode : children) {
			if (treeNode instanceof Expression) {
				Expression e = (Expression) treeNode;
				result.append(e.encode(ctx));
			}
		}
		return result.toString();
	}
	
	@Override
	public int nt() {
		int result = 0;
		for (TreeNode treeNode : body.getChildren()) {
			if (treeNode instanceof Expression) {
				Expression e = (Expression) treeNode;
				if (e.nt() > result) result = e.nt();
			}
		}
		return result;
	}

}

/**
 * Defines AST constructor 'let'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class let extends Expression {
	public AbstractSymbol identifier;
	public AbstractSymbol type_decl;
	public Expression init;
	public Expression body;

	/**
	 * Creates "let" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for identifier
	 * @param a1
	 *            initial value for type_decl
	 * @param a2
	 *            initial value for init
	 * @param a3
	 *            initial value for body
	 */
	public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			Expression a3, Expression a4) {
		super(lineNumber);
		identifier = a1;
		type_decl = a2;
		init = a3;
		body = a4;
	}

	public TreeNode copy() {
		return new let(lineNumber, copy_AbstractSymbol(identifier),
				copy_AbstractSymbol(type_decl), (Expression) init.copy(),
				(Expression) body.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "let\n");
		dump_AbstractSymbol(out, n + 2, identifier);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump(out, n + 2);
		body.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_let");
		dump_AbstractSymbol(out, n + 2, identifier);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump_with_types(out, n + 2);
		body.dump_with_types(out, n + 2);
		dump_type(out, n);
	}
	
	@Override
	public int nt() {
		return Math.max(init.nt(), 1+body.nt());
	}


	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		if (init instanceof no_expr) {
			if (type_decl.equals(TreeConstants.Int) || type_decl.equals(TreeConstants.Str) || type_decl.equals(TreeConstants.Bool)) {
				result.append("\tla $a0 "+type_decl+CgenSupport.PROTOBJ_SUFFIX+"\n");
				result.append("\tjal Object.copy\n");
			} else {
				result.append("\tmove $a0 $zero\n");
			}
		} else {
			result.append(init.encode(ctx));
		}
		result.append(pushTemp(ctx));
		ctx.enterLet(identifier, peekTemp(ctx), setTemp(ctx));
		result.append(body.encode(ctx));
		result.append("\tmove $t1 $a0\n");
		ctx.exitLet();
		result.append(popTemp(ctx));
		result.append("\tmove $a0 $t1\n");
		return result.toString();
	}

}

abstract class arifOp extends Expression {
	public Expression e1;
	public Expression e2;
	
	public arifOp(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	
	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append(e1.encode(ctx));//this should put int object to a0
		result.append("\tlw	$a0 12($a0)\n");//first attribute of int is it's value
		result.append(pushTemp(ctx));
		result.append(e2.encode(ctx));
		result.append("\tjal Object.copy\n");//prepare copy
		result.append("\tmove $t2 $a0\n");
		result.append("\tlw	$t1 12($t2)\n");
		result.append(popTemp(ctx));
		result.append("\t"+getIntruction()+" $a0 $a0 $t1\n");//add ints
		result.append("\tsw	 $a0 12($t2)\n");
		result.append("\tmove $a0 $t2\n");
		return result.toString();
	}
	
	protected abstract String getIntruction();

	@Override
	public int nt() {
		return max(e1.nt(), 1+e2.nt());
	}

}

/**
 * Defines AST constructor 'plus'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class plus extends arifOp {

	public plus(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new plus(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "plus\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_plus");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	protected String getIntruction() {
		return "add";
	}

}

/**
 * Defines AST constructor 'sub'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class sub extends arifOp {

	public sub(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new sub(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "sub\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_sub");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}


	@Override
	protected String getIntruction() {
		return "sub";
	}

}

/**
 * Defines AST constructor 'mul'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class mul extends arifOp {
	
	public mul(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new mul(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "mul\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_mul");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	protected String getIntruction() {
		return "mul";
	}

	
}

/**
 * Defines AST constructor 'divide'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class divide extends arifOp {

	public divide(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new divide(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "divide\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_divide");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	protected String getIntruction() {
		return "div";
	}

}

/**
 * Defines AST constructor 'neg'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class neg extends Expression {
	public Expression e1;

	/**
	 * Creates "neg" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 */
	public neg(int lineNumber, Expression a1) {
		super(lineNumber);
		e1 = a1;
	}

	public TreeNode copy() {
		return new neg(lineNumber, (Expression) e1.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "neg\n");
		e1.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_neg");
		e1.dump_with_types(out, n + 2);
		dump_type(out, n);
	}


	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append(e1.encode(ctx));
		result.append("\tjal Object.copy\n");
		result.append("\tlw	$t1 12($a0)\n");
		result.append("\tneg $t1 $t1\n");
		result.append("\tsw	 $t1 12($a0)\n");
		return result.toString();
	}
	
	@Override
	public int nt() {
		return e1.nt();
	}

}

/**
 * Defines AST constructor 'lt'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class lt extends binaryCmpExpression {

	public lt(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new lt(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "lt\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_lt");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	protected String getInstruction() {
		return "slt";
	}

}

abstract class binaryCmpExpression extends Expression {
	public Expression e1;
	public Expression e2;

	public binaryCmpExpression(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	
	@Override
	public String encode(Context ctx) {
		boolean comparingAttributes = false;
		if (e1.get_type() == TreeConstants.Int || e1.get_type() == TreeConstants.Bool) {
			comparingAttributes = true;
		}
		StringBuffer result = new StringBuffer();
		
		result.append("la $a0 bool_const0\n");
		result.append("jal Object.copy\n");
		result.append(pushTemp(ctx));
		
		result.append(e1.encode(ctx));
		if (comparingAttributes) {
			result.append("\tlw	$a0 12($a0)\n");
		}
		result.append(pushTemp(ctx));
		result.append(e2.encode(ctx));
		if (comparingAttributes) {
			result.append("\tlw	$a0 12($a0)\n");
		}
		result.append("\tmove $t2 $a0\n");
		result.append(popTemp(ctx));
		result.append("\tmove $t1 $a0\n");
		
		String cmp = getInstruction();
		result.append("\t"+cmp+" $t3 $t1 $t2\n");
		result.append(popTemp(ctx));
		result.append("\tsw	$t3 12($a0)\n");
		return result.toString();
	}

	protected abstract String getInstruction();

	@Override
	public int nt() {
		return max(e1.nt(), 1+e2.nt())+1;
	}
}

/**
 * Defines AST constructor 'eq'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class eq extends binaryCmpExpression {

	public eq(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new eq(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "eq\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_eq");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	protected String getInstruction() {
		return "seq";
	}
	
	@Override
	public String encode(Context ctx) {
		if (e1.get_type() == TreeConstants.Int || e1.get_type() == TreeConstants.Bool || e1.get_type() == TreeConstants.Str) {
			return encodeUtilCompare(ctx);
		} else {
			return super.encode(ctx);
		}
	
	}

	private String encodeUtilCompare(Context ctx) {
		StringBuffer result = new StringBuffer();
		
		result.append(e1.encode(ctx));
		result.append(pushTemp(ctx));
		result.append(e2.encode(ctx));
		result.append("\tmove $t2 $a0\n");
		result.append(popTemp(ctx));
		result.append("\tmove $t1 $a0\n");
		result.append("la $a0 bool_const1\n");		
		result.append("la $a1 bool_const0\n");		
		result.append("\tjal equality_test\n");
		return result.toString();
	}
}

/**
 * Defines AST constructor 'leq'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class leq extends binaryCmpExpression {
	
	public leq(int lineNumber, Expression a1, Expression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new leq(lineNumber, (Expression) e1.copy(),
				(Expression) e2.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "leq\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_leq");
		e1.dump_with_types(out, n + 2);
		e2.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	protected String getInstruction() {
		return "sle";
	}


}

/**
 * Defines AST constructor 'comp'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class comp extends Expression {
	public Expression e1;

	/**
	 * Creates "comp" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 */
	public comp(int lineNumber, Expression a1) {
		super(lineNumber);
		e1 = a1;
	}

	public TreeNode copy() {
		return new comp(lineNumber, (Expression) e1.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "comp\n");
		e1.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_comp");
		e1.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append(e1.encode(ctx));
		result.append("\tjal Object.copy\n");
		result.append("\tlw	$a1 12($a0)\n");
		result.append("\txori $a1 $a1 1\n");
		result.append("\tsw	$a1 12($a0)\n");
		return result.toString();
	}
	
	@Override
	public int nt() {
		return e1.nt();
	}

}

/**
 * Defines AST constructor 'int_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class int_const extends Expression {
	public AbstractSymbol token;

	/**
	 * Creates "int_const" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for token
	 */
	public int_const(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		token = a1;
	}

	public TreeNode copy() {
		return new int_const(lineNumber, copy_AbstractSymbol(token));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "int_const\n");
		dump_AbstractSymbol(out, n + 2, token);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_int");
		dump_AbstractSymbol(out, n + 2, token);
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method method is provided to you
	 * as an example of code generation.
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
		CgenSupport
				.emitLoadInt(CgenSupport.ACC,
						(IntSymbol) AbstractTable.inttable.lookup(token
								.getString()), s);
	}

	@Override
	public String encode(Context ctx) {
		int idx = token.index;
		return "\tla	$a0 int_const"+idx+"\n";
	}

}

/**
 * Defines AST constructor 'bool_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class bool_const extends Expression {
	public Boolean val;

	/**
	 * Creates "bool_const" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for val
	 */
	public bool_const(int lineNumber, Boolean a1) {
		super(lineNumber);
		val = a1;
	}

	public TreeNode copy() {
		return new bool_const(lineNumber, copy_Boolean(val));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "bool_const\n");
		dump_Boolean(out, n + 2, val);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_bool");
		dump_Boolean(out, n + 2, val);
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method method is provided to you
	 * as an example of code generation.
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
		CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(val), s);
	}

	@Override
	public String encode(Context ctx) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(os);
		code(stream);
		return os.toString();
	}

}

/**
 * Defines AST constructor 'string_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class string_const extends Expression {
	public AbstractSymbol token;

	/**
	 * Creates "string_const" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for token
	 */
	public string_const(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		token = a1;
	}

	public TreeNode copy() {
		return new string_const(lineNumber, copy_AbstractSymbol(token));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "string_const\n");
		dump_AbstractSymbol(out, n + 2, token);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_string");
		out.print(Utilities.pad(n + 2) + "\"");
		Utilities.printEscapedString(out, token.getString());
		out.println("\"");
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method method is provided to you
	 * as an example of code generation.
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
		CgenSupport.emitLoadString(CgenSupport.ACC,
				(StringSymbol) AbstractTable.stringtable.lookup(token
						.getString()), s);
	}

	@Override
	public String encode(Context ctx) {
		int idx = token.index;
		return "\tla $a0 str_const"+idx+"\n";
	}

}

/**
 * Defines AST constructor 'new_'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class new_ extends Expression {
	public AbstractSymbol type_name;

	/**
	 * Creates "new_" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for type_name
	 */
	public new_(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		type_name = a1;
	}

	public TreeNode copy() {
		return new new_(lineNumber, copy_AbstractSymbol(type_name));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "new_\n");
		dump_AbstractSymbol(out, n + 2, type_name);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_new");
		dump_AbstractSymbol(out, n + 2, type_name);
		dump_type(out, n);
	}


	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		if (type_name.equals(TreeConstants.SELF_TYPE)) {
			result.append("\tmove $a0 $s0\n");
			result.append("\tlw $t1 0($a0) #load class tag\n");
			result.append("\tsll $t1 $t1 3\n");//4*2*idx - offset in class table
			result.append("\tla	$t2 class_objTab\n");
			result.append("\taddu $t1 $t1 $t2\n");
			result.append("\tmove	$s1 $t1\n");
			result.append("\tlw	$a0 0($t1)\n");
			result.append("\tjal Object.copy #copy proto object\n");
			result.append("\tlw	$t1 4($s1)\n");
			result.append("\tjalr $t1 #init dynamically\n ");
		} else {
			result.append("\tla $a0 "+type_name+CgenSupport.PROTOBJ_SUFFIX+"\n");
			result.append("\tjal Object.copy\n");
			result.append("\tjal "+type_name+CgenSupport.CLASSINIT_SUFFIX+"\n");
		}
		return result.toString();
	}

	@Override
	public int nt() {
		return 1;
	}
}

/**
 * Defines AST constructor 'isvoid'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class isvoid extends Expression {
	public Expression e1;

	/**
	 * Creates "isvoid" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 */
	public isvoid(int lineNumber, Expression a1) {
		super(lineNumber);
		e1 = a1;
	}

	public TreeNode copy() {
		return new isvoid(lineNumber, (Expression) e1.copy());
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "isvoid\n");
		e1.dump(out, n + 2);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_isvoid");
		e1.dump_with_types(out, n + 2);
		dump_type(out, n);
	}

	
	@Override
	public String encode(Context ctx) {
		StringBuffer result = new StringBuffer();
		result.append("la $a0 bool_const0\n");
		result.append("jal Object.copy\n");
		result.append(pushTemp(ctx));
		result.append(e1.encode(ctx));
		result.append("\tseq $t1 $a0 $zero\n");
		result.append(popTemp(ctx));
		result.append("\tsw	$t1 12($a0)\n");
		return result.toString();
	}
	
	@Override
	public int nt() {
		return e1.nt()+1;
	}

}

/**
 * Defines AST constructor 'no_expr'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class no_expr extends Expression {
	/**
	 * Creates "no_expr" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 */
	public no_expr(int lineNumber) {
		super(lineNumber);
	}

	public TreeNode copy() {
		return new no_expr(lineNumber);
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "no_expr\n");
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_no_expr");
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method is to be completed in
	 * programming assignment 5. (You may add or remove parameters as you wish.)
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
	}

	@Override
	public String encode(Context ctx) {
		return "";
	}

}

/**
 * Defines AST constructor 'object'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class object extends Expression {
	public AbstractSymbol name;

	/**
	 * Creates "object" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 */
	public object(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		name = a1;
	}

	public TreeNode copy() {
		return new object(lineNumber, copy_AbstractSymbol(name));
	}

	public void dump(PrintStream out, int n) {
		out.print(Utilities.pad(n) + "object\n");
		dump_AbstractSymbol(out, n + 2, name);
	}

	public void dump_with_types(PrintStream out, int n) {
		dump_line(out, n);
		out.println(Utilities.pad(n) + "_object");
		dump_AbstractSymbol(out, n + 2, name);
		dump_type(out, n);
	}

	/**
	 * Generates code for this expression. This method is to be completed in
	 * programming assignment 5. (You may add or remove parameters as you wish.)
	 * 
	 * @param s
	 *            the output stream
	 * */
	public void code(PrintStream s) {
	}

	@Override
	public String encode(Context ctx) {
		return ctx.encodeGetter(name);
	}

}
