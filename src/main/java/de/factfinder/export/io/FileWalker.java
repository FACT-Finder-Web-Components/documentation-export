package de.factfinder.export.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provides functionality to traverse directories and find markdown files easily
 */
final class FileWalker {

	private FileWalker() {
		//util
	}

	private static List<File> readMarkdownFilesRecursively(final File baseDir) {
		List<File> markdownFiles = null;
		try {
			markdownFiles = Files.walk(Paths.get(baseDir.getAbsolutePath()))
					.filter(Files::isRegularFile)
					.map(Path::toFile)
					.filter(File::canRead)
					.filter(file -> file.getName().endsWith("md"))
					.filter(file -> !file.getParent().contains("documentation"))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return markdownFiles;
	}

	static List<File> readOnlyApiFiles(final File baseDir) {
		return readMarkdownFilesRecursively(baseDir).stream().filter(f -> f.getName().contains("api")).collect(Collectors.toList());
	}

	static List<File> readOnlyDocumentationFiles(final File baseDir) {
		return readMarkdownFilesRecursively(baseDir).stream().filter(f -> !f.getName().contains("api")).collect(Collectors.toList());
	}

	static List<File> readMarkdownFiles(final File directory) {
		return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
				.filter(File::canRead)
				.filter(File::isFile)
				.filter(file -> file.getName().endsWith(".md"))
				.collect(Collectors.toList());

	}
}
