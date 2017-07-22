package week12;

import java.util.OptionalInt;

import common.collections.IntArrayList;

public class TwoSATProblemBuilder {
	
	private IntArrayList left = new IntArrayList();
	private IntArrayList right = new IntArrayList();
	
	public static TwoSATProblemBuilder twoSAT() {
		return new TwoSATProblemBuilder();
	}
	
	public TwoSATProblemBuilder add(int v1, int v2) {
		left.add(v1);
		right.add(v2);
		return this;
	}
	
	TwoSATProblem build() {
		OptionalInt max1= left.stream().max();
		OptionalInt max2= right.stream().max();
		int n = Math.max(max1.getAsInt(), max2.getAsInt());
		return new TwoSATProblem(n, left.toArray(), right.toArray());
	}
}
