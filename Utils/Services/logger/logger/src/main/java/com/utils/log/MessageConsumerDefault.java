package com.utils.log;

public class MessageConsumerDefault extends AbstractMessageConsumer {

	@Override
	public void printMessageSpecific(
			final MessageLevel messageLevel,
			final String message) {

		if (messageLevel == MessageLevel.INFO ||
				messageLevel == MessageLevel.PROGRESS ||
				messageLevel == MessageLevel.STATUS) {
			System.out.println(message);

		} else if (messageLevel == MessageLevel.WARNING ||
				messageLevel == MessageLevel.ERROR ||
				messageLevel == MessageLevel.EXCEPTION) {
			System.err.println(message);
		}
	}
}
