package de.factfinder.export;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ExportMain {

	public static void main(String[] args) {

		Option baseUrl = Option.builder("u")
				.required(true)
				.desc("The base URL - e.g. https://web-components.fact-finder.de/\n" +
						"This is the basis for all deep-links")
				.longOpt("base-url")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Option language = Option.builder("l")
				.required(false)
				.desc("The language of the documentation files - Used to name the export files\n" +
						"Defaults to 'en'")
				.longOpt("language")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Option outputDir = Option.builder("o")
				.required(false)
				.desc("The directory the resulting export files will be put in.")
				.longOpt("output-directory")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Option inputDir = Option.builder("i")
				.required(true)
				.desc("The directory that contains the markdown files to use as input")
				.longOpt("input-directory")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Options options = new Options();
		options.addOption(baseUrl);
		options.addOption(language);
		options.addOption(outputDir);
		options.addOption(inputDir);
	}
}
