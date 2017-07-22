package a3;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class may be used to contain the semantic information such as the
 * inheritance graph. You may use it or not as you like: it is only here to
 * provide a container for the supplied methods.
 */
class ClassTable {
	private int semantErrors;
	private PrintStream errorStream;

	private Map<AbstractSymbol, class_c> classes = new HashMap<AbstractSymbol, class_c>();
	private boolean mainMethodFound = false;

	/**
	 * Creates data structures representing basic Cool classes (Object, IO, Int,
	 * Bool, String). Please note: as is this method does not do anything
	 * useful; you will need to edit it to make if do what you want.
	 * */
	private void installBasicClasses() {
		AbstractSymbol filename = AbstractTable.stringtable
				.addString("<basic class>");

		// The following demonstrates how to create dummy parse trees to
		// refer to basic Cool classes. There's no need for method
		// bodies -- these are already built into the runtime system.

		// IMPORTANT: The results of the following expressions are
		// stored in local variables. You will want to do something
		// with those variables at the end of this method to make this
		// code meaningful.

		// The Object class has no parent class. Its methods are
		// cool_abort() : Object aborts the program
		// type_name() : Str returns a string representation
		// of class name
		// copy() : SELF_TYPE returns a copy of the object

		class_c Object_class = new class_c(
				0,
				TreeConstants.Object_,
				TreeConstants.No_class,
				new Features(0)
						.appendElement(
								new method(0, TreeConstants.cool_abort,
										new Formals(0), TreeConstants.Object_,
										new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.type_name,
										new Formals(0), TreeConstants.Str,
										new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.copy,
										new Formals(0),
										TreeConstants.SELF_TYPE, new no_expr(0))),
				filename);
		register(Object_class);

		// The IO class inherits from Object. Its methods are
		// out_string(Str) : SELF_TYPE writes a string to the output
		// out_int(Int) : SELF_TYPE "    an int    " "     "
		// in_string() : Str reads a string from the input
		// in_int() : Int "   an int     " "     "

		class_c IO_class = new class_c(
				0,
				TreeConstants.IO,
				TreeConstants.Object_,
				new Features(0)
						.appendElement(
								new method(0, TreeConstants.out_string,
										new Formals(0)
												.appendElement(new formalc(0,
														TreeConstants.arg,
														TreeConstants.Str)),
										TreeConstants.SELF_TYPE, new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.out_int,
										new Formals(0)
												.appendElement(new formalc(0,
														TreeConstants.arg,
														TreeConstants.Int)),
										TreeConstants.SELF_TYPE, new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.in_string,
										new Formals(0), TreeConstants.Str,
										new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.in_int,
										new Formals(0), TreeConstants.Int,
										new no_expr(0))), filename);
		register(IO_class);

		// The Int class has no methods and only a single attribute, the
		// "val" for the integer.

		class_c Int_class = new class_c(0, TreeConstants.Int,
				TreeConstants.Object_, new Features(0).appendElement(new attr(
						0, TreeConstants.val, TreeConstants.prim_slot,
						new no_expr(0))), filename);
		register(Int_class);

		// Bool also has only the "val" slot.
		class_c Bool_class = new class_c(0, TreeConstants.Bool,
				TreeConstants.Object_, new Features(0).appendElement(new attr(
						0, TreeConstants.val, TreeConstants.prim_slot,
						new no_expr(0))), filename);
		register(Bool_class);

		// The class Str has a number of slots and operations:
		// val the length of the string
		// str_field the string itself
		// length() : Int returns length of the string
		// concat(arg: Str) : Str performs string concatenation
		// substr(arg: Int, arg2: Int): Str substring selection

		class_c Str_class = new class_c(
				0,
				TreeConstants.Str,
				TreeConstants.Object_,
				new Features(0)
						.appendElement(
								new attr(0, TreeConstants.val,
										TreeConstants.Int, new no_expr(0)))
						.appendElement(
								new attr(0, TreeConstants.str_field,
										TreeConstants.prim_slot, new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.length,
										new Formals(0), TreeConstants.Int,
										new no_expr(0)))
						.appendElement(
								new method(0, TreeConstants.concat,
										new Formals(0)
												.appendElement(new formalc(0,
														TreeConstants.arg,
														TreeConstants.Str)),
										TreeConstants.Str, new no_expr(0)))
						.appendElement(
								new method(
										0,
										TreeConstants.substr,
										new Formals(0)
												.appendElement(
														new formalc(
																0,
																TreeConstants.arg,
																TreeConstants.Int))
												.appendElement(
														new formalc(
																0,
																TreeConstants.arg2,
																TreeConstants.Int)),
										TreeConstants.Str, new no_expr(0))),
				filename);
		register(Str_class);

	}

