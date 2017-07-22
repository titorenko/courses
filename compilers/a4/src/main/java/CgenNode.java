

/*
 Copyright (c) 2000 The Regents of the University of California.
 All rights reserved.

 Permission to use, copy, modify, and distribute this software for any
 purpose, without fee, and without written agreement is hereby granted,
 provided that the above copyright notice and the following two
 paragraphs appear in all copies of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
 DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
 CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
 PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

// This is a project skeleton file

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

class CgenNode extends class_c {
	/** The parent of this node in the inheritance tree */
	private CgenNode parent;

	/** The children of this node in the inheritance tree */
	private Vector children;

	/** Indicates a basic class */
	final static int Basic = 0;

	/** Indicates a class that came from a Cool program */
	final static int NotBasic = 1;

	/** Does this node correspond to a basic class? */
	private int basic_status;

	private CgenClassTable table;

	/**
	 * Constructs a new CgenNode to represent class "c".
	 * 
	 * @param c
	 *            the class
	 * @param basic_status
	 *            is this class basic or not
	 * @param table
	 *            the class table
	 * */
	CgenNode(Class_ c, int basic_status, CgenClassTable table) {
		super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
		this.parent = null;
		this.children = new Vector();
		this.basic_status = basic_status;
		this.table = table;
		AbstractTable.stringtable.addString(name.getString());
	}

	void addChild(CgenNode child) {
		children.addElement(child);
	}
	
	public CgenClassTable getTable() {
		return table;
	}

	/**
	 * Gets the children of this class
	 * 
	 * @return the children
	 * */
	Enumeration getChildren() {
		return children.elements();
	}

	/**
	 * Sets the parent of this class.
	 * 
	 * @param parent
	 *            the parent
	 * */
	void setParentNd(CgenNode parent) {
		if (this.parent != null) {
			Utilities.fatalError("parent already set in CgenNode.setParent()");
		}
		if (parent == null) {
			Utilities.fatalError("null parent in CgenNode.setParent()");
		}
		this.parent = parent;
	}

	/**
	 * Gets the parent of this class
	 * 
	 * @return the parent
	 * */
	CgenNode getParentNd() {
		return parent;
	}

	/**
	 * Returns true is this is a basic class.
	 * 
	 * @return true or false
	 * */
	boolean basic() {
		return basic_status == Basic;
	}
	
	public List<CgenNode> getThisWithParents() {
		List<CgenNode> parents = getParents();
		parents.add(0, this);
		Collections.reverse(parents);
		return parents;
	}
	
	public List<CgenNode> getParents() {
		List<CgenNode> result = new ArrayList<CgenNode>();
		CgenNode current = this;
		while(current.getParentNd() != null) {
			result.add(current.getParentNd());
			current = current.getParentNd();
		}
		return result;
	}
	
	public boolean isNoClass() {
		return getName().equals(TreeConstants.No_class);
	}

	public int getIndexOfMethod(AbstractSymbol name) {
		int idx = 0;
		List<QualifiedMethod> methods = getAllMethods();
		for (QualifiedMethod qm : methods) {
			if (qm.m.name.equals(name)) return idx;
			idx++;
		}
		throw new RuntimeException("Method not found: "+name);
	}
	
	public int getIndexOfAttribute(AbstractSymbol name) {
		int idx = 0;
		for (attr a: getAllAttributesInOrder()) {
			if (a.name.equals(name)) {
				return idx; 
			}
			idx++;
		}
		return -1;
	}
	
	public List<attr> getAllAttributesInOrder() {
		List<CgenNode> hierarchy = getThisWithParents();
		List<attr> result = new ArrayList<attr>();
		for (CgenNode pn : hierarchy) {
			result.addAll(pn.getAttributes());
		}
		return result;
	}

	public List<CgenNode> getAllSubtypes() {
		List<CgenNode> result = new ArrayList<CgenNode>();
		getAllSubtypes(result);
		return result;
	}

	private void getAllSubtypes(List<CgenNode> result) {
		result.add(this);
		@SuppressWarnings("rawtypes")
		Enumeration children = getChildren();
		while (children.hasMoreElements()) {
			CgenNode child = (CgenNode) children.nextElement();
			child.getAllSubtypes(result);
		}
	}
	
	public List<QualifiedMethod> getAllMethods() {
		List<QualifiedMethod> result = new ArrayList<QualifiedMethod>();
		Set<AbstractSymbol> codedMethods = new HashSet<AbstractSymbol>();
		for (CgenNode pn : getThisWithParents()) {
			List<method> methods = pn.getMethods();
			for (method method : methods) {
				if (!codedMethods.add(method.name)) continue;
				CgenNode lowestImpl = getLowestImplementation(method.name);
				result.add(new QualifiedMethod(lowestImpl, method));
			}
		}
		return result;
	}
	
	
	private CgenNode getLowestImplementation(AbstractSymbol methodName) {
		List<CgenNode> hierarchy = getThisWithParents();
		for (int i = hierarchy.size()-1; i >=0; i--) {
			CgenNode node = hierarchy.get(i);
			List<method> methods = node.getMethods();
			for (method method : methods) {
				if (method.name.equals(methodName)) {
					return node;
				}
			}
		}
		throw new RuntimeException("method not found: "+methodName);
	}
	
	@Override
	public String toString() {
		return "cgn for "+name;
	}
}