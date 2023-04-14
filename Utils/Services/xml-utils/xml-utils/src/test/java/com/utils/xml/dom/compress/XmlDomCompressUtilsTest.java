package com.utils.xml.dom.compress;

import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import com.utils.io.ResourceFileUtils;
import com.utils.log.Logger;
import com.utils.xml.dom.XmlDomUtils;

class XmlDomCompressUtilsTest {

	@Test
	void testCompressXml() throws Exception {

		final String resourceFilePathString = "com/utils/xml/dom/compress/test.xml";
		try (InputStream inputStream = ResourceFileUtils.resourceFileToInputStream(resourceFilePathString)) {

			final Document document = XmlDomUtils.openDocument(inputStream);
			final String xmlFileContents = XmlDomUtils.saveXmlFile(document, false, 4);
			Logger.printLine("XML file contents:");
			Logger.printLine(xmlFileContents);

			Logger.printNewLine();
			Logger.printNewLine();
			Logger.printLine("Compressed XML file contents:");
			XmlDomCompressUtils.compressXml(document);
			final String compressedXmlFileContents = XmlDomUtils.saveXmlFile(document, false, -1);
			Logger.printLine(compressedXmlFileContents);

			Logger.printNewLine();
			Logger.printNewLine();
			Logger.printLine("Decompressed XML file contents:");
			XmlDomCompressUtils.decompressXml(document);
			final String decompressedXmlFileContents = XmlDomUtils.saveXmlFile(document, false, 4);
			Logger.printLine(decompressedXmlFileContents);

			Assertions.assertEquals(xmlFileContents, decompressedXmlFileContents);
		}
	}
}
