package de.factfinder.export.io.markdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MarkdownParser {

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
			br.close();
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
			br.close();
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
				if (!line.startsWith("#") && !isInCodeBlock && !(line.length() <= 1)) regularText.append(line.trim().concat(" "));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return regularText.toString();
	}

	private static String readApiHeadings(final File markdownFile) {
		StringBuilder apiHeadings = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("## `")) {
					apiHeadings.append(line.replace("## ", "").replace("`", "")).append(" ");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return apiHeadings.toString();
	}

	private static String readOnlySubHeadingBlocks(final File markdownFile) {
		StringBuilder subHeadingBlocks = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(markdownFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!(line.startsWith("## `") || line.equals("---") || line.isEmpty() || line.startsWith("| Name") || line.startsWith("| ---"))) {
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
			StringBuilder tableContent = new StringBuilder();
			Scanner scanner = new Scanner(block);
			String heading = scanner.nextLine().trim().toLowerCase();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("|")) {
					String name = Arrays.stream(line.split("\\|")).findFirst().orElse("");
					tableContent.append(name.trim()).append(" ");
				}
			}
			scanner.close();
			if (!blockMap.isEmpty() && blockMap.containsKey(heading)) {
				tableContent.append(blockMap.get("heading"));
			}
			blockMap.put(heading, tableContent.toString());
		}
		return blockMap;
	}

	public static String parseDocumentation(final File markdownFile, final String baseUrl) {
		return readAllCodeBlocks(markdownFile) + ";" + readAllHeadings(markdownFile) + ";" + readRegularText(markdownFile) + ";" + baseUrl + "/"
							+ markdownFile.getName().replace(".md", "\n");
	}

	public static String parseApi(final File markdownFile, final String baseUrl) {
		String subHeadingBlocks = readOnlySubHeadingBlocks(markdownFile);
		Map<String, String> tableContent = mapTableContent(subHeadingBlocks);
		String headings = readApiHeadings(markdownFile);


		return null;
	}

}
