package com.utils.xml.dom.openers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

class XmlErrorHandler implements ErrorHandler {

	private List<SAXParseException> exceptionList;

	XmlErrorHandler() {

		exceptionList = new ArrayList<>();
	}

	@Override
	public void error(
			final SAXParseException exception) {

		exceptionList.add(exception);
	}

	@Override
	public void fatalError(
			final SAXParseException exception) {

		exceptionList = new ArrayList<>();
	}

	@Override
	public void warning(
			final SAXParseException exception) {

		exceptionList = new ArrayList<>();
	}

	String createExceptionsString(
			final String schemaLocation) {

		final StringBuilder sbExceptionsString = new StringBuilder();
		if (StringUtils.isNotBlank(schemaLocation)) {
			sbExceptionsString.append("failed to validate XML file against the schema file \"")
					.append(schemaLocation).append("\"");
		} else {
			sbExceptionsString.append("failed to validate XML file against the schema file");
		}
		for (final SAXParseException exception : exceptionList) {

			sbExceptionsString
					.append(System.lineSeparator())
					.append("LINE ").append(exception.getLineNumber())
					.append(", COLUMN ").append(exception.getColumnNumber())
					.append(" ||| ").append(exception.getLocalizedMessage());
		}
		return sbExceptionsString.toString();
	}

	boolean checkHasExceptions() {

		return !exceptionList.isEmpty();
	}
}
