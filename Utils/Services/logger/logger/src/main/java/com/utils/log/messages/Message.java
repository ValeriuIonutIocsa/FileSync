package com.utils.log.messages;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.utils.string.StrUtils;

public class Message implements Serializable, Comparable<Message> {

	private static final long serialVersionUID = 4096644278551613552L;

	private final MessageType messageType;
	private final int displayOrder;
	private final String messageCategory;
	private final String messageString;

	Message(
			final MessageType messageType,
			final int displayOrder,
			final String messageCategory,
			final String messageString) {

		this.messageType = messageType;
		this.displayOrder = displayOrder;
		this.messageCategory = messageCategory;
		this.messageString = messageString;
	}

	@Override
	public int compareTo(
			final Message other) {

		return new CompareToBuilder()
				.append(messageType, other.messageType)
				.append(displayOrder, other.displayOrder)
				.toComparison();
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(
			final Object obj) {

		final boolean result;
		if (getClass().isInstance(obj)) {
			result = compareTo(getClass().cast(obj)) == 0;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public String getMessageCategory() {
		return messageCategory;
	}

	public String getMessageString() {
		return messageString;
	}
}
