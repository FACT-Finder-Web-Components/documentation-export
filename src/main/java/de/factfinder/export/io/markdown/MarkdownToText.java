package de.factfinder.export.io.markdown;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MarkdownToText {

	private final static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
			Extensions.ALL
	);

	private final static Parser PARSER = Parser.builder(OPTIONS).build();

	public static String textOnly(final File markdownFile) throws IOException {

		Node document = PARSER.parseReader(new FileReader(markdownFile));

		TextCollectingVisitor textCollectingVisitor = new TextCollectingVisitor();
		BasedSequence[] basedSequences = textCollectingVisitor.collectAndGetSegments(document);

		return Arrays.stream(basedSequences).filter(seq -> !seq.isBlank()).map(BasedSequence::trim).collect(Collectors.joining());
	}

}
