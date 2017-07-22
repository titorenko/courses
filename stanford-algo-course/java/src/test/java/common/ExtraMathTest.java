package common;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtraMathTest {
	@Test
	public void testLog2() {
		assertEquals(0, ExtraMath.log2(1));
		assertEquals(1, ExtraMath.log2(2));
		assertEquals(3, ExtraMath.log2(7));
		assertEquals(3, ExtraMath.log2(8));
		assertEquals(4, ExtraMath.log2(9));
	}
}
