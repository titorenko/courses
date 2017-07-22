class A {
    foo():SELF_TYPE {{ 0;self; }};
	bar():Int { 1 };
};

class Main inherits IO {
	a: A <- new A;
	 
	main():Object {{
		out_int(a.foo().bar());
	}};
};
