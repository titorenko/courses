package a3;
import java.io.PrintStream;


public class Context {

	private final ClassTable classTable;
	private final SymbolTable symbolTable;
	
	private class_c clazz;

	public Context(ClassTable classTable) {
		this.classTable = classTable;
		this.symbolTable = new SymbolTable();
	}

	public ClassTable getClassTable() {
		return classTable;
	}
	
	public boolean isAssignable(AbstractSymbol left, AbstractSymbol right) {
		return classTable.isAssignable(clazz, left, right); 
	}
	
	public void reportError(TreeNode node, String errorMsg) {
		PrintStream stream = classTable.semantError(clazz.getFilename(), node);
		stream.println(errorMsg);
	}

	public void setCurrentClass(class_c clazz) {
		if (this.clazz != null) {
			this.symbolTable.exitScope();
		}
		this.symbolTable.enterScope();
		symbolTable.addId(TreeConstants.self, TreeConstants.SELF_TYPE);
		this.clazz = clazz;
	}
	
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public MethodSignature getMethodTypeSignature(TreeNode node, AbstractSymbol classType, AbstractSymbol methodName) {
		if (classType == TreeConstants.SELF_TYPE) classType = clazz.getName();
		return classTable.getMethodSignature(clazz.getFilename(), node, classType, methodName);
	}

	public AbstractSymbol getAttributeType(AbstractSymbol name) {
		Object localVar = symbolTable.lookup(name);
		if (localVar != null) {
			return (AbstractSymbol) localVar;
		} else {
			return classTable.getAttributeType(clazz.getName(), name);
		}
	}

	public void addId(TreeNode node, AbstractSymbol id, AbstractSymbol type) {
		if (isIllegalIdentifier(id)) {
			reportError(node, "'self' cannot be bound in a 'let' expression.");
		} else if (symbolTable.probe(id) != null) {
			reportError(node, "Identifier "+id+" is already defined");
		} else {
			symbolTable.addId(id, type);
		}
	}

	public boolean isIllegalIdentifier(AbstractSymbol identifier) {
		return "self".equals(identifier.str);
	}

	public AbstractSymbol getTypeOrSelfType(Expression expr) {
		AbstractSymbol type = expr.get_type();
		return getTypeOrSelfType(type);
	}

	public AbstractSymbol getTypeOrSelfType(AbstractSymbol type) {
		if (type == TreeConstants.SELF_TYPE) {
			return clazz.getName();
		}
		return type;
	}

	
}
