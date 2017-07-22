import java.util.List;


public class CodeGenerator {
	
	private method newInitMethod(final int nt) { 
		return new method(0, null, null, null, null) {
			public int getArgsNumber() {
				return 0;
			}
			
			public int nt() {
				return nt;	
			}
		};
	}
	
	String codeObjectInit(CgenNode node) {
		method initMethod = newInitMethod(getAttributeInitTemps(node));
		CgenNode parent = node.getParentNd();
		String parentInitName = parent.isNoClass() ? null : parent.getClassInitMethodName();
		StringBuffer result = new StringBuffer();
		result.append(node.getClassInitMethodName()+":\n");
		result.append(initMethod.encodeEntry());
		result.append(callFunction(parentInitName));
		result.append(encodeAttributeInit(node));
		result.append("\tmove $a0 $s0\n");
		result.append(initMethod.encodeExit());
		return result.toString();
	}
	
	int getAttributeInitTemps(CgenNode node) {
		int max = 0;
		for (attr a : node.getAttributes()) {
			if (a.nt() > max) max = a.nt();
		}
		return max;
	}
	
	private String encodeAttributeInit(CgenNode node) {
		StringBuffer result = new StringBuffer();
		int idx = node.getAllAttributesInOrder().size()-node.getAttributes().size();
		for (attr attr : node.getAttributes()) {
			result.append(attr.encodeInit(node, idx));
			idx++;
		}
		return result.toString();
	}

	private String callFunction(String name) {
		return  (name == null ? "" : "\tjal	"+name+"\n");
	}
	
	String encodeFunctions(CgenNode node) {
		StringBuffer result = new StringBuffer();
		List<method> methods = node.getMethods();
		Context ctx = new Context(node);
		for (method method : methods) {
			ctx.enterMethod(method);
			result.append(method.encode(ctx));
			ctx.exitMethod(method);
		}
		return result.toString();
	}

}