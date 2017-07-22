class QualifiedMethod {
	public final CgenNode c;
	public final method m;

	public QualifiedMethod(CgenNode c, method m) {
		this.c = c;
		this.m = m;
	}

	String getDispatchEncoding() {
		return CgenSupport.WORD + c.getName() + "." + m.name;
	}
}