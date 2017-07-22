package a3;
import org.junit.Test;


public class HairyScary {
	@Test
	public void testHairyScary() throws Exception {
		TestableSemant ts = new TestableSemant(getClass().getResourceAsStream("/hairyscary.cl"));
		String semantML = ts.semantML();
		System.out.println(semantML);
	}
}
