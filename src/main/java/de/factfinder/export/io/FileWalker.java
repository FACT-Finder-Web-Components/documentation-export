package de.factfinder.export.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides functionality to traverse directories and find markdown files easily
 */
public final class FileWalker {

	private FileWalker() {
		//util
	}

	public static List<File> readMarkdownFilesRecursively(String baseDir) {
		List<File> markdownFiles = null;
		try {
			markdownFiles = Files.walk(Paths.get(baseDir))
								 .filter(Files::isRegularFile)
								 .map(Path::toFile)
								 .filter(File::canRead)
								 .filter(file -> file.getName().endsWith("md"))
								 .collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return markdownFiles;
	}

	public static List<File> readOnlyApiFiles(String baseDir) {
		return readMarkdownFilesRecursively(baseDir).stream().filter(f -> f.getName().contains("api")).collect(Collectors.toList());
	}

	public static List<File> readOnlyDocumentationFiles(String baseDir) {
		return readMarkdownFilesRecursively(baseDir).stream().filter(f -> !f.getName().contains("api")).collect(Collectors.toList());
	}
}
