package com.utils.xml.dom.openers;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

class XmlErrorHandler implements ErrorHandler {

	private SAXParseException exception;

	@Override
	public void error(
			final SAXParseException ex) {
		exception = ex;
	}

	@Override
	public void fatalError(
			final SAXParseException ex) {
		exception = ex;
	}

	@Override
	public void warning(
			final SAXParseException ex) {
		exception = ex;
	}

	SAXParseException getException() {
		return exception;
	}
}
