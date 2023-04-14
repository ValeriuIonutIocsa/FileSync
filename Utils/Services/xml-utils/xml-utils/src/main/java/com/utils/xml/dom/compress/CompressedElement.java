package com.utils.xml.dom.compress;

import java.util.LinkedHashMap;
import java.util.Map;

import com.utils.string.StrUtils;

class CompressedElement {

	private final String compressedName;
	private final Map<String, String> attributesByNameMap;

	CompressedElement(
			final String compressedName) {

		this.compressedName = compressedName;

		attributesByNameMap = new LinkedHashMap<>();
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	String getCompressedName() {
		return compressedName;
	}

	Map<String, String> getAttributesByNameMap() {
		return attributesByNameMap;
	}
}
