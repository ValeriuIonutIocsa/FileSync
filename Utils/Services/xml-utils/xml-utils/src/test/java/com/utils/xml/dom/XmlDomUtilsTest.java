package com.utils.xml.dom;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.utils.io.PathUtils;
import com.utils.log.Logger;

class XmlDomUtilsTest {

	@Test
	void testRemoveElementsByTagName() throws Exception {

		final Document document = createTestDocument();

		Logger.printLine("initial document:");
		Logger.printLine(XmlDomUtils.saveXmlFile(document, false, 4));

		final Element documentElement = document.getDocumentElement();
		XmlDomUtils.removeElementsByTagName(documentElement, "test3");

		Logger.printNewLine();
		Logger.printLine("edited document:");
		Logger.printLine(XmlDomUtils.saveXmlFile(document, false, 4));
	}

	@Test
	void testSaveXmlFile() throws Exception {

		final Document document = createTestDocument();

		final String str = XmlDomUtils.saveXmlFile(document, false, 4);
		Logger.printLine(str);

		final String xmlFilePathString =
				PathUtils.computePath(PathUtils.createRootPath(), "tmp", "xml_dom_utils_test.xml");
		Logger.printProgress("saving XMl file:");
		Logger.printLine(xmlFilePathString);
		XmlDomUtils.saveXmlFile(document, false, 4, xmlFilePathString);
	}

	private static Document createTestDocument() throws Exception {

		final Document document = XmlDomUtils.createNewDocument();

		final Element test1Element = document.createElement("test1");

		final Element test2Element = document.createElement("test2");
		test2Element.setAttribute("attribute", "a>b<c");

		final Element test3Element = document.createElement("test3");
		test3Element.setAttribute("before_attribute", "before");
		test3Element.setAttribute("after_attribute", "after");

		test2Element.appendChild(test3Element);

		final Element test4Element = document.createElement("test4");
		test2Element.appendChild(test4Element);

		test1Element.appendChild(test2Element);

		document.appendChild(test1Element);

		return document;
	}

	@Test
	void testParseAndSaveXmlFile() throws Exception {

		final String xmlFilePathString =
				PathUtils.computePath(PathUtils.createRootPath(), "tmp", "xml_dom_utils_test.xml");
		Logger.printProgress("parsing and saving XML file:");
		Logger.printLine(xmlFilePathString);
		final Document document = XmlDomUtils.openDocument(xmlFilePathString);

		XmlDomUtils.saveXmlFile(document, false, 4, xmlFilePathString);
	}
}
