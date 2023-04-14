package com.utils.xml.dom.compress;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.utils.annotations.ApiMethod;
import com.utils.xml.dom.XmlDomUtils;

public final class XmlDomCompressUtils {

	private static final int LETTER_COUNT = 'z' - 'a';
	private static final int LETTER_COUNT_TIMES_TWO = LETTER_COUNT * 2;

	private XmlDomCompressUtils() {
	}

	@ApiMethod
	public static void compressXml(
			final Document document) {

		final Element documentElement = document.getDocumentElement();
		final Map<String, CompressedElement> compressedElementsByTagNameMap = new LinkedHashMap<>();
		compressElementRec(documentElement, document, compressedElementsByTagNameMap);
		writeCompressionDictionary(documentElement, compressedElementsByTagNameMap);
	}

	private static void compressElementRec(
			final Element element,
			final Document document,
			final Map<String, CompressedElement> compressedElementsByTagNameMap) {

		final List<Element> childElementList = XmlDomUtils.getChildElements(element);
		for (final Element childElement : childElementList) {

			final String childElementTagName = childElement.getTagName();
			CompressedElement compressedElement =
					compressedElementsByTagNameMap.get(childElementTagName);
			if (compressedElement == null) {

				final int index = compressedElementsByTagNameMap.size();
				final String compressedName = createCompressedName(index);
				compressedElement = new CompressedElement(compressedName);
				compressedElementsByTagNameMap.put(childElementTagName, compressedElement);
			}
			final String childElementCompressedName = compressedElement.getCompressedName();
			document.renameNode(childElement, null, childElementCompressedName);

			final List<Attr> attrList = XmlDomUtils.getAttributes(childElement);
			for (final Attr attr : attrList) {

				final String attrName = attr.getName();

				final Map<String, String> attributesByNameMap = compressedElement.getAttributesByNameMap();
				String attrCompressedName = attributesByNameMap.get(attrName);
				if (attrCompressedName == null) {

					final int index = attributesByNameMap.size();
					attrCompressedName = createCompressedName(index);
					attributesByNameMap.put(attrName, attrCompressedName);
				}

				document.renameNode(attr, null, attrCompressedName);
			}

			compressElementRec(childElement, document, compressedElementsByTagNameMap);
		}
	}

	private static void writeCompressionDictionary(
			final Element documentElement,
			final Map<String, CompressedElement> compressedElementsByTagNameMap) {

		final Document document = documentElement.getOwnerDocument();
		final Element compressionDictionaryElement = document.createElement("CompressionDictionary");
		for (final Map.Entry<String, CompressedElement> mapEntry : compressedElementsByTagNameMap.entrySet()) {

			final Element compressedElementElement = document.createElement("CompressedElement");
			final String name = mapEntry.getKey();
			compressedElementElement.setAttribute("Name", name);
			final CompressedElement compressedElement = mapEntry.getValue();
			final String compressedName = compressedElement.getCompressedName();
			compressedElementElement.setAttribute("CompressedName", compressedName);

			final Map<String, String> attributesByNameMap = compressedElement.getAttributesByNameMap();
			for (final Map.Entry<String, String> attrMapEntry : attributesByNameMap.entrySet()) {

				final Element compressedAttributeElement = document.createElement("CompressedAttribute");
				final String attrName = attrMapEntry.getKey();
				compressedAttributeElement.setAttribute("Name", attrName);
				final String attrCompressedName = attrMapEntry.getValue();
				compressedAttributeElement.setAttribute("CompressedName", attrCompressedName);
				compressedElementElement.appendChild(compressedAttributeElement);
			}
			compressionDictionaryElement.appendChild(compressedElementElement);
		}
		documentElement.appendChild(compressionDictionaryElement);
	}

	private static String createCompressedName(
			final int indexParam) {

		final StringBuilder sbCompressedName = new StringBuilder();
		int index = indexParam;
		do {
			final int letterAsciiCode;
			final int modulo = index % LETTER_COUNT_TIMES_TWO;
			if (modulo < LETTER_COUNT) {
				letterAsciiCode = 'a' + modulo;
			} else {
				letterAsciiCode = 'A' + modulo - LETTER_COUNT;
			}
			sbCompressedName.append((char) letterAsciiCode);
			index = index / LETTER_COUNT_TIMES_TWO;
		} while (index > 0);
		return sbCompressedName.toString();
	}

	@ApiMethod
	public static void decompressXml(
			final Document document) {

		final Element documentElement = document.getDocumentElement();
		final Map<String, String> namesByIdMap = new HashMap<>();
		fillCompressedElementsByTagNameMap(documentElement, namesByIdMap);
		decompressElementRec(documentElement, document, namesByIdMap);
	}

	private static void fillCompressedElementsByTagNameMap(
			final Element documentElement,
			final Map<String, String> namesByIdMap) {

		final Element compressionDictionaryElement =
				XmlDomUtils.getFirstElementByTagName(documentElement, "CompressionDictionary");
		if (compressionDictionaryElement != null) {

			final List<Element> compressedElementElementList =
					XmlDomUtils.getElementsByTagName(compressionDictionaryElement, "CompressedElement");
			for (final Element compressedElementElement : compressedElementElementList) {

				final String compressedName = compressedElementElement.getAttribute("CompressedName");
				final String name = compressedElementElement.getAttribute("Name");
				namesByIdMap.put(compressedName, name);

				final List<Element> compressedAttributeElementList =
						XmlDomUtils.getElementsByTagName(compressedElementElement, "CompressedAttribute");
				for (final Element compressedAttributeElement : compressedAttributeElementList) {

					final String attrCompressedName = compressedAttributeElement.getAttribute("CompressedName");
					final String attrId = compressedName + ">" + attrCompressedName;
					final String attrName = compressedAttributeElement.getAttribute("Name");
					namesByIdMap.put(attrId, attrName);
				}
			}
			compressionDictionaryElement.getParentNode().removeChild(compressionDictionaryElement);
		}
	}

	private static void decompressElementRec(
			final Element element,
			final Document document,
			final Map<String, String> namesByIdMap) {

		final List<Element> childElementList = XmlDomUtils.getChildElements(element);
		for (final Element childElement : childElementList) {

			final String childElementCompressedName = childElement.getTagName();
			final String childElementName = namesByIdMap.get(childElementCompressedName);
			if (childElementName != null) {
				document.renameNode(childElement, childElement.getNamespaceURI(), childElementName);
			}

			final List<Attr> attrList = XmlDomUtils.getAttributes(childElement);
			for (final Attr attr : attrList) {

				final String attrCompressedName = attr.getName();
				final String attrId = childElementCompressedName + ">" + attrCompressedName;
				final String attrName = namesByIdMap.get(attrId);
				if (attrName != null) {
					document.renameNode(attr, attr.getNamespaceURI(), attrName);
				}
			}

			decompressElementRec(childElement, document, namesByIdMap);
		}
	}
}
