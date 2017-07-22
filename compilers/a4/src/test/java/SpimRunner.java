

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;

class SpimRunner {
	String run(String code) throws IOException {
		File spimFile = new File("spim.s");
		spimFile.delete();
		FileUtils.write(spimFile, code);
		return exec(spimFile);
	}

	private String exec(File spimFile) throws ExecuteException, IOException {
		String line = "/usr/class/cs143/cool/bin/spim " + spimFile.getAbsolutePath();
		CommandLine cmdLine = CommandLine.parse(line);
		DefaultExecutor executor = new DefaultExecutor();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		executor.setStreamHandler(streamHandler);
		executor.execute(cmdLine);
		return new String(outputStream.toByteArray());
	}
}