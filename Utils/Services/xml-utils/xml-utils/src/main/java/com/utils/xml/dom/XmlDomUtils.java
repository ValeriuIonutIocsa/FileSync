package com.utils.xml.dom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.utils.annotations.ApiMethod;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.string.StrUtils;
import com.utils.xml.dom.documents.ValidatedDocument;
import com.utils.xml.dom.openers.DocumentOpenerFile;
import com.utils.xml.dom.openers.DocumentOpenerInputStream;

public final class XmlDomUtils {

	private XmlDomUtils() {
	}

	@ApiMethod
	public static Document createNewDocument() throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.newDocument();
	}

	@ApiMethod
	public static Document openDocument(
			final InputStream inputStream) throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		return new DocumentOpenerInputStream(inputStream, null).openDocument(documentBuilderFactory);
	}

	@ApiMethod
	public static ValidatedDocument openAndValidateDocumentResourceSchema(
			final InputStream inputStream,
			final String schemaResourceFilePathString) throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		return new DocumentOpenerInputStream(inputStream, null)
				.openAndValidateDocumentResourceSchema(documentBuilderFactory, schemaResourceFilePathString);
	}

	@ApiMethod
	public static ValidatedDocument openAndValidateDocumentLocalSchema(
			final InputStream inputStream,
			final String schemaFolderPathString) throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		return new DocumentOpenerInputStream(inputStream, schemaFolderPathString)
				.openAndValidateDocumentLocalSchema(documentBuilderFactory);
	}

	@ApiMethod
	public static ValidatedDocument openAndValidateDocumentResourceSchema(
			final String filePathString,
			final String schemaResourceFilePathString) throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		final File file = new File(filePathString);
		return new DocumentOpenerFile(file)
				.openAndValidateDocumentResourceSchema(documentBuilderFactory, schemaResourceFilePathString);
	}

	@ApiMethod
	public static ValidatedDocument openAndValidateDocumentLocalSchema(
			final String filePathString) throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		final File file = new File(filePathString);
		return new DocumentOpenerFile(file)
				.openAndValidateDocumentLocalSchema(documentBuilderFactory);
	}

	@ApiMethod
	public static Document openDocument(
			final String filePathString) throws Exception {

		final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();
		final File file = new File(filePathString);
		return new DocumentOpenerFile(file).openDocument(documentBuilderFactory);
	}

	private static DocumentBuilderFactory createDocumentBuilderFactory() {

		DocumentBuilderFactory documentBuilderFactory;
		try {
			documentBuilderFactory = DocumentBuilderFactory.newInstance(
					"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
					Thread.currentThread().getContextClassLoader());

		} catch (final Throwable ignored) {
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
		}
		return documentBuilderFactory;
	}

	@ApiMethod
	public static void saveXmlFile(
			final Document document,
			final boolean omitXmlDeclaration,
			final int indentAmount,
			final String outputPathString) throws Exception {

		FactoryFolderCreator.getInstance().createParentDirectories(outputPathString, false, true);
		FactoryReadOnlyFlagClearer.getInstance().clearReadOnlyFlagFile(outputPathString, false, true);

		final StreamResult streamResult = new StreamResult(outputPathString);
		saveXml(document, omitXmlDeclaration, indentAmount, streamResult);
	}

	@ApiMethod
	public static String saveXmlFile(
			final Document document,
			final boolean omitXmlDeclaration,
			final int indentAmount) throws Exception {

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		saveXmlFile(document, omitXmlDeclaration, indentAmount, byteArrayOutputStream);
		return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
	}

	@ApiMethod
	public static void saveXmlFile(
			final Document document,
			final boolean omitXmlDeclaration,
			final int indentAmount,
			final OutputStream outputStream) throws Exception {

		final Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
		final StreamResult streamResult = new StreamResult(writer);
		saveXml(document, omitXmlDeclaration, indentAmount, streamResult);
	}

	private static void saveXml(
			final Document document,
			final boolean omitXmlDeclaration,
			final int indentAmount,
			final StreamResult streamResult) throws Exception {

		final Element documentElement = document.getDocumentElement();
		processTextNodesRec(documentElement);

		final DOMSource domSource = new DOMSource(document);

		final TransformerFactory transformerFactory = TransformerFactory.newInstance(
				"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",
				Thread.currentThread().getContextClassLoader());
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
				StrUtils.booleanToYesNoString(omitXmlDeclaration));
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		if (indentAmount >= 0) {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		}
		if (indentAmount > 0) {
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					String.valueOf(indentAmount));
		}
		transformer.transform(domSource, streamResult);
	}

	@ApiMethod
	public static void processTextNodesRec(
			final Node parentNode) {

		final NodeList nodeList = parentNode.getChildNodes();
		for (int i = nodeList.getLength() - 1; i >= 0; i--) {

			final Node childNode = nodeList.item(i);
			final int nodeType = childNode.getNodeType();
			if (nodeType == Node.ELEMENT_NODE) {
				processTextNodesRec(childNode);

			} else if (nodeType == Node.TEXT_NODE) {

				final String nodeValue = childNode.getNodeValue();
				final String trimmedNodeVal = nodeValue.trim();
				if (trimmedNodeVal.isEmpty()) {
					parentNode.removeChild(childNode);
				} else {
					childNode.setNodeValue(trimmedNodeVal);
				}
			}
		}
	}

	@ApiMethod
	public static List<Element> getChildElements(
			final Element parentElement) {

		final List<Element> childElementList = new ArrayList<>();
		if (parentElement != null) {

			final NodeList childNodes = parentElement.getChildNodes();
			final int childNodesLength = childNodes.getLength();
			for (int i = 0; i < childNodesLength; i++) {

				final Node childNode = childNodes.item(i);
				final int nodeType = childNode.getNodeType();
				if (nodeType == Node.ELEMENT_NODE) {

					final Element childElement = (Element) childNode;
					childElementList.add(childElement);
				}
			}
		}
		return childElementList;
	}

	@ApiMethod
	public static List<Element> getChildElementsByTagName(
			final Element parentElement,
			final String tagName) {

		final List<Element> childElementList = new ArrayList<>();
		if (parentElement != null && tagName != null) {

			final NodeList childNodes = parentElement.getChildNodes();
			final int childNodesLength = childNodes.getLength();
			for (int i = 0; i < childNodesLength; i++) {

				final Node childNode = childNodes.item(i);
				final int nodeType = childNode.getNodeType();
				if (nodeType == Node.ELEMENT_NODE) {

					final Element childElement = (Element) childNode;
					if (tagName.equals(childElement.getTagName())) {

						childElementList.add(childElement);
					}
				}
			}
		}
		return childElementList;
	}

	@ApiMethod
	public static Element getFirstChildElementByTagName(
			final Element parentElement,
			final String tagName) {

		Element element = null;
		if (parentElement != null && tagName != null) {

			final NodeList childNodes = parentElement.getChildNodes();
			final int childNodesLength = childNodes.getLength();
			for (int i = 0; i < childNodesLength; i++) {

				final Node childNode = childNodes.item(i);
				final int nodeType = childNode.getNodeType();
				if (nodeType == Node.ELEMENT_NODE) {

					final Element childElement = (Element) childNode;
					if (tagName.equals(childElement.getTagName())) {

						element = childElement;
						break;
					}
				}
			}
		}
		return element;
	}

	@ApiMethod
	public static List<Element> getElementsByTagName(
			final Element parentElement,
			final String tagName) {

		final List<Element> elementList = new ArrayList<>();
		if (parentElement != null) {

			final NodeList nodeList = parentElement.getElementsByTagName(tagName);
			final int nodeListLength = nodeList.getLength();
			for (int i = 0; i < nodeListLength; i++) {

				final Node node = nodeList.item(i);
				final int nodeType = node.getNodeType();
				if (nodeType == Node.ELEMENT_NODE) {

					final Element element = (Element) node;
					elementList.add(element);
				}
			}
		}
		return elementList;
	}

	@ApiMethod
	public static Element getFirstElementByTagName(
			final Element parentElement,
			final String tagName) {

		Element element = null;
		if (parentElement != null && tagName != null) {

			final NodeList nodeList = parentElement.getElementsByTagName(tagName);
			final int length = nodeList.getLength();
			for (int i = 0; i < length; i++) {

				final Node node = nodeList.item(i);
				final int nodeType = node.getNodeType();
				if (nodeType == Node.ELEMENT_NODE) {

					element = (Element) node;
					break;
				}
			}
		}
		return element;
	}

	@ApiMethod
	public static String computeAttributeValue(
			final Element parentElement,
			final String tagName,
			final String attributeName) {

		String attributeValue = "";
		final Element element = XmlDomUtils.getFirstElementByTagName(parentElement, tagName);
		if (element != null) {
			attributeValue = element.getAttribute(attributeName);
		}
		return attributeValue;
	}

	@ApiMethod
	public static List<Attr> getAttributes(
			final Element element) {

		final List<Attr> attrList = new ArrayList<>();
		final NamedNodeMap attributeMap = element.getAttributes();
		final int attributeMapLength = attributeMap.getLength();
		for (int i = 0; i < attributeMapLength; i++) {

			final Node item = attributeMap.item(i);
			final Attr attr = (Attr) item;
			attrList.add(attr);
		}
		return attrList;
	}

	@ApiMethod
	public static void removeElementsByTagName(
			final Element parentElement,
			final String tagName) {

		if (parentElement != null) {

			final List<Element> elementList = getElementsByTagName(parentElement, tagName);
			for (final Element element : elementList) {

				final Node parentNode = element.getParentNode();
				parentNode.removeChild(element);
			}
		}
	}

	@ApiMethod
	public static String getFirstLevelTextContent(
			final Node node) {

		final StringBuilder textContent = new StringBuilder();
		final NodeList nodeList = node.getChildNodes();
		final int nodeListLength = nodeList.getLength();
		for (int i = 0; i < nodeListLength; i++) {

			final Node childNode = nodeList.item(i);
			if (childNode != null) {

				final int nodeType = childNode.getNodeType();
				if (nodeType == Node.TEXT_NODE) {

					final String childNodeTextContent = childNode.getTextContent();
					textContent.append(childNodeTextContent);
				}
			}
		}
		return textContent.toString();
	}

	@ApiMethod
	public static String getChildTextContent(
			final Element parentElement,
			final String childTagName) {

		String textContent = null;
		final Element childElement = getFirstElementByTagName(parentElement, childTagName);
		if (childElement != null) {

			textContent = childElement.getTextContent();
			textContent = textContent.trim();
		}
		return textContent;
	}

	@ApiMethod
	public static void createChildWithTextContent(
			final Element parentElement,
			final String childTagName,
			final String textContent) {

		final Document document = parentElement.getOwnerDocument();
		final Element childElement = document.createElement(childTagName);
		childElement.setTextContent(textContent);

		parentElement.appendChild(childElement);
	}

	@ApiMethod
	public static void cloneNode(
			final Node srcNode,
			final Node destNode) {

		removeAllAttributes(destNode);

		final NamedNodeMap srcAttributesMap = srcNode.getAttributes();
		final NamedNodeMap dstAttributesMap = destNode.getAttributes();
		for (int i = 0; i < srcAttributesMap.getLength(); i++) {

			final Node attrNode = srcAttributesMap.item(i);
			final Document document = destNode.getOwnerDocument();
			final Node importedAttrNode = document.importNode(attrNode, true);
			dstAttributesMap.setNamedItem(importedAttrNode);
		}

		removeAllChildren(destNode);

		final NodeList srcChildNodeList = srcNode.getChildNodes();
		for (int i = 0; i < srcChildNodeList.getLength(); i++) {

			final Node childNode = srcChildNodeList.item(i);
			final Document document = destNode.getOwnerDocument();
			final Node importedChildNode = document.importNode(childNode, true);
			destNode.appendChild(importedChildNode);
		}
	}

	@ApiMethod
	public static void removeAllAttributes(
			final Node node) {

		final List<String> attributeNameList = new ArrayList<>();

		final NamedNodeMap attributeMap = node.getAttributes();
		for (int i = 0; i < attributeMap.getLength(); i++) {

			final Attr attr = (Attr) attributeMap.item(i);
			final String name = attr.getName();
			attributeNameList.add(name);
		}

		for (final String attributeName : attributeNameList) {
			attributeMap.removeNamedItem(attributeName);
		}
	}

	@ApiMethod
	public static void removeAllChildren(
			final Node node) {

		while (node.hasChildNodes()) {

			final Node firstChild = node.getFirstChild();
			node.removeChild(firstChild);
		}
	}

	@ApiMethod
	public static boolean isChildOf(
			final Element element,
			final Element parentElement) {

		boolean childOf = false;
		Node node = element;
		while (node != null) {

			if (node.isEqualNode(parentElement)) {
				childOf = true;
				break;
			}
			node = node.getParentNode();
		}
		return childOf;
	}

	@ApiMethod
	public static String nodeToString(
			final Node node) {

		final Document document = node.getOwnerDocument();
		final DOMImplementationLS domImplementationLS =
				(DOMImplementationLS) document.getImplementation();
		final LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		return lsSerializer.writeToString(node);
	}
}
