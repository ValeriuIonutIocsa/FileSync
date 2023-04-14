package com.utils.string.converters;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class ConverterDateTest {

	@Test
	void testDateToString() {

		final String str = ConverterDate.dateToString(new Date());
		Logger.printLine(str);
	}

	@Test
	void testTryParseDate() {

		final Date date = ConverterDate.tryParseDate("2020-Feb-28 11:07:38 PM");
		Assertions.assertNotNull(date);

		Logger.printLine(ConverterDate.dateToString(date));
	}
}
