package de.factfinder.export.io.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions extends Options {

	public CliOptions() {
		super();

		Option apiDirectory = Option.builder("a")
				.required(true)
				.desc("Directory containing all \"API\" files")
				.longOpt("api-directory")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Option docDirectory = Option.builder("d")
				.required(true)
				.desc("Directory containing all \"Documentation\" files")
				.longOpt("doc-directory")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Option absoluteURL = Option.builder("u")
				.required(false)
				.desc("Optional. Prepends the passed argument to the \"deeplink\". Default uses relative path.")
				.longOpt("url")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		Option outputDir = Option.builder("o")
				.required(false)
				.desc("Optional. Exports will be put into this directory")
				.longOpt("output-dir")
				.hasArg(true)
				.numberOfArgs(1)
				.build();

		addOption(apiDirectory);
		addOption(docDirectory);
		addOption(absoluteURL);
		addOption(outputDir);
	}
}
