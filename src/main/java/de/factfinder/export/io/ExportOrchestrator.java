package de.factfinder.export.io;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import de.factfinder.export.io.csv.FactFinderExportWriter;
import de.factfinder.export.io.markdown.MarkdownParser;
import org.apache.commons.cli.CommandLine;

public final class ExportOrchestrator {

	private final static String API_CSV_HEADER = "id;title;property;mixins;methods;events;attributes;deeplink\n";
	private final static String DOCS_CSV_HEADER = "id;title;code;description;headings;deeplink\n";
	private final static String DEFAULT_API_EXPORT_FILENAME = "/api-export.csv";
	private final static String DEFAULT_DOC_EXPORT_FILENAME = "/doc-export.csv";

	private static String indexLines(String header, List<String> csvLines) {
		StringBuilder sb = new StringBuilder(header);
		csvLines.forEach(line -> sb.append(String.format("\"%d\";", csvLines.indexOf(line))).append(line));
		return sb.toString();
	}

	public static void runExport(CommandLine cmd) throws IOException {
		String absoluteUrl = cmd.hasOption("u") ? cmd.getOptionValue("u") : "";
		if (cmd.hasOption("a")) {
			String apiDirectory = cmd.getOptionValue("a");
			String outputDir = cmd.hasOption("o") ? cmd.getOptionValue("o") : apiDirectory;

			List<File> files = FileWalker.readMarkdownFiles(new File(apiDirectory));
			List<String> apiLines = files.stream().map(file -> MarkdownParser.parseApi(file, absoluteUrl)).collect(Collectors.toList());
			String csvContent = indexLines(API_CSV_HEADER, apiLines);

			FactFinderExportWriter csvWriter = new FactFinderExportWriter(outputDir + DEFAULT_API_EXPORT_FILENAME);
			csvWriter.write(csvContent).close();
		}
		if (cmd.hasOption("d")) {
			String docDirectory = cmd.getOptionValue("d");
			String outputDir = cmd.hasOption("o") ? cmd.getOptionValue("o") : docDirectory;

			List<File> files = FileWalker.readMarkdownFiles(new File(docDirectory));
			List<String> docLines = files.stream().map(file -> MarkdownParser.parseDocumentation(file, absoluteUrl)).collect(Collectors.toList());
			String csvContent = indexLines(DOCS_CSV_HEADER, docLines);

			FactFinderExportWriter csvWriter = new FactFinderExportWriter(outputDir + DEFAULT_DOC_EXPORT_FILENAME);
			csvWriter.write(csvContent).close();
		}
	}

	private ExportOrchestrator() {
		// util
	}
}
