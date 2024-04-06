package com.utils.xml.dom.openers;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.utils.io.ResourceFileUtils;
import com.utils.log.Logger;
import com.utils.string.StrUtils;
import com.utils.xml.dom.documents.ValidatedDocument;

abstract class AbstractDocumentOpener {

	public ValidatedDocument openAndValidateDocumentResourceSchema(
			final DocumentBuilderFactory documentBuilderFactory,
			final String schemaResourceFilePathString) throws Exception {

		final Document document;

		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setNamespaceAware(true);

		if (StringUtils.isNotBlank(schemaResourceFilePathString)) {

			final Schema schema;
			try (InputStream inputStream =
					ResourceFileUtils.resourceFileToInputStream(schemaResourceFilePathString)) {

				final SchemaFactory schemaFactory =
						SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				schema = schemaFactory.newSchema(new StreamSource(inputStream));
			}
			documentBuilderFactory.setSchema(schema);
		}

		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
		documentBuilder.setErrorHandler(xmlErrorHandler);

		document = parse(documentBuilder);

		final boolean validationSuccessful;
		final boolean hasExceptions = xmlErrorHandler.checkHasExceptions();
		if (hasExceptions) {

			final String exceptionsString = xmlErrorHandler.createExceptionsString(null);
			Logger.printError(exceptionsString);
			validationSuccessful = false;

		} else {
			validationSuccessful = true;
		}

		return new ValidatedDocument(document, validationSuccessful);
	}

	public ValidatedDocument openAndValidateDocumentLocalSchema(
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
		final boolean hasExceptions = xmlErrorHandler.checkHasExceptions();
		if (hasExceptions) {

			printValidationErrorMessage(document, xmlErrorHandler);
			validationSuccessful = false;

		} else {
			validationSuccessful = true;
		}

		return new ValidatedDocument(document, validationSuccessful);
	}

	private static void printValidationErrorMessage(
			final Document document,
			final XmlErrorHandler xmlErrorHandler) {

		final Element documentElement = document.getDocumentElement();
		String schemaLocation = null;
		if (documentElement != null) {
			schemaLocation = documentElement.getAttribute("xsi:noNamespaceSchemaLocation");
		}
		if (StringUtils.isBlank(schemaLocation)) {
			Logger.printError("schema file location is not defined in the XML file");
		}
		final String exceptionsString = xmlErrorHandler.createExceptionsString(schemaLocation);
		Logger.printError(exceptionsString);
	}

	public Document openDocument(
			final DocumentBuilderFactory documentBuilderFactory) throws Exception {

		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return parse(documentBuilder);
	}

	abstract Document parse(
			DocumentBuilder documentBuilder) throws Exception;

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
