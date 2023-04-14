package com.utils.log.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.utils.string.StrUtils;

public class Messages implements Serializable {

	private static final long serialVersionUID = -7658013346641279882L;

	private final List<Message> messageList;
	private final int[] messageCountArray;

	public Messages() {

		messageList = new ArrayList<>();
		messageCountArray = new int[3];
	}

	public void addMessages(
			final Messages messages) {

		messageList.addAll(messages.messageList);
		for (int i = 0; i < messageCountArray.length; i++) {
			messageCountArray[i] += messages.messageCountArray[i];
		}
	}

	public void addMessage(
			final MessageType messageType,
			final int displayOrder,
			final String categoryName,
			final String messageString) {

		final Message message =
				new Message(messageType, displayOrder, categoryName, messageString);
		messageList.add(message);

		messageCountArray[messageType.ordinal()]++;
	}

	public int computeMessageCount(
			final MessageType messageType) {
		return messageCountArray[messageType.ordinal()];
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public List<Message> createDisplayMessageList() {

		final List<Message> displayMessageList = new ArrayList<>(messageList);
		displayMessageList.sort(Comparator.naturalOrder());

		final int criticalCount = computeMessageCount(MessageType.CRITICAL);
		final int warningCount = computeMessageCount(MessageType.WARNING);
		final int infoCount = computeMessageCount(MessageType.INFO);
		final boolean problemCount = criticalCount + warningCount + infoCount == 0;
		if (problemCount) {

			final Message approvalMessage = new Message(MessageType.APPROVAL, 0,
					"no problems found", "Good Job! No problems were found.");
			displayMessageList.add(approvalMessage);
		}
		return displayMessageList;
	}
}
