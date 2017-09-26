package de.factfinder.export.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.factfinder.export.io.csv.DocumentationCsvWriter;
import de.factfinder.export.io.markdown.MarkdownParser;

public final class ExportOrchestrator {

	public static void runExport(String inputBaseDir, String outputBaseDir, final String baseUrl) throws IOException {

		//		List<File> apiFiles = FileWalker.readOnlyApiFiles(inputBaseDir);
		List<File> docuFiles = FileWalker.readOnlyDocumentationFiles(inputBaseDir);

		StringBuilder documentationCsvContent = new StringBuilder();
		docuFiles.stream().map(file -> MarkdownParser.parse(file, baseUrl)).forEach(documentationCsvContent::append);

		DocumentationCsvWriter csvWriter = new DocumentationCsvWriter(outputBaseDir + "documentation-en.csv");
		csvWriter.write(documentationCsvContent.toString()).closeFile();
	}

	public ExportOrchestrator() {
		//util
	}
}
