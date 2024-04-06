package com.utils.string.converters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ConverterDate {

	private final static String SIMPLE_DATE_FORMAT = "yyyy-MMM-dd HH:mm:ss";
	private final static String FULL_DATE_FORMAT = "yyyy-MMM-dd HH:mm:ss.SSS z";

	private ConverterDate() {
	}

	public static String dateToString(
			final Date date) {

		return new SimpleDateFormat(FULL_DATE_FORMAT, Locale.US).format(date);
	}

	public static Date stringToDate(
			final String dateString) {

		Date date = tryParseDate(dateString, FULL_DATE_FORMAT);
		if (date == null) {
			date = tryParseDate(dateString, SIMPLE_DATE_FORMAT);
		}
		return date;
	}

	private static Date tryParseDate(
			final String dateString,
			final String dateFormat) {

		Date date = null;
		try {
			date = new SimpleDateFormat(dateFormat, Locale.US).parse(dateString);

		} catch (final Exception ignored) {
		}
		return date;
	}
}
