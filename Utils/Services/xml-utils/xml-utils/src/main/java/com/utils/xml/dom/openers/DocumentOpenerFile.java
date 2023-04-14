package com.utils.xml.dom.openers;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

public class DocumentOpenerFile extends AbstractDocumentOpener {

	private final File file;

	public DocumentOpenerFile(
			final File file) {

		this.file = file;
	}

	@Override
	Document parse(
			final DocumentBuilder documentBuilder) throws Exception {
		return documentBuilder.parse(file);
	}
}