	private void register(class_c clazz) {
		validate(clazz);
		AbstractSymbol name = clazz.getName();
		if (classes.containsKey(name)) {
			semantError(clazz).println(
					"Class " + name + " was previously defined");
		}
		classes.put(name, clazz);
	}

	private void validate(class_c clazz) {
		if (clazz.getName().equals(TreeConstants.SELF_TYPE)) {
			semantError(clazz).println(
					"'SELF_TYPE' cannot be used as class name");
			return;
		}

		List<AbstractSymbol> names = getAttributeNames(clazz);
		if (names.contains(TreeConstants.self)) {
			semantError(clazz).println(
					"'self' cannot be the name of an attribute in " + clazz);
		}
		for (AbstractSymbol name : names) {
			if (Collections.frequency(names, name) > 1) {
				semantError(clazz).println(
						"attr " + name + " is already defined in class "
								+ clazz);
			}
		}

		List<method> methods = clazz.getMethods();
		names.clear();
		for (method m : methods) {
			if (names.contains(m.getName())) {
				semantError(clazz)
						.println(
								"method " + m + " is already defined in class "
										+ clazz);
			} else {
				names.add(m.getName());
			}
			if (m.getName().str.equals("main")
					&& clazz.getName() == TreeConstants.Main) {
				mainMethodFound = true;
			}
		}
	}

	private List<AbstractSymbol> getAttributeNames(class_c clazz) {
		List<AbstractSymbol> result = new ArrayList<AbstractSymbol>();
		List<attr> attributes = clazz.getAttributes();
		for (attr attr : attributes) {
			result.add(attr.getName());
		}
		return result;
	}

	private List<AbstractSymbol> getParentAttributeNames(class_c clazz) {
		List<AbstractSymbol> result = new ArrayList<AbstractSymbol>();
		List<AbstractSymbol> parents = getStrictParents(clazz.getName());
		for (AbstractSymbol parent : parents) {
			result.addAll(getAttributeNames(classes.get(parent)));
		}
		return result;
	}

	public ClassTable(Classes cls) {
		semantErrors = 0;
		errorStream = System.err;
		installBasicClasses();
		List<TreeNode> children = cls.getChildren();
		for (TreeNode treeNode : children) {
			class_c clazz = (class_c) treeNode;
			if (clazz.getName().equals(TreeConstants.Object_)
					|| clazz.getName().equals(TreeConstants.Int)
					|| clazz.getName().equals(TreeConstants.Bool)
					|| clazz.getName().equals(TreeConstants.IO)) {
				semantError(clazz).println(
						"Class '" + clazz.getName() + "' cannot be redefined");
			} else {
				register(clazz);
			}
		}
		if (!mainMethodFound) {
			semantError().println("Class Main is not defined.");
		}
		validateInheritance();
		validateAttributeAndMethodTypes();
	}

	private static final List<AbstractSymbol> knownTypes = Arrays
			.asList(new AbstractSymbol[] { TreeConstants.prim_slot,
					TreeConstants.SELF_TYPE });

