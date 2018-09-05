package de.factfinder.export.io.markdown;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MarkdownParserTest {

	private File docTestFile;
	private File apiTestFile;

	@Before
	public void beforeEach() {
		URL url = this.getClass().getResource("/test.md");
		docTestFile = new File(url.getFile());
		url = this.getClass().getResource("/ff-suggest.api.md");
		apiTestFile = new File(url.getFile());
	}

	@Test
	public void test_mapTableContent() {
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
	public void test_generateMultiAttributeField() {
		Map<String, String> testMap = new HashMap<>();
		testMap.put("properties", "step-size selected-min-value selected-max-value submit-on-release absolute-min-value absolute-max-value ");
		String multiAttributeField = MarkdownParser.generateMultiAttributeField(testMap);
		assertThat(multiAttributeField, is("|properties=step-size|properties=selected-min-value|properties=selected-max-value|properties=submit-on-release|properties=absolute-min-value|properties=absolute-max-value|"));
	}

	@Test
	public void test_parseOnlyText() throws IOException {
		assertNotNull(docTestFile);
		assertTrue(docTestFile.exists());

		final String expected = "FieldRolesThis section describes an essential part of the integrationof FACT-Finder using Web Components. FACT-Finder and Web Componentsdependon a proper definition of these roles. A missing definition can result inunexpected behaviors that might look like bugs.Certain fields/columns in FACT-Finders product database can have specialmeanings. E.g. apricefield. Many of FACT-Finders features, such asTracking require knowledge of these special meanings. Since every customermight use customfield namesthere needs to be a mapping between themeaningof a field and itsfield name.We call these meaningsfieldRoles. During the set-up process of a newchannel in the FACT-Finder-UI you assign a field to each one of thefieldRoles.These are thefieldRoles:brand– name of manufacturer.campaignProductNumber– product number, that are used to reference theproduct in the campaign manager.deeplink– URL of the product page.description– product description.displayProductNumber– The displayed product number.ean– EANimageUrl– product image-URL.price– price field.productName– product name.trackingProductNumber– product number used in trackingmasterArticleNumber– product number of the main product if productvariants are usedIMPORTANT:During set-up of a channel in the FACT-Finder-UI you haveto assign each of thefieldRoleslisted above to one field/column inyour product database.This means in order for FACT-Finder and Web Components to process yourdata properly you also need to provide a mapping offieldRolestofield namesin your front-end before you request data via Web Componentsfrom any API except the search API.More specifically, to use the following elements it is mandatory to definethefieldRolesproperty on client-side:<ff-recommendation><ff-similar-products><ff-communication currency-code ...><ff-suggest><ff-checkout-tracking><ff-tag-cloud><ff-campaign-pushed-products>(Tracking) loaded via<ff-campaign-landing-page>,<ff-campaign-products>or<ff-campaign-shopping-cart>In other words:You might have a field calledprixthat contains theproducts price in your product database. During set-up you assign therolepriceto your fieldprixin the FACT-Finder-UI. In order to use theCurrency Attributeson your product detail page, you have to mappricetoprixin thefactfinder.communication.fieldRolesproperty in yourfront-end.NOTICE:ThefieldRolesare configured in the FACT-Finder-UI duringthe set-up process. It is essential the roles you define in the browsermatch the ones defined in FACT-Finder.How to definefieldRoleson the Web Components sideYou need to do this when theffReadyevent is dispatched soff-communicationknows the correct mapping before your API-Requests aresent.fieldRolesis a property offactfinder.communication.Here is anexampleof how your product database might look afterthe set-up.idcampaignIdmasterIdRRPimagename---12114.45http://Nerf Gun22219.99http://Ergonomic Keyboard32312.5http://Rubber DuckThis is how you set thefieldRoles:IMPORTANTBoth EventListeners have to be declared before our scripts are loaded or you could miss the eventand your code is never executed!<script>\n" +
				"    document.addEventListener(\"ffReady\", function () {\n" +
				"                    factfinder.communication.fieldRoles = {\n" +
				"                        campaignProductNumber: \"campaignId\",\n" +
				"                        imageUrl: \"image\",\n" +
				"                        masterArticleNumber: \"masterId\",\n" +
				"                        price: \"RRP\",\n" +
				"                        productName: \"name\"\n" +
				"                    };\n" +
				"                });\n" +
				"</script>\n" +
				"<link rel=\"import\" href=\"pathToHtmlImport/elements.build.with_dependencies.html\">NOTICE:We omitted some roles in this example butyou always have to set allfieldRolesin the back- as well asfront-end.How to look up yourfieldRolesAs we mentioned above, the search API can be queried without providingthefieldRolesproperty. This is because a response from the search APIcontains thefieldRolesproperty as it is defined in FACT-Finder.You can simply query the search API and look at the response JSON to findthefieldRolesproperty. Then you can use that object in your front-endassignment fo thefactfinder.communication.fieldRolesproperty.Example:You can check-out therecord-list demoand adjust theff-communication-parameters to match your set-up anduse the search-box in the demo to submit any search query. In yourdev-toolsnetwork tab you can look at the JSON-response undersearchResult.fieldRolesto find thefieldRolesyou will need.Integration Checklist:AllfieldRolesare defined properly. The value in your definition hasto match the fields name in your product database. This is case sensitive.You define thefieldRoleswhen theffReadyevent is fired.You define thefieldRolesbefore you do a request. Thisshouldalways be the case if you followed #2.";

		String text = MarkdownToText.textOnly(docTestFile);
		assertEquals(text, expected);
	}

	@Test
	public void test_parseApiFile() {
		assertNotNull(apiTestFile);
		assertTrue(apiTestFile.exists());
		String api = MarkdownParser.parseApi(apiTestFile, "");
		System.out.println(api);
		// TODO test
	}

}