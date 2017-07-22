import java.util.List;


public class Context {
	private CgenNode node;
	private method method;
	
	private int tempsUsed = 0;
	private static int label = 0;
	
	private SymbolTable variables = new SymbolTable();
	
	public Context(CgenNode node) {
		this.node = node;
		variables.enterScope();
	}
	
	public void enterMethod(method method) {
		this.method = method;
		variables.enterScope();
		List<formalc> params = method.getParams();
		int index = 0;
		for (formalc param : params) {
			variables.addId(param.name, new Accessor(method.encodeParamGet(index), method.encodeParamSet(index)));
			index++;
		}
	}
	
	public void exitMethod(method method) {
		if (method != this.method) System.err.println("WARN: unexpected state in exitMethod");
		this.method = null;
		variables.exitScope();
	}
	

	public void tempUsed() {
		tempsUsed++;
	}
	
	public void tempNotNeeded() {
		tempsUsed--;
	}
	
	public int getTempSpaceUsedInBytes() {
		return tempsUsed * 4;
	}

	public int getIndexOfMethod(AbstractSymbol name) {
		return node.getIndexOfMethod(name);
	}

	public String nextLabel() {
		return "l"+label++;
	}
	

	public String encodeGetter(AbstractSymbol name) {
		if (variables.lookup(name) != null) {
			Accessor a = (Accessor) variables.lookup(name);
			return a.get;
		} else if (node.getIndexOfAttribute(name) >= 0) {
			int offset = 12 + node.getIndexOfAttribute(name)*4;
			return "\tlw	$a0 "+offset+"($s0)     #load of "+name+"\n";
		} else if (name.equals(TreeConstants.self)) {
			return "\tmove $a0 $s0\n";
		} else {
			System.err.println("!!! unknown get var: "+name);
			return "";
		}
	}
	
	public String encodeSetter(AbstractSymbol name) {
		if (variables.lookup(name) != null) {
			Accessor a = (Accessor) variables.lookup(name);
			return a.set;
		} else if (node.getIndexOfAttribute(name) >= 0) {
			int offset = 12 + node.getIndexOfAttribute(name)*4;
			return "\tsw	$a0 "+offset+"($s0)\n";
		} else {
			System.err.println("!!! unknown set var: "+name);
			return "";
		}
	}
	

	public CgenNode getNode() {
		return node;
	}

	public void enterLet(AbstractSymbol identifier, String getCode, String setCode) {
		variables.enterScope();
		variables.addId(identifier, new Accessor(getCode, setCode));
	}

	public void exitLet() {
		variables.exitScope();
	}

	public int getNodeIndex() {
		return node.getTable().getClassTag(node.name);
	}

	
}


class Accessor {
	final String get;
	final String set;

	public Accessor(String get, String set) {
		this.get = get;
		this.set = set;
	}
}