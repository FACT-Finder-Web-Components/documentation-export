package de.factfinder.export;

import java.io.File;
import java.io.IOException;

import de.factfinder.export.markdown.MarkdownParser;

public class ExportMain {

	public static void main(String[] args) throws IOException {

		File file = new File("/home/sdamrath/code/documentation/markdown/en/ff-compare.md");
		String codeBlocks = MarkdownParser.readAllCodeBlocks(file);
		String headings = MarkdownParser.readAllHeadings(file);
		String regularText = MarkdownParser.readRegularText(file);
		System.out.println(codeBlocks);
		System.out.println(headings);
		System.out.println(regularText);
	}
}
