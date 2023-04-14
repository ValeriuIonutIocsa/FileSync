package com.utils.xml.dom.chains;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.utils.io.PathUtils;
import com.utils.io.StreamUtils;
import com.utils.log.Logger;
import com.utils.xml.dom.XmlDomUtils;

class XmlLongestChainParserTest {

	@Test
	void testWork() throws Exception {

		final String xmlFilePathString;
		final int input = Integer.parseInt("12");
		if (input == 1) {
			xmlFilePathString = "D:\\tmp\\ProjectAnalyzer\\Outputs\\BMG12_0U0_I00-symbol_types_local_5.0.24.xml";

		} else if (input == 11) {
			xmlFilePathString = "D:\\tmp\\ProjectAnalyzer\\Outputs\\FOFB4_0U0_120-symbol_types_local_5.0.24.xml";
		} else if (input == 12) {
			xmlFilePathString = "D:\\tmp\\ProjectAnalyzer\\TestData\\SymbolTypes\\" +
					"FOFB4_0U0_120_Os_CounterAddRelJob_symbol_types.xml";
		} else {
			throw new RuntimeException();
		}

		final Document document = XmlDomUtils.openDocument(xmlFilePathString);
		final Element documentElement = document.getDocumentElement();
		final List<ElementWithDepth> elementWithDepthList = new ArrayList<>();
		fillElementWithDepthListRec(documentElement, 1, elementWithDepthList);

		elementWithDepthList.sort(Comparator.comparing(
				ElementWithDepth::getDepth, Comparator.reverseOrder()));

		final int displayedCount = Math.min(elementWithDepthList.size(), 20);

		for (int i = 0; i < displayedCount; i++) {

			final ElementWithDepth elementWithDepth = elementWithDepthList.get(i);
			elementWithDepth.computeElementChain();
		}

		final String outputPathString =
				PathUtils.computePathWoExt(xmlFilePathString) + "_longest_chains.txt";
		Logger.printProgress("generating output file:");
		Logger.printLine(outputPathString);

		try (PrintStream printStream = StreamUtils.openPrintStream(outputPathString)) {

			for (int i = 0; i < displayedCount; i++) {

				final ElementWithDepth elementWithDepth = elementWithDepthList.get(i);
				printStream.print("chain depth: ");
				printStream.print(elementWithDepth.getDepth());
				printStream.println();

				final List<String> chainedElementList = elementWithDepth.getChainedElementList();
				for (final String chainedElement : chainedElementList) {

					printStream.print(chainedElement);
					printStream.println();
				}

				printStream.println();
				printStream.println();
				printStream.println();
			}
		}
	}

	private static void fillElementWithDepthListRec(
			final Element element,
			final int depth,
			final List<ElementWithDepth> elementWithDepthList) {

		final List<Element> childElementList = XmlDomUtils.getChildElements(element);
		if (childElementList.isEmpty()) {
			elementWithDepthList.add(new ElementWithDepth(element, depth));

		} else {
			final int childDepth = depth + 1;
			for (final Element childElement : childElementList) {
				fillElementWithDepthListRec(childElement, childDepth, elementWithDepthList);
			}
		}
	}
}
