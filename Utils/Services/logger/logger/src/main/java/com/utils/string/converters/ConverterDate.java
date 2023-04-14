package com.utils.string.converters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ConverterDate {

	private final static String SIMPLE_DATE_FORMAT = "yyyy-MMM-dd HH:mm:ss";

	private ConverterDate() {
	}

	public static String dateToString(
			final Date date) {
		return new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US).format(date);
	}

	public static Date tryParseDate(
			final String dateString) {

		Date date = null;
		try {
			date = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US).parse(dateString);
		} catch (final Exception ignored) {
		}
		return date;
	}
}
