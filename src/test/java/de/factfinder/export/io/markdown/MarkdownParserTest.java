package de.factfinder.export.io.markdown;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MarkdownParserTest {
	@Test
	public void test_mapTableContent() throws Exception {
		String testBlocks = " Properties\n" + "| **step-size**&nbsp;(Number) | Determines in which steps the slider should move when dragged. |\n"
							+ "| **selected-min-value**&nbsp;(Number)| The currently selected minimum value. Cannot be less than absolute-min-value. |\n"
							+ "| **selected-max-value**&nbsp;(Number)| The currently selected maximum value. Cannot be less than absolute-max-value. |\n"
							+ "| **submit-on-release**&nbsp;(Number) **Options**&nbsp;true,&nbsp;false (default: true) | If this is set to true a filter request is made immediately upon releasing the slider btn. |\n"
							+ "| **absolute-min-value**&nbsp;(Number) | Indicates the minimum lower end e.g. if set to 50, dragging the slider btn to the most left will result in a value of 50. |\n"
							+ "| **absolute-max-value**&nbsp;(Number) | Indicates the maximum upper end e.g. if set to 500, dragging the slider btn to the most right will result in a value of 500. |\n";

		Map<String, String> stringStringMap = MarkdownParser.mapTableContent(testBlocks);
		assertThat(stringStringMap.containsKey("properties"), is(true));
		assertThat(stringStringMap.get("properties"),
				   is("step-size selected-min-value selected-max-value submit-on-release absolute-min-value absolute-max-value "));
	}

	@Test
	public void test_generateMultiAttributeField(){
		Map<String, String> testMap = new HashMap<>();
		testMap.put("properties", "step-size selected-min-value selected-max-value submit-on-release absolute-min-value absolute-max-value ");
		String multiAtrributeField = MarkdownParser.generateMultiAttributeField(testMap);
		assertThat(multiAtrributeField, is("|properties=step-size|properties=selected-min-value|properties=selected-max-value|properties=submit-on-release|properties=absolute-min-value|properties=absolute-max-value|"));
	}

}