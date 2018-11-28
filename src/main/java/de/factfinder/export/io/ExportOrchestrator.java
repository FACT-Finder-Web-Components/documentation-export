package de.factfinder.export.io;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.factfinder.export.io.csv.FactFinderExportWriter;
import de.factfinder.export.io.markdown.MarkdownParser;
import org.apache.commons.cli.CommandLine;

public final class ExportOrchestrator {

	private final static String API_CSV_HEADER = "id;title;property;mixins;methods;events;attributes;deeplink\n";
	private final static String DOCS_CSV_HEADER = "id;title;code;description;headings;deeplink\n";
	private final static String DEFAULT_API_EXPORT_FILENAME = "/api-export%s.csv";
	private final static String DEFAULT_DOC_EXPORT_FILENAME = "/doc-export%s.csv";

	private static String indexLines(String header, List<String> csvLines) {
		StringBuilder sb = new StringBuilder(header);
		IntConsumer writeLine = index -> sb.append(String.format("\"%d\";", index)).append(csvLines.get(index));
		IntStream.range(0, csvLines.size()).forEach(writeLine);
		return sb.toString();
	}

	public static void runExport(CommandLine cmd) throws IOException {
		String absoluteUrl = cmd.getOptionValue("u", "");
		String version = cmd.getOptionValue("v", "");
		if (cmd.hasOption("a")) {
			String apiDirectory = cmd.getOptionValue("a");
			String outputDir = cmd.getOptionValue("o", apiDirectory);

			List<File> files = FileWalker.readMarkdownFiles(new File(apiDirectory));
			List<String> apiLines = files.stream().map(file -> MarkdownParser.parseApi(file, absoluteUrl)).collect(Collectors.toList());
			String csvContent = indexLines(API_CSV_HEADER, apiLines);

			FactFinderExportWriter csvWriter = new FactFinderExportWriter(outputDir + String.format(DEFAULT_API_EXPORT_FILENAME, version));
			csvWriter.write(csvContent).close();
		}
		if (cmd.hasOption("d")) {
			String docDirectory = cmd.getOptionValue("d");
			String outputDir = cmd.getOptionValue("o", docDirectory);

			List<File> files = FileWalker.readMarkdownFiles(new File(docDirectory));
			List<String> docLines = files.stream().map(file -> MarkdownParser.parseDocumentation(file, absoluteUrl)).collect(Collectors.toList());
			String csvContent = indexLines(DOCS_CSV_HEADER, docLines);

			FactFinderExportWriter csvWriter = new FactFinderExportWriter(outputDir + String.format(DEFAULT_DOC_EXPORT_FILENAME, version));
			csvWriter.write(csvContent).close();
		}
	}

	private ExportOrchestrator() {
		// util
	}
}