	private void validateAttributeAndMethodTypes() {
		for (class_c c : classes.values()) {
			for (attr a : c.getAttributes()) {
				if (!isValidType(a.getType_decl())) {
					semantError(c).println(
							"Attribute " + a + " has undefined type "
									+ a.getType_decl());
				}
			}
			for (method m : c.getMethods()) {
				MethodSignature signature = m.getSignature();
				if (!isValidType(signature.getReturnType())) {
					semantError(c).println(
							"Method " + m + " has undefined type "
									+ signature.getReturnType());
				}
				for (AbstractSymbol t : signature.getParams()) {
					if (!isValidType(t)) {
						semantError(c).println(
								"Method " + m + " has undefined type param "
										+ t);
					}
				}
				Set<AbstractSymbol> names = new HashSet<AbstractSymbol>();
				for (AbstractSymbol n : signature.getNames()) {
					if (names.contains(n)) {
						semantError(c).println(
								"Method " + m + " has duplicate arg " + n);
					} else {
						names.add(n);
					}
				}
				if (names.contains(TreeConstants.self)) {
					semantError(c).println(
							"Method " + m + " has self in parameter list");
				}
			}
		}
	}

	private boolean isValidType(AbstractSymbol t) {
		if (knownTypes.contains(t))
			return true;
		return classes.containsKey(t);
	}

	private void validateInheritance() {
		for (class_c c : classes.values()) {
			Set<AbstractSymbol> parents = new HashSet<AbstractSymbol>();
			class_c parent = c;
			while (parent.getName() != TreeConstants.Object_) {
				if (parents.contains(parent.getName())) {
					semantError(c).println(
							"Found cycle in inheritance tree involving "
									+ parent);
					break;
				}
				parents.add(parent.getName());
				AbstractSymbol parentName = parent.getParent();
				class_c newParent = classes.get(parentName);
				if (newParent == null) {
					semantError(parent).println(
							"Parent undefined " + parentName + " in " + parent);
					break;
				}
				if (newParent.getName() == TreeConstants.Int
						|| newParent.getName() == TreeConstants.Str
						|| newParent.getName() == TreeConstants.Bool) {
					semantError(parent).println(
							"It is illegal to inherit from "
									+ newParent.getName());
					break;
				}
				parent = newParent;
			}

			validateAttributeOverride(c);
			validateMethodOverride(c);
		}
	}

	private void validateAttributeOverride(class_c clazz) {
		List<AbstractSymbol> names = getAttributeNames(clazz);
		List<AbstractSymbol> parentAttributes = getParentAttributeNames(clazz);
		for (AbstractSymbol name : names) {
			if (Collections.frequency(names, name) > 1) {
				semantError(clazz).println(
						"attr " + name + " is already defined in class "
								+ clazz);
			} else if (parentAttributes.contains(name)) {
				semantError(clazz).println(
						"attribute " + name
								+ " is already defined in base class");
			}
		}
	}

	private void validateMethodOverride(class_c c) {
		List<AbstractSymbol> parents = getStrictParents(c.getName());
		for (method m : c.getMethods()) {
			method overridden = findMethod(m.getName(), parents);
			if (overridden != null) {
				MethodSignature s1 = m.getSignature();
				MethodSignature s2 = overridden.getSignature();
				if (s1.getParams().length != s2.getParams().length) {
					semantError(c).println("Method "+m.getClass()+" has different number of arguments compared to the method in the base class");
				} else if (!Arrays.deepEquals(s1.getParams(), s2.getParams())) {
					semantError(c).println("Method "+m.getClass()+" has different types of arguments compared to the method in the base class");
				}
			}
		}
	}

