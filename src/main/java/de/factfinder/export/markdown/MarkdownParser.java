package de.factfinder.export.markdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MarkdownParser {

	private MarkdownParser() {
		//Util
	}

	public static String readAllCodeBlocks(File markdownFile) {
		StringBuilder code = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("```")) {
					while (!(line = br.readLine()).contains("```")) {
						code.append(line.trim().concat(" "));
					}
					if (!line.endsWith("```")) {
						code.append(line.replaceAll("`", ""));
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code.toString();
	}

	public static String readAllHeadings(File markdownFile) {
		StringBuilder headings = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) {
					headings.append(line.trim().concat(" "));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headings.toString();
	}

	public static String readRegularText(File markdownFile) {
		StringBuilder regularText = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			boolean isInCodeBlock = false;
			while ((line = br.readLine()) != null) {
				if (line.contains("```")) {
					isInCodeBlock = !isInCodeBlock;
					continue;
				}
				if (!line.startsWith("#") && !isInCodeBlock && !(line.length() <= 1)) regularText.append(line.trim().concat(" \n"));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return regularText.toString();
	}

}
