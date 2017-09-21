package de.factfinder.export;

import java.io.File;
import java.io.IOException;

import de.factfinder.export.csv.DocumentationCsvWriter;
import de.factfinder.export.markdown.MarkdownParser;

public class ExportMain {

	public static void main(String[] args) throws IOException {

		File file = new File(args[0]);
		String url = "http://example.com/".concat(file.getName());
		String codeBlocks = MarkdownParser.readAllCodeBlocks(file);
		String headings = MarkdownParser.readAllHeadings(file);
		String regularText = MarkdownParser.readRegularText(file);

		DocumentationCsvWriter csvWriter = new DocumentationCsvWriter(args[1]);
		final String csvContent = "\"" + codeBlocks +"\";\"" + headings + "\";\"" + regularText + "\";\"" + url;
		csvWriter.write(csvContent);
		csvWriter.closeFile();
	}
}
