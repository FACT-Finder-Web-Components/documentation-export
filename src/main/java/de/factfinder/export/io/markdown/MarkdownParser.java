package de.factfinder.export.io.markdown;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MarkdownParser {

	private final static String RELATIVE_PATH = "/api/";
	private final static String DOCS_TAB = "#tab=docs";
	private final static String API_TAB = "#tab=api";

	private MarkdownParser() {
		//Util
	}

	private static String readAllCodeBlocks(final File markdownFile) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code.toString();
	}

	private static String readAllHeadings(final File markdownFile) {
		StringBuilder headings = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) {
					headings.append(line.trim().concat(" "));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headings.toString();
	}

	private static String readRegularText(final File markdownFile) {
		StringBuilder regularText = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			boolean isInCodeBlock = false;
			while ((line = br.readLine()) != null) {
				if (line.contains("```")) {
					isInCodeBlock = !isInCodeBlock;
					continue;
				}
				if (!line.startsWith("#") && !isInCodeBlock && line.length() > 1) {
					regularText.append(line.trim().concat(" "));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return regularText.toString();
	}

	private static String readOnlySubHeadingBlocks(final File markdownFile) {
		StringBuilder subHeadingBlocks = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!(line.startsWith("## `") || line.contains("___") || line.isEmpty() || line.startsWith("| Name") || line.startsWith("| ---"))) {
					subHeadingBlocks.append(line).append("\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return subHeadingBlocks.toString();
	}

	static Map<String, String> mapTableContent(String blocks) {
		String[] split = blocks.split("###");
		Map<String, String> blockMap = new HashMap<>();
		for (String block : split) {
			if (block != null && !block.isEmpty()) {
				StringBuilder tableContent = new StringBuilder();
				Scanner scanner = new Scanner(block);
				String heading = scanner.nextLine().trim().toLowerCase();
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					int firstIndex = line.indexOf("**");
					int secondIndex = line.indexOf("**", firstIndex + 2);
					if (firstIndex >= 0 && secondIndex > firstIndex) {
						String name = line.substring(firstIndex, secondIndex + 2);
						tableContent.append(name.trim().replaceAll("\\*", "")).append(" ");
					}
				}
				scanner.close();
				if (!blockMap.isEmpty() && blockMap.containsKey(heading)) {
					tableContent.append(blockMap.get(heading));
				}
				blockMap.put(heading, tableContent.toString());
			}
		}
		return blockMap;
	}

	static String generateMultiAttributeField(Map<String, String> tableContent) {
		StringBuilder multiAttributeField = new StringBuilder("|");
		for (String key : tableContent.keySet()) {
			Arrays.stream(tableContent.get(key).split(" ")).forEach(s -> multiAttributeField.append(key).append("=").append(s).append("|"));
		}
		return multiAttributeField.toString();
	}

	public static String parseDocumentation(final File markdownFile, String absoluteUrl) {
		String title = sanitize(markdownFile.getName().replace(".md", ""));
		return ("\"" + title + "\";\""
				+ sanitize(readAllCodeBlocks(markdownFile)) + "\";\""
				+ sanitize(readRegularText(markdownFile)) + "\";\""
				+ sanitize(readAllHeadings(markdownFile)) + "\";\""
				+ absoluteUrl + RELATIVE_PATH + title).replaceAll("#", "")
				+ DOCS_TAB + "\"\n";
	}

	public static String parseApi(final File markdownFile, String absoluteUrl) {
		Map<String, String> tableContent = mapTableContent(readOnlySubHeadingBlocks(markdownFile));
		String multiAttributeField = sanitize(generateMultiAttributeField(tableContent));
		String title = sanitize(markdownFile.getName().replace(".api.md", ""));

		return ("\"" + title + "\";\""
				+ sanitize(tableContent.get("properties")) + "\";\""
				+ sanitize(tableContent.get("mixins")) + "\";\""
				+ sanitize(tableContent.get("methods")) + "\";\""
				+ sanitize(tableContent.get("events")) + "\";\""
				+ multiAttributeField + "\";\""
				+ absoluteUrl + RELATIVE_PATH + title).replaceAll("#", "")
				+ API_TAB + "\"\n";
	}

	private static String sanitize(String string) {
		return string != null && !string.isEmpty() ? string.replace("\"", "").replace(";", "") : "";
	}
}
