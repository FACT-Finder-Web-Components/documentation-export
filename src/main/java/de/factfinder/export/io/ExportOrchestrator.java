package de.factfinder.export.io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import de.factfinder.export.io.csv.FactFinderExportWriter;
import de.factfinder.export.io.markdown.MarkdownParser;

public final class ExportOrchestrator {

	private static String getLanguage(File directory) {
		return directory.getName();
	}

	public static void runExport(String inputBaseDir, String outputBaseDir, final String baseUrl) throws IOException {

		final File inputDir = new File(inputBaseDir);

		String documentationFilename = "documentation-".concat(getLanguage(inputDir)).concat(".csv");
		String apiFilename = "api-".concat(getLanguage(inputDir)).concat(".csv");

		List<File> apiFiles = FileWalker.readOnlyApiFiles(inputDir);
		List<File> docuFiles = FileWalker.readOnlyDocumentationFiles(inputDir);

		StringBuilder documentationCsvContent = new StringBuilder("id;title;code;description;headings;deeplink\n");
		List<String> parsedDocuFiles = docuFiles.stream().map(file -> MarkdownParser.parseDocumentation(file, baseUrl)).collect(Collectors.toList());
		for (int i = 0; i < parsedDocuFiles.size(); i++) {
			documentationCsvContent.append(String.format("\"%d\";", i).concat(parsedDocuFiles.get(i)));
		}

		StringBuilder apiCsvContent = new StringBuilder("id;title;property;mixins;methods;events;attributes;deeplink\n");
		List<String> parsedApiFiles = apiFiles.stream().map(file -> MarkdownParser.parseApi(file, baseUrl)).collect(Collectors.toList());
		for (int i = 0; i < parsedApiFiles.size(); i++) {
			apiCsvContent.append(String.format("\"%d\";", i).concat(parsedApiFiles.get(i)));
		}

		FactFinderExportWriter csvWriter = new FactFinderExportWriter(outputBaseDir + documentationFilename);
		csvWriter.write(documentationCsvContent.toString()).closeFile();
		csvWriter = new FactFinderExportWriter(outputBaseDir + apiFilename);
		csvWriter.write(apiCsvContent.toString()).closeFile();
	}

	public ExportOrchestrator() {
		//util
	}
}
