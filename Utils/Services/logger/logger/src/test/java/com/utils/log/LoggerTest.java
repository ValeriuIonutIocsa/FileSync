package com.utils.log;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class LoggerTest {

	@Test
	void testPrintFinishMessage() {

		final Instant start = Instant.now();
		Logger.printFinishMessage(start);
	}
}
