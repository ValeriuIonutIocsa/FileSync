package com.personal.scripts.gen.fs.settings;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.utils.io.IoUtils;
import com.utils.log.Logger;
import com.utils.xml.dom.XmlDomUtils;

public class FactoryFileSyncSettings {

	private FactoryFileSyncSettings() {
	}

	public static FileSyncSettings newInstance(
			final String settingsFilePathString) {

		FileSyncSettings fileSyncSettings = null;
		try {
			if (!IoUtils.fileExists(settingsFilePathString)) {
				Logger.printError("the settings file does not exist");

			} else {
				final List<FolderPair> folderPairList = new ArrayList<>();

				final Document document = XmlDomUtils.openDocument(settingsFilePathString);
				final Element documentElement = document.getDocumentElement();

				final List<Element> folderPairElementList =
						XmlDomUtils.getElementsByTagName(documentElement, "FolderPair");
				for (final Element folderPairElement : folderPairElementList) {

					final FolderPair folderPair = parseInputFolderPair(folderPairElement);
					if (folderPair != null) {
						folderPairList.add(folderPair);
					}
				}

				if (folderPairList.isEmpty()) {
					Logger.printError("there are no folder pairs defined in the settings file");

				} else {
					fileSyncSettings = new FileSyncSettings(folderPairList);
				}
			}

		} catch (final Exception exc) {
			Logger.printError("failed to parse settings file");
			Logger.printException(exc);
		}
		return fileSyncSettings;
	}

	private static FolderPair parseInputFolderPair(
			final Element folderPairElement) {

		FolderPair folderPair = null;
		final Element srcFolderPathElement =
				XmlDomUtils.getFirstElementByTagName(folderPairElement, "SrcFolderPath");
		if (srcFolderPathElement != null) {

			final String srcFolderPathString = srcFolderPathElement.getAttribute("Value");

			final Element dstFolderPathElement =
					XmlDomUtils.getFirstElementByTagName(folderPairElement, "DstFolderPath");
			if (dstFolderPathElement != null) {

				final String dstFolderPathString = dstFolderPathElement.getAttribute("Value");
				folderPair = new FolderPair(srcFolderPathString, dstFolderPathString);
			}
		}
		return folderPair;
	}
}
