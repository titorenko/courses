import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Test;


public class ClassInitTest {
	@Test
	public void testObjectClassInit() throws FileNotFoundException {
		CgenClassTable table = new CgenClassTable(new Classes(0), new PrintStream(new ByteArrayOutputStream()));
		CgenNode node = (CgenNode) table.lookup(TreeConstants.Object_);
		String result = new CodeGenerator().codeObjectInit(node);
		String expected = 
		"Object_init:"+
		"	addiu	$sp $sp -12"+
		"	sw	$fp 12($sp)"+
		"	sw	$s0 8($sp)"+
		"	sw	$ra 4($sp)"+
		"	addiu	$fp $sp 4"+
		"	move	$s0 $a0"+
		"	move	$a0 $s0"+
		"	lw	$fp 12($sp)"+
		"	lw	$s0 8($sp)"+
		"	lw	$ra 4($sp)"+
		"	addiu	$sp $sp 12"+
		"	jr	$ra";

		assertEquals(expected.replaceAll("(\n|\\s)", ""), result.replaceAll("(\n|\\s)", ""));
	}
}
