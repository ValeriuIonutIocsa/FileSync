package com.utils.xml.dom.openers;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.utils.io.PathUtils;
import com.utils.io.ReaderUtils;

public class DocumentOpenerInputStream extends AbstractDocumentOpener {

	private final InputStream inputStream;
	private final String schemaFolderPathString;

	public DocumentOpenerInputStream(
			final InputStream inputStream,
			final String schemaFolderPathString) {

		this.inputStream = inputStream;
		this.schemaFolderPathString = schemaFolderPathString;
	}

	@Override
	Document parse(
			final DocumentBuilder documentBuilder) throws Exception {

		documentBuilder.setEntityResolver((
				publicId,
				systemId) -> {

			final String schemaFileName = PathUtils.computeFileName(systemId);
			final String schemaFilePathString = PathUtils.computePath(schemaFolderPathString, schemaFileName);
			return new InputSource(ReaderUtils.openBufferedReader(schemaFilePathString));
		});

		return documentBuilder.parse(inputStream);
	}
}
