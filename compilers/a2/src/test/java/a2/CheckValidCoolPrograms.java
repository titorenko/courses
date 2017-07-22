package a2;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java_cup.runtime.Symbol;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import a2.TokenConstants;


public class CheckValidCoolPrograms {
	@Test
	public void testAcceptValidCoolPrograms() throws Exception {
		File dir = new File(getClass().getResource("/good").toURI());
		File[] programs = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return true;//name.contains("");
			}
		});
		Map<String, Integer> results = new HashMap<String, Integer>();
		for (File p : programs) {
			String contents = FileUtils.readFileToString(p);
			TestableParser parser = new TestableParser(contents);
			Symbol smb = parser.parse();
			results.put(p.getName(), smb.sym);
		}
		for (Entry<String, Integer> e : results.entrySet()) {
			System.out.println(e);
		}
		for (Entry<String, Integer> e : results.entrySet()) {
			assertEquals("Error in file "+e.getKey()+": "+e.getValue(),  (Integer)TokenConstants.EOF, (Integer)e.getValue());
		}
	}
}
