package com.utils.xml.dom.openers;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import com.utils.log.Logger;
import com.utils.string.StrUtils;
import com.utils.xml.dom.documents.ValidatedDocument;

abstract class AbstractDocumentOpener {

	public ValidatedDocument openAndValidateDocument(
			final DocumentBuilderFactory documentBuilderFactory) throws Exception {

		final Document document;

		documentBuilderFactory.setValidating(true);
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				XMLConstants.W3C_XML_SCHEMA_NS_URI);

		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
		documentBuilder.setErrorHandler(xmlErrorHandler);

		document = parse(documentBuilder);

		final boolean validationSuccessful;
		final SAXParseException exception = xmlErrorHandler.getException();
		if (exception != null) {
			printValidationErrorMessage(document, exception);
			validationSuccessful = false;
		} else {
			validationSuccessful = true;
		}

		return new ValidatedDocument(document, validationSuccessful);
	}

	public Document openDocument(
			final DocumentBuilderFactory documentBuilderFactory) throws Exception {

		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return parse(documentBuilder);
	}

	abstract Document parse(
			DocumentBuilder documentBuilder) throws Exception;

	private static void printValidationErrorMessage(
			final Document document,
			final SAXParseException exception) {

		final Element documentElement = document.getDocumentElement();
		final String schemaLocation;
		if (documentElement != null) {
			schemaLocation = documentElement.getAttribute("xsi:noNamespaceSchemaLocation");
		} else {
			schemaLocation = null;
		}
		if (StringUtils.isBlank(schemaLocation)) {
			Logger.printError("schema file location is not defined in the XML file");

		} else {
			final String exceptionMessage = "LINE " + exception.getLineNumber() +
					", COLUMN " + exception.getColumnNumber() + " ||| " + exception.getLocalizedMessage();

			Logger.printError("failed to validate XML file against the schema file \"" + schemaLocation + "\"" +
					System.lineSeparator() + exceptionMessage);
		}
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