	private method findMethod(AbstractSymbol methodName, List<AbstractSymbol> parents) {
		for (AbstractSymbol parent : parents) {
			class_c c = classes.get(parent);
			List<method> methods = c.getMethods();
			for (method method : methods) {
				if (methodName.equals(method.getName())) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * Prints line number and file name of the given class.
	 * 
	 * Also increments semantic error count.
	 * 
	 * @param c
	 *            the class
	 * @return a print stream to which the rest of the error message is to be
	 *         printed.
	 * 
	 * */
	public PrintStream semantError(class_c c) {
		return semantError(c.getFilename(), c);
	}

	/**
	 * Prints the file name and the line number of the given tree node.
	 * 
	 * Also increments semantic error count.
	 * 
	 * @param filename
	 *            the file name
	 * @param t
	 *            the tree node
	 * @return a print stream to which the rest of the error message is to be
	 *         printed.
	 * 
	 * */
	public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
		errorStream.print(filename + ":" + t.getLineNumber() + ": ");
		return semantError();
	}

	/**
	 * Increments semantic error count and returns the print stream for error
	 * messages.
	 * 
	 * @return a print stream to which the error message is to be printed.
	 * 
	 * */
	public PrintStream semantError() {
		semantErrors++;
		return errorStream;
	}

	/** Returns true if there are any static semantic errors. */
	public boolean errors() {
		return semantErrors != 0;
	}

	public AbstractSymbol getAttributeType(AbstractSymbol className,
			AbstractSymbol attrName) {
		List<AbstractSymbol> parents = getParents(className);
		for (AbstractSymbol parent : parents) {
			class_c c = classes.get(parent);
			List<attr> attrs = c.getAttributes();
			for (attr attr : attrs) {
				if (attr.getName() == attrName) {
					return attr.getType_decl();
				}
			}
		}
		semantError(classes.get(className)).println(
				"Attribute " + attrName + " is not defined in class "
						+ className);
		return TypeFactory.getObjectType();
	}

	public MethodSignature getMethodSignature(AbstractSymbol filename,
			TreeNode node, AbstractSymbol classType, AbstractSymbol methodName) {
		class_c c = classes.get(classType);
		if (c == null) {
			semantError(filename, node).println(
					"Class  " + classType + " is not defined");
			return new MethodSignature();
		}
		List<method> methods = c.getMethods();
		for (method m : methods) {
			if (m.getName() == methodName) {
				return m.getSignature();
			}
		}
		if (c.getParent() != TreeConstants.No_class) {
			return getMethodSignature(filename, node, c.getParent(), methodName);
		}
		semantError(filename, node).println(
				"Dispatch to undefined method " + methodName + ".");
		return new MethodSignature();
	}

	public boolean isAssignable(class_c clazz, AbstractSymbol left,
			AbstractSymbol right) {
		class_c lt = getClassOrSelfType(left, clazz);
		if (lt == null) {
			semantError(clazz).println("Class " + lt + " is not defined");
			return false;
		}
		class_c rt = getClassOrSelfType(right, clazz);
		if (rt == null) {
			semantError(clazz).println("Class " + rt + " is not defined");
			return false;
		}
		do {
			if (lt == rt)
				return true;
			rt = rt.getParent() == null ? null : classes.get(rt.getParent());
		} while (rt != null);
		return false;
	}

	private class_c getClassOrSelfType(AbstractSymbol name, class_c class_ctx) {
		return (name == TreeConstants.SELF_TYPE) ? class_ctx : classes
				.get(name);
	}

	private List<AbstractSymbol> getStrictParents(AbstractSymbol name) {
		List<AbstractSymbol> parents = getParents(name);
		parents.remove(name);
		return parents;
	}

	private List<AbstractSymbol> getParents(AbstractSymbol ct) {
		List<AbstractSymbol> parents = new ArrayList<AbstractSymbol>();
		class_c c = classes.get(ct);
		while (c != null) {
			parents.add(c.getName());
			if (c.getParent() == null)
				break;
			c = classes.get(c.getParent());
		}
		Collections.reverse(parents);
		return parents;
	}

	public AbstractSymbol lub(AbstractSymbol t1, AbstractSymbol t2) {
		Iterator<AbstractSymbol> parents1 = getParents(t1).iterator();
		Iterator<AbstractSymbol> parents2 = getParents(t2).iterator();
		AbstractSymbol lub = TreeConstants.Object_;
		while (parents1.hasNext() && parents2.hasNext()) {
			AbstractSymbol next1 = parents1.next();
			AbstractSymbol next2 = parents2.next();
			if (next1 != next2) {
				break;
			} else {
				lub = next1;
			}
		}
		return lub;
	}

	public AbstractSymbol lub(List<AbstractSymbol> types) {
		if (types.size() == 1)
			return types.get(0);
		AbstractSymbol club = lub(types.get(0), types.get(1));
		for (int i = 2; i < types.size(); i++) {
			club = lub(club, types.get(i));
		}
		return club;
	}
}
