package com.utils.xml.dom.documents;

import org.w3c.dom.Document;

import com.utils.string.StrUtils;

public class ValidatedDocument {

	private final Document document;
	private final boolean validationSuccessful;

	public ValidatedDocument(
			final Document document,
			final boolean validationSuccessful) {

		this.document = document;
		this.validationSuccessful = validationSuccessful;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public Document getDocument() {
		return document;
	}

	public boolean isValidationSuccessful() {
		return validationSuccessful;
	}
}
