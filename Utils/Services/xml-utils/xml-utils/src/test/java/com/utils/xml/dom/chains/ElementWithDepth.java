package com.utils.xml.dom.chains;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.utils.string.StrUtils;

class ElementWithDepth {

	private final Element element;
	private final int depth;

	private List<String> chainedElementList;

	ElementWithDepth(
			final Element element,
			final int depth) {

		this.element = element;
		this.depth = depth;
	}

	public void computeElementChain() {

		chainedElementList = new ArrayList<>();
		fillChainedElementListRec(element, chainedElementList);
	}

	private static void fillChainedElementListRec(
			final Node node,
			final List<String> chainedElementList) {

		if (node instanceof Element) {

			final Element element = (Element) node;
			final String chainedElement = elementToString(element);
			chainedElementList.add(0, chainedElement);
		}

		final Node parentNode = node.getParentNode();
		if (parentNode != null) {
			fillChainedElementListRec(parentNode, chainedElementList);
		}
	}

	private static String elementToString(
			final Element element) {

		final StringBuilder sbElementString = new StringBuilder();

		sbElementString.append('<');
		final String tagName = element.getTagName();
		sbElementString.append(tagName);

		final NamedNodeMap attributes = element.getAttributes();
		final int length = attributes.getLength();
		for (int i = 0; i < length; i++) {

			final Node attributeNode = attributes.item(i);
			if (attributeNode instanceof Attr) {

				final Attr attr = (Attr) attributeNode;
				final String attrName = attr.getName();
				final String attrValue = attr.getValue();

				sbElementString.append(' ');
				sbElementString.append(attrName);
				sbElementString.append("=\"");
				sbElementString.append(attrValue);
                sbElementString.append('"');
			}
		}
        sbElementString.append('>');
		return sbElementString.toString();
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	int getDepth() {
		return depth;
	}

	List<String> getChainedElementList() {
		return chainedElementList;
	}
}
