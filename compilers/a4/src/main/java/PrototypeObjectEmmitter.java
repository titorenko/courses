

import java.util.List;

class PrototypeObjectEmmitter {
	final int zeroSymbolIndex = AbstractTable.inttable.addString("0").index;
	final int emptyStringIndex = AbstractTable.stringtable.indexOf("");
	
	public String emit(CgenNode cl, int classTag) {
		StringBuffer result = new StringBuffer();
		
		result.append(CgenSupport.WORD+"-1\n");//Garbage Collector Tag
		result.append(cl.getName()+CgenSupport.PROTOBJ_SUFFIX+":\n");
		result.append(CgenSupport.WORD+classTag+"\n");//class tag
		int size = cl.getAllAttributesInOrder().size() + 3;
		result.append(CgenSupport.WORD+size+"\n");//size in 32 bit words
		result.append(CgenSupport.WORD+cl.getName()+CgenSupport.DISPTAB_SUFFIX+"\n");
		emitAttributes(result, cl);
		return result.toString();
	}

	private void emitAttributes(StringBuffer result, CgenNode cl) {
		List<attr> attributes = cl.getAllAttributesInOrder();
		for (attr attr : attributes) {
			result.append(CgenSupport.WORD+attr.getDefaultValue()+"\n");
		}
	}
	
	
	
}
