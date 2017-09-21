package de.factfinder.export;

import java.io.File;
import java.io.IOException;

import de.factfinder.export.csv.DocuCsvWriter;
import de.factfinder.export.markdown.MarkdownParser;

public class ExportMain {

	public static void main(String[] args) throws IOException {

		File file = new File(args[0]);
		String url = "http://example.com/".concat(file.getName());
		String codeBlocks = MarkdownParser.readAllCodeBlocks(file);
		String headings = MarkdownParser.readAllHeadings(file);
		String regularText = MarkdownParser.readRegularText(file);

		DocuCsvWriter csvWriter = new DocuCsvWriter(args[1]);
		final String csvContent = codeBlocks + ";;;" + url + "\n" + ";" + headings + ";;" + url + "\n" + ";;" + regularText + ";" + url + "\n";
		csvWriter.write(csvContent);
		csvWriter.closeFile();
	}
}
