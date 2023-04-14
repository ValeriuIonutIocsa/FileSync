package com.utils.log;

public interface MessageConsumer {

	void printMessage(
			MessageLevel messageLevel,
			String messageParam);

	void printMessageSpecific(
			MessageLevel messageLevel,
			String message);
}
