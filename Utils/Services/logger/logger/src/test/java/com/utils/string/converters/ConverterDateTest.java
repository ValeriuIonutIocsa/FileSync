package com.utils.string.converters;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.utils.log.Logger;
import com.utils.string.StrUtils;

class ConverterDateTest {

	private static Date date;

	@BeforeAll
	static void beforeAll() {

		final int input = StrUtils.tryParsePositiveInt("1");
		if (input == 1) {
			date = new Date();

		} else if (input == 11) {
			date = ConverterDate.stringToDate("2024-Feb-18 18:23:19.674 EET");

		} else if (input == 21) {
			date = ConverterDate.stringToDate("2020-Feb-28 18:07:38");

		} else {
			throw new RuntimeException();
		}
	}

	@Test
	void testStringToDate() {

		final String dateString = ConverterDate.dateToString(date);
		Logger.printLine("dateString: " + dateString);

		final Date date = ConverterDate.stringToDate(dateString);
		final String secondDateString = ConverterDate.dateToString(date);

		Assertions.assertEquals(dateString, secondDateString);
	}
}
