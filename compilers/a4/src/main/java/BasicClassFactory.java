

public class BasicClassFactory {

	public static final BasicClassFactory INSTANCE = new BasicClassFactory();

	private AbstractSymbol filename = AbstractTable.stringtable
			.addString("<basic class>");

	class_c getObjectClass() {
		// The Object class has no parent class. Its methods are
		// cool_abort() : Object aborts the program
		// type_name() : Str returns a string representation
		// of class name
		// copy() : SELF_TYPE returns a copy of the object

		return new class_c(
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
	}

	class_c getIoClass() {
		// The IO class inherits from Object. Its methods are
		// out_string(Str) : SELF_TYPE writes a string to the output
		// out_int(Int) : SELF_TYPE "    an int    " "     "
		// in_string() : Str reads a string from the input
		// in_int() : Int "   an int     " "     "

		return new class_c(
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
	}

	class_c getStrClass() {
		// The class Str has a number of slots and operations:
		// val the length of the string
		// str_field the string itself
		// length() : Int returns length of the string
		// concat(arg: Str) : Str performs string concatenation
		// substr(arg: Int, arg2: Int): Str substring selection

		return new class_c(
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
	}
}
