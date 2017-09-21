package de.factfinder.export.csv;

import java.io.IOException;

public class DocuCsvWriter extends FactFinderExportWriter {

	private final String COLUMNS = "col1;col2;col3\n";

	public DocuCsvWriter(final String outputDir) throws IOException {
		super(outputDir);
	}

	@Override
	public FactFinderExportWriter write(final String rows) {
		String csvContent = COLUMNS.concat(rows);
		return super.write(csvContent);
	}
}