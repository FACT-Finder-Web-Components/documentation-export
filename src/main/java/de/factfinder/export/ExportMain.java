package de.factfinder.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import de.factfinder.export.io.FileWalker;

public class ExportMain {

	public static void main(String[] args) throws FileNotFoundException {
		List<File> files = FileWalker.readMarkdownFilesRecursively(args[0]);
		File file = files.get(0);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		br.lines().forEach(System.out::println);
	}
}
