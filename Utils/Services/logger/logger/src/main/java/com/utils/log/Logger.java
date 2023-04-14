package com.utils.log;

import java.time.Duration;
import java.time.Instant;

import com.utils.annotations.ApiMethod;
import com.utils.string.StrUtils;
import com.utils.string.gradle.GradleUtils;
import com.utils.string.junit.JUnitUtils;

public final class Logger {

	private static MessageConsumer messageConsumer = new MessageConsumerDefault();

	private static boolean debugMode;

	static {
		final boolean jUnitTest = JUnitUtils.isJUnitTest();
		if (jUnitTest) {
			debugMode = true;
		}

		final boolean gradle = GradleUtils.isGradle();
		if (gradle) {
			System.setErr(System.out);
		}
	}

	private Logger() {
	}

	@ApiMethod
	public static void printNewLine() {
		printLine("");
	}

	@ApiMethod
	public static void printLine(
			final Object object) {

		final String message = String.valueOf(object);
		printLine(message);
	}

	@ApiMethod
	public static void printLine(
			final String message) {
		printMessage(MessageLevel.INFO, message);
	}

	@ApiMethod
	public static void printProgress(
			final Object object) {

		final String message = String.valueOf(object);
		printProgress(message);
	}

	@ApiMethod
	public static void printProgress(
			final String messageParam) {

		final StringBuilder sbMessage = new StringBuilder(messageParam);
		sbMessage.insert(0, "--> ");
		printMessage(MessageLevel.PROGRESS, sbMessage.toString());
	}

	@ApiMethod
	public static void printStatus(
			final Object object) {

		final String message = String.valueOf(object);
		printStatus(message);
	}

	@ApiMethod
	public static void printStatus(
			final String message) {
		printMessage(MessageLevel.STATUS, message);
	}

	@ApiMethod
	public static void printWarning(
			final Object object) {

		final String message = String.valueOf(object);
		printWarning(message);
	}

	@ApiMethod
	public static void printWarning(
			final String messageParam) {

		final StringBuilder sbMessage = new StringBuilder(messageParam);
		sbMessage.insert(0, "WARNING - ");
		printMessage(MessageLevel.WARNING, sbMessage.toString());
	}

	@ApiMethod
	public static void printError(
			final Object object) {

		final String message = String.valueOf(object);
		printError(message);
	}

	@ApiMethod
	public static void printError(
			final String messageParam) {

		final StringBuilder sbMessage = new StringBuilder(messageParam);
		sbMessage.insert(0, "ERROR - ");
		printMessage(MessageLevel.ERROR, sbMessage.toString());
	}

	@ApiMethod
	public static void printException(
			final Throwable throwable) {

		final String message = exceptionToString(throwable);
		printMessage(MessageLevel.EXCEPTION, message);
	}

	@ApiMethod
	public static String exceptionToString(
			final Throwable throwable) {

		final StringBuilder sbExceptionString = new StringBuilder();
		if (throwable == null) {
			sbExceptionString.append("NULL exception");

		} else {
			final Class<? extends Throwable> excClass = throwable.getClass();
			final String excClassSimpleName = excClass.getSimpleName();
			sbExceptionString.append("exception of class \"").append(excClassSimpleName)
					.append("\" has occurred").append(System.lineSeparator());

			final String excMessage = throwable.getMessage();
			sbExceptionString.append(excMessage).append(System.lineSeparator());

			final StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
			for (int i = 0; i < stackTraceElementArray.length; i++) {

				final StackTraceElement stackTraceElement = stackTraceElementArray[i];
				sbExceptionString.append(stackTraceElement);
				if (i < stackTraceElementArray.length - 1) {
					sbExceptionString.append(System.lineSeparator());
				}
			}
		}
		return sbExceptionString.toString();
	}

	@ApiMethod
	public static void printFinishMessage(
			final Instant start) {
		printFinishMessage("Done.", start);
	}

	@ApiMethod
	public static void printFinishMessage(
			final String message,
			final Instant start) {

		final Duration executionTime = Duration.between(start, Instant.now());
		printStatus(message + " Execution time: " + StrUtils.durationToString(executionTime));
	}

	@ApiMethod
	public static void printToBeImplemented(
			final String name) {
		printLine(name + " (to be implemented...)");
	}

	@ApiMethod
	public static void printDebugLine(
			final Object message) {
		printMessage(MessageLevel.INFO, String.valueOf(message));
	}

	@ApiMethod
	public static void printDebugError(
			final Object message) {
		printMessage(MessageLevel.ERROR, String.valueOf(message));
	}

	@ApiMethod
	public static void printDebugTime(
			final long start) {

		final long nanoTime = System.nanoTime() - start;
		printMessage(MessageLevel.ERROR, StrUtils.nanoTimeToString(nanoTime));
	}

	private static void printMessage(
			final MessageLevel info,
			final String message) {
		messageConsumer.printMessage(info, message);
	}

	@ApiMethod
	public static void setMessageConsumer(
			final MessageConsumer messageConsumer) {
		Logger.messageConsumer = messageConsumer;
	}

	@ApiMethod
	public static MessageConsumer getMessageConsumer() {
		return messageConsumer;
	}

	@ApiMethod
	public static void setDebugMode(
			final boolean debugMode) {
		Logger.debugMode = debugMode;
	}

	@ApiMethod
	public static boolean isDebugMode() {
		return debugMode;
	}
}
