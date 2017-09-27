package de.factfinder.export.io.markdown;

import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class MarkdownParserTest {
	@Test
	public void mapTableContent() throws Exception {
		String testBlocks = " Properties\n" + "| **step-size**&nbsp;(Number) | Determines in which steps the slider should move when dragged. |\n"
							+ "| **selected-min-value**&nbsp;(Number)| The currently selected minimum value. Cannot be less than absolute-min-value. |\n"
							+ "| **selected-max-value**&nbsp;(Number)| The currently selected maximum value. Cannot be less than absolute-max-value. |\n"
							+ "| **submit-on-release**&nbsp;(Number) **Options**&nbsp;true,&nbsp;false (default: true) | If this is set to true a filter request is made immediately upon releasing the slider btn. |\n"
							+ "| **absolute-min-value**&nbsp;(Number) | Indicates the minimum lower end e.g. if set to 50, dragging the slider btn to the most left will result in a value of 50. |\n"
							+ "| **absolute-max-value**&nbsp;(Number) | Indicates the maximum upper end e.g. if set to 500, dragging the slider btn to the most right will result in a value of 500. |\n"
							+ "\n" + "\n" + "\n" + " Methods\n" + "| Name | Description |\n" + "| ---- | ----------- |\n"
							+ "| **submit** | Send the filter request with the current values. |\n" + "\n";

		Map<String, String> stringStringMap = MarkdownParser.mapTableContent(testBlocks);
		assertThat(stringStringMap.containsKey("properties"), is(true));
	}

}