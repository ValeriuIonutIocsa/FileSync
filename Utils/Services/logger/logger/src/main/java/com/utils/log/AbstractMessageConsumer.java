package com.utils.log;

public abstract class AbstractMessageConsumer implements MessageConsumer {

	@Override
	public void printMessage(
			final MessageLevel messageLevel,
			final String messageParam) {

		final boolean debugMode = Logger.isDebugMode();
		if (debugMode || messageLevel != MessageLevel.EXCEPTION) {

			String message = messageParam;
			if (message == null) {
				message = "NULL";
			}
			printMessageSpecific(messageLevel, message);
		}
	}
}
