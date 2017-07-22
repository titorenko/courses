package a3;


public class TypeFactory {
	
	public static AbstractSymbol getType(String type) {
		return AbstractTable.idtable.addString(type);
	}
	
	public static AbstractSymbol getIntType() {
		return getType("Int");
	}

	public static boolean isIntType(AbstractSymbol s) {
		return getIntType() == s;
	}

	public static AbstractSymbol getObjectType() {
		return getType("Object");
	}

	public static AbstractSymbol getBoolType() {
		return getType("Bool");
	}

	public static AbstractSymbol getStringType() {
		return getType("String");
	}

	public static boolean isBoolType(AbstractSymbol s) {
		return getBoolType() == s;
	}

	public static boolean isStringType(AbstractSymbol s) {
		return getStringType() == s;
	}
}
