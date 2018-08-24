package de.factfinder.export;

import de.factfinder.export.io.ExportOrchestrator;
import de.factfinder.export.io.cli.CliOptions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class ExportMain {

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new CliOptions();
		DefaultParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		ExportOrchestrator.runExport(cmd);
	}
}
