package de.factfinder.export.io.csv;

import java.io.IOException;

public class DocumentationCsvWriter extends FactFinderExportWriter {

	private final String COLUMNS = "code;headings;regular;deeplink\n";

	public DocumentationCsvWriter(final String outputDir) throws IOException {
		super(outputDir);
	}

	@Override
	public FactFinderExportWriter write(final String rows) {
		String csvContent = COLUMNS.concat(rows);
		return super.write(csvContent);
	}
}