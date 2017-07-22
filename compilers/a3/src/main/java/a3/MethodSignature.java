package a3;

import java.util.List;


public class MethodSignature {
	final AbstractSymbol[] params;
	final AbstractSymbol[] names;
	final AbstractSymbol returnType;
	
	public MethodSignature() {
		params = new AbstractSymbol[0];
		names = new AbstractSymbol[0];
		returnType = TypeFactory.getObjectType();
	}
	
	public MethodSignature(List<AbstractSymbol> paramNames, List<AbstractSymbol> paramTypes, AbstractSymbol returnType) {
		this.params = paramTypes.toArray(new AbstractSymbol[0]);
		this.names = paramNames.toArray(new AbstractSymbol[0]);
		this.returnType = returnType;
	}

	public AbstractSymbol[] getParams() {
		return params;
	}
	
	public AbstractSymbol[] getNames() {
		return names;
	}
	
	public AbstractSymbol getReturnType() {
		return returnType;
	}

	public boolean isApplicable(Context ctx, List<AbstractSymbol> paramTypes) {
		for (int i = 0; i < params.length; i++) {
			if (!ctx.isAssignable(params[i], paramTypes.get(i))) {
				return false;
			}
		}
		return true;
	}
}
