package de.factfinder.export.io.csv;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class FactFinderExportWriter {

	private Writer out;

	public FactFinderExportWriter(String outputDir) throws IOException {
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputDir), StandardCharsets.UTF_8));
	}

	public FactFinderExportWriter write(String content) {
		try {
			this.out.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void closeFile() {
		try {
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}