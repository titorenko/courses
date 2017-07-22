package week12;

import static org.junit.Assert.*;
import static week12.TwoSATProblemBuilder.twoSAT;

import org.junit.Test;

public class TwoSATSimplifierTest {
	
	@Test
	public void testSimplificationOneVar() {
		TwoSATProblem p1 = twoSAT().add(-1, 2).add(1, -2).add(1, 3).build();
		TwoSATProblem s1 = new TwoSATSimplifier(p1).simplify();
		assertArrayEquals(new int[] {-1, 1}, s1.leftVars);
		assertArrayEquals(new int[] {2, -2}, s1.rightVars);
	}
	
	@Test
	public void testSimplificationTwoVar() {
		TwoSATProblem p1 = twoSAT().add(1, 2).add(1, -2).add(-1, 3).build();
		TwoSATProblem s1 = new TwoSATSimplifier(p1).simplify();
		assertEquals(0, s1.leftVars.length);
		assertEquals(0, s1.rightVars.length);
	}

}
