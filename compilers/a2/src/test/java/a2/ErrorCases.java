package a2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ErrorCases {
	
	@Test
	public void testBadBlock1() throws Exception {
		TestableParser parser = new TestableParser(getClass().getResourceAsStream("/badblock.test"));
		parser.parse();
		assertEquals(2, parser.getNumberOfErrors());
	}
	
	@Test
	public void testBadBlock2() throws Exception {
		TestableParser parser = new TestableParser(getClass().getResourceAsStream("/badblock2.test"));
		parser.parse();
		assertEquals(1, parser.getNumberOfErrors());
	}
}
