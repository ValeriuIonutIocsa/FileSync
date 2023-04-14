package com.utils.string;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;
import com.utils.string.characters.SpecialCharacterUtils;

public final class StrUtils {

	private StrUtils() {
	}

	@ApiMethod
	public static String booleanToIntString(
			final boolean b) {

		final String intString;
		if (b) {
			intString = "1";
		} else {
			intString = "0";
		}
		return intString;
	}

	@ApiMethod
	public static String booleanToYesNoString(
			final boolean b) {

		final String yesNoString;
		if (b) {
			yesNoString = "yes";
		} else {
			yesNoString = "no";
		}
		return yesNoString;
	}

	/**
	 * @param n
	 *            Integer value
	 * @return empty string if n is null, string value of n otherwise
	 */
	@ApiMethod
	public static String integerToString(
			final Integer n,
			final boolean useGrouping) {

		final String str;
		if (n != null) {
			if (useGrouping) {
				str = NumberFormat.getNumberInstance(Locale.US).format(n);
			} else {
				str = String.valueOf(n);
			}
		} else {
			str = "";
		}
		return str;
	}

	/**
	 * @param n
	 *            Long value
	 * @return empty string if n is null, string value of n otherwise
	 */
	@ApiMethod
	public static String longToString(
			final Long n,
			final boolean useGrouping) {

		final String str;
		if (n != null) {
			if (useGrouping) {
				str = NumberFormat.getNumberInstance(Locale.US).format(n);
			} else {
				str = String.valueOf(n);
			}
		} else {
			str = "";
		}
		return str;
	}

	@ApiMethod
	public static String positiveByteToString(
			final byte b) {

		final String str;
		if (b >= 0) {
			str = String.valueOf(b);
		} else {
			str = "";
		}
		return str;
	}

	/**
	 * @param n
	 *            int value
	 * @return empty string if n < 0, string value of n otherwise
	 */
	@ApiMethod
	public static String positiveIntToString(
			final int n,
			final boolean useGrouping) {

		final String str;
		if (n >= 0) {
			if (useGrouping) {
				str = NumberFormat.getNumberInstance(Locale.US).format(n);
			} else {
				str = String.valueOf(n);
			}
		} else {
			str = "";
		}
		return str;
	}

	/**
	 * @param n
	 *            long value
	 * @return empty string if n < 0, string value of n otherwise
	 */
	@ApiMethod
	public static String positiveLongToString(
			final long n,
			final boolean useGrouping) {

		final String str;
		if (n >= 0) {
			if (useGrouping) {
				str = NumberFormat.getNumberInstance(Locale.US).format(n);
			} else {
				str = String.valueOf(n);
			}
		} else {
			str = "";
		}
		return str;
	}

	@ApiMethod
	public static String unsignedIntToHexString(
			final int n) {

		final long longN = Integer.toUnsignedLong(n);
		return createHexString(longN);
	}

	@ApiMethod
	public static String positiveIntToHexString(
			final int n) {

		final String str;
		if (n >= 0) {
			str = createHexString(n);
		} else {
			str = "";
		}
		return str;
	}

	@ApiMethod
	public static String positiveLongToHexString(
			final long n) {

		final String str;
		if (n >= 0) {
			str = createHexString(n);
		} else {
			str = "";
		}
		return str;
	}

	@ApiMethod
	public static String createHexString(
			final long value) {

		final StringBuilder stringBuilder = new StringBuilder();
		appendHexString(value, stringBuilder);
		return stringBuilder.toString();
	}

	@ApiMethod
	public static void appendHexString(
			final long value,
			final StringBuilder stringBuilder) {

		stringBuilder
				.append("0x")
				.append(Long.toHexString(value));
	}

	@ApiMethod
	public static void printHexString(
			final long value,
			final PrintStream printStream) {

		printStream.print("0x");
		printStream.print(Long.toHexString(value));
	}

	@ApiMethod
	public static String byteArrayToBinaryString(
			final byte[] byteArray) {

		final StringBuilder sb = new StringBuilder();
		if (byteArray != null) {

			for (final byte b : byteArray) {
				sb.append(unsignedIntToPaddedBinaryString(Byte.toUnsignedInt(b), 8));
			}
		}
		return sb.toString();
	}

	@ApiMethod
	public static String byteArrayToBinaryString(
			final byte[] byteArray,
			final String separator) {

		final StringBuilder sb = new StringBuilder();
		if (byteArray != null) {

			for (int i = 0; i < byteArray.length; i++) {

				final byte b = byteArray[i];
				sb.append(unsignedIntToPaddedBinaryString(Byte.toUnsignedInt(b), 8));
				if (i < byteArray.length - 1) {
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}

	@ApiMethod
	public static String byteArrayToHexString(
			final byte[] byteArray) {

		final StringBuilder sb = new StringBuilder();
		if (byteArray != null) {

			for (final byte b : byteArray) {
				sb.append(unsignedIntToPaddedHexString(Byte.toUnsignedInt(b), 2));
			}
		}
		return sb.toString();
	}

	@ApiMethod
	public static String byteArrayToHexString(
			final byte[] byteArray,
			final String separator) {

		final StringBuilder sb = new StringBuilder();
		if (byteArray != null) {

			for (int i = 0; i < byteArray.length; i++) {

				final byte b = byteArray[i];
				sb.append(unsignedIntToPaddedHexString(Byte.toUnsignedInt(b), 2));
				if (i < byteArray.length - 1) {
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}

	@ApiMethod
	public static String createLeftPaddedString(
			final String str,
			final int length) {

		final StringBuilder stringBuilder = new StringBuilder();
		appendLeftPaddedString(str, length, stringBuilder);
		return stringBuilder.toString();
	}

	@ApiMethod
	public static String createRightPaddedString(
			final String str,
			final int length) {

		final StringBuilder stringBuilder = new StringBuilder();
		appendRightPaddedString(str, length, stringBuilder);
		return stringBuilder.toString();
	}

	@ApiMethod
	public static void appendLeftPaddedString(
			final String str,
			final int length,
			final StringBuilder stringBuilder) {

		final int paddingLength = length - str.length();
		if (paddingLength > 0) {

			int i = 0;
			while (i < paddingLength) {

				stringBuilder.append(' ');
				i++;
			}
		}
		stringBuilder.append(str);
	}

	@ApiMethod
	public static void appendRightPaddedString(
			final String str,
			final int length,
			final StringBuilder stringBuilder) {

		stringBuilder.append(str);
		final int paddingLength = length - str.length();
		if (paddingLength > 0) {

			int i = 0;
			while (i < paddingLength) {

				stringBuilder.append(' ');
				i++;
			}
		}
	}

	@ApiMethod
	public static void printLeftPaddedString(
			final String str,
			final int length,
			final PrintStream printStream) {

		final int paddingLength = length - str.length();
		for (int i = 0; i < paddingLength; i++) {
			printStream.append(' ');
		}
		printStream.append(str);
	}

	@ApiMethod
	public static void printRightPaddedString(
			final String str,
			final int length,
			final PrintStream printStream) {

		printStream.append(str);
		final int paddingLength = length - str.length();
		for (int i = 0; i < paddingLength; i++) {
			printStream.append(' ');
		}
	}

	@ApiMethod
	public static String unsignedIntToPaddedBinaryString(
			final int n,
			final int size) {

		final String bitString = Integer.toUnsignedString(n, 2);
		final StringBuilder stringBuilder = new StringBuilder(bitString);

		for (int i = bitString.length(); i < size; i++) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String unsignedIntToPaddedString(
			final int n,
			final int size) {

		final String bitString = Integer.toUnsignedString(n);
		final StringBuilder stringBuilder = new StringBuilder(bitString);

		for (int i = bitString.length(); i < size; i++) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String unsignedIntToPaddedHexString(
			final int n,
			final int size) {

		final String bitString = Integer.toUnsignedString(n, 16);
		final StringBuilder stringBuilder = new StringBuilder(bitString);

		for (int i = bitString.length(); i < size; i++) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String unsignedLongToPaddedBinaryString(
			final long n,
			final int size) {

		final String bitString = Long.toUnsignedString(n, 2);
		final StringBuilder stringBuilder = new StringBuilder(bitString);

		for (int i = bitString.length(); i < size; i++) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String unsignedLongToPaddedString(
			final long n,
			final int size) {

		final String bitString = Long.toUnsignedString(n);
		final StringBuilder stringBuilder = new StringBuilder(bitString);

		for (int i = bitString.length(); i < size; i++) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String unsignedLongToPaddedHexString(
			final long n,
			final int size) {

		final String bitString = Long.toUnsignedString(n, 16);
		final StringBuilder stringBuilder = new StringBuilder(bitString);

		for (int i = bitString.length(); i < size; i++) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String timeSToString(
			final double time) {
		return doubleToString(time, 0, 2, true) + "s";
	}

	@ApiMethod
	public static void printRepeatedString(
			final String string,
			final int count,
			final PrintStream printStream) {

		for (int i = 0; i < count; i++) {
			printStream.print(string);
		}
	}

	@ApiMethod
	public static String timeMsToString(
			final double time) {
		return doubleToString(time, 0, 2, true) + "ms";
	}

	@ApiMethod
	public static String timeUsToString(
			final double time) {
		return doubleToString(time, 0, 2, true) + SpecialCharacterUtils.MU + "s";
	}

	@ApiMethod
	public static String timeNsToString(
			final double time) {
		return doubleToString(time, 0, 2, true) + "ns";
	}

	/**
	 * @param d
	 *            the input double number between 0 and 1
	 * @param digitsCount
	 *            the number of digits after the decimal point in the output String
	 * @return a percentage representation of the input double number
	 */
	@ApiMethod
	public static String doubleToPercentageString(
			final double d,
			final int digitsCount) {

		final String str;
		final String tmpStr = doubleToString(d * 100, 0, digitsCount, false);
		if (tmpStr.isEmpty()) {
			str = "";
		} else {
			str = tmpStr + "%";
		}
		return str;
	}

	/**
	 * @param dObj
	 *            the input double number
	 * @param mandatoryDigitCount
	 *            digits after the decimal point that will be there even if they are equal to 0
	 * @param optionalDigitCount
	 *            digits after the mandatory digits that will be there only if they are not equal to 0
	 * @param useGrouping
	 *            group every 3 digits
	 * @return the formatted String representation of the input float number
	 */
	@ApiMethod
	public static String doubleObjectToFloatString(
			final Double dObj,
			final int mandatoryDigitCount,
			final int optionalDigitCount,
			final boolean useGrouping) {

		final String str;
		final String tmpStr = doubleObjectToString(dObj, mandatoryDigitCount, optionalDigitCount, useGrouping);
		if (tmpStr.isEmpty()) {
			str = "";
		} else {
			str = tmpStr + "f";
		}
		return str;
	}

	/**
	 * @param dObj
	 *            the input double number
	 * @param mandatoryDigitCount
	 *            digits after the decimal point that will be there even if they are equal to 0
	 * @param optionalDigitCount
	 *            digits after the mandatory digits that will be there only if they are not equal to 0
	 * @param useGrouping
	 *            group every 3 digits
	 * @return the formatted String representation of the input double number
	 */
	@ApiMethod
	public static String doubleObjectToString(
			final Double dObj,
			final int mandatoryDigitCount,
			final int optionalDigitCount,
			final boolean useGrouping) {

		final String str;
		if (dObj == null) {
			str = "";
		} else {
			str = doubleToString(dObj, mandatoryDigitCount, optionalDigitCount, useGrouping);
		}
		return str;
	}

	/**
	 * @param d
	 *            the input double number
	 * @param mandatoryDigitCount
	 *            digits after the decimal point that will be there even if they are equal to 0
	 * @param optionalDigitCount
	 *            digits after the mandatory digits that will be there only if they are not equal to 0
	 * @param useGrouping
	 *            group every 3 digits
	 * @return the formatted String representation of the input float number
	 */
	@ApiMethod
	public static String doubleToFloatString(
			final double d,
			final int mandatoryDigitCount,
			final int optionalDigitCount,
			final boolean useGrouping) {

		final String str;
		final String tmpStr = doubleToString(d, mandatoryDigitCount, optionalDigitCount, useGrouping);
		if (tmpStr.isEmpty()) {
			str = "";
		} else {
			str = tmpStr + "f";
		}
		return str;
	}

	/**
	 * @param d
	 *            the input float number
	 * @param mandatoryDigitCount
	 *            digits after the decimal point that will be there even if they are equal to 0
	 * @param optionalDigitCount
	 *            digits after the mandatory digits that will be there only if they are not equal to 0
	 * @param useGrouping
	 *            group every 3 digits
	 * @return the formatted String representation of the input float number
	 */
	@ApiMethod
	public static String doubleToString(
			final double d,
			final int mandatoryDigitCount,
			final int optionalDigitCount,
			final boolean useGrouping) {

		final String str;
		if (Double.isNaN(d)) {
			str = "";

		} else {
			final String format;
			if (mandatoryDigitCount > 0 || optionalDigitCount > 0) {
				format = "0." + StringUtils.repeat('0', mandatoryDigitCount) +
						StringUtils.repeat('#', optionalDigitCount);
			} else {
				format = "0";
			}
			final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
			final DecimalFormat decimalFormat = new DecimalFormat(format, decimalFormatSymbols);
			if (useGrouping) {
				decimalFormat.setGroupingUsed(true);
				decimalFormat.setGroupingSize(3);
			}
			str = decimalFormat.format(d);
		}
		return str;
	}

	@ApiMethod
	public static String doubleObjectToString(
			final Double dObj) {

		final String str;
		if (dObj == null) {
			str = "";
		} else {
			str = doubleToString(dObj);
		}
		return str;
	}

	@ApiMethod
	public static String doubleToString(
			final double d) {

		final String str;
		if (Double.isNaN(d)) {
			str = "";
		} else {
			str = String.valueOf(d);
		}
		return str;
	}

	@ApiMethod
	public static String durationToString(
			final Duration duration) {

		final StringBuilder stringBuilder = new StringBuilder();
		final long allSeconds = duration.get(ChronoUnit.SECONDS);
		final long hours = allSeconds / 3600;
		if (hours > 0) {
			stringBuilder.append(hours).append("h ");
		}

		final long minutes = (allSeconds - hours * 3600) / 60;
		if (minutes > 0) {
			stringBuilder.append(minutes).append("m ");
		}

		final long nanoseconds = duration.get(ChronoUnit.NANOS);
		final double seconds = allSeconds - hours * 3600 - minutes * 60 +
				nanoseconds / 1_000_000_000.0;
		stringBuilder.append(doubleToString(seconds, 3, 0, false)).append('s');

		return stringBuilder.toString();
	}

	@ApiMethod
	public static String nanoTimeToString(
			final long nanoTime) {

		final StringBuilder stringBuilder = new StringBuilder();
		if (nanoTime >= 0) {

			if (nanoTime > 1_000_000_000) {
				final long timeS = nanoTime / 1_000_000_000;
				stringBuilder.append(timeS).append("s ");
			}

			if (nanoTime > 1_000_000) {
				final long timeMs = (nanoTime / 1_000_000) % 1_000;
				stringBuilder.append(timeMs).append("ms ");
			}

			if (nanoTime > 1_000) {
				final long timeUs = (nanoTime / 1_000) % 1_000;
				stringBuilder.append(timeUs).append(SpecialCharacterUtils.MU).append("s ");
			}

			final long timeNs = nanoTime % 1_000;
			stringBuilder.append(timeNs).append("ns");
		}
		return stringBuilder.toString();
	}

	/**
	 * @param object
	 *            the input object
	 * @return a String representation of the input Object created using reflection
	 */
	@ApiMethod
	public static String reflectionToString(
			final Object object) {
		return ReflectionToString.work(object);
	}

	@ApiMethod
	public static String removeNonDigits(
			final String str) {

		final String result;
		if (str == null) {
			result = null;

		} else {
			final StringBuilder sbResult = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {

				final char ch = str.charAt(i);
				final boolean digit = Character.isDigit(ch);
				if (digit) {
					sbResult.append(ch);
				}
			}
			result = sbResult.toString();
		}
		return result;
	}

	@ApiMethod
	public static String removeWhiteSpace(
			final String str) {

		final String result;
		if (str == null) {
			result = null;

		} else {
			final StringBuilder sbResult = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {

				final char ch = str.charAt(i);
				final boolean whitespace = Character.isWhitespace(ch);
				if (!whitespace) {
					sbResult.append(ch);
				}
			}
			result = sbResult.toString();
		}
		return result;
	}

	@ApiMethod
	public static String createDateTimeString() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
	}

	@ApiMethod
	public static String createPathDateTimeString() {
		return new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_SSS__zzz", Locale.US).format(new Date());
	}

	@ApiMethod
	public static String createDisplayDateTimeString() {
		return new SimpleDateFormat("dd MMM yyyy, hh:mm:ss zzz", Locale.US).format(new Date());
	}

	@ApiMethod
	public static boolean parseBooleanFromIntString(
			final String booleanString) {
		return "1".equals(booleanString);
	}

	@ApiMethod
	public static Byte tryParseByte(
			final String byteString) {

		Byte value = null;
		try {
			value = (byte) Integer.parseInt(byteString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static Byte tryParseByteFromHexString(
			final String byteString) {

		Byte value = null;
		try {
			value = (byte) Integer.parseInt(byteString, 16);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static Byte tryParseByteFromBinaryString(
			final String byteString) {

		Byte value = null;
		try {
			value = (byte) Integer.parseInt(byteString, 2);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static byte tryParsePositiveByte(
			final String byteString) {

		byte value = -1;
		try {
			value = Byte.parseByte(byteString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static Short tryParseShort(
			final String shortString) {

		Short value = null;
		try {
			value = Short.parseShort(shortString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static Integer tryParseInt(
			final String intString) {

		Integer value = null;
		try {
			value = Integer.parseInt(intString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static int tryParsePositiveInt(
			final String intString) {

		int value = -1;
		try {
			value = Integer.parseInt(intString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static int tryParsePositiveIntFromHexString(
			final String hexStringParam) {

		int value = -1;
		try {
			String hexString = hexStringParam;
			hexString = hexString.substring(2);
			value = Integer.parseInt(hexString, 16);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static int tryParsePositiveIntHexOrDec(
			final String string) {

		final int value;
		if (StringUtils.startsWith(string, "0x")) {
			value = StrUtils.tryParsePositiveIntFromHexString(string);
		} else {
			value = StrUtils.tryParsePositiveInt(string);
		}
		return value;
	}

	@ApiMethod
	public static Long tryParseLong(
			final String longString) {

		Long value = null;
		try {
			value = Long.parseLong(longString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static long tryParsePositiveLong(
			final String longString) {

		long value = -1;
		try {
			value = Long.parseLong(longString);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static long tryParsePositiveLongFromHexString(
			final String hexStringParam) {

		long value = -1;
		try {
			String hexString = hexStringParam;
			hexString = hexString.substring(2);
			value = Long.parseLong(hexString, 16);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static long tryParsePositiveLongFromHexStringWoPrefix(
			final String hexString) {

		long value = -1;
		try {
			value = Long.parseLong(hexString, 16);
		} catch (final Exception ignored) {
		}
		return value;
	}

	@ApiMethod
	public static long tryParsePositiveLongHexOrDec(
			final String string) {

		final long value;
		if (StringUtils.startsWith(string, "0x")) {
			value = StrUtils.tryParsePositiveLongFromHexString(string);
		} else {
			value = StrUtils.tryParsePositiveLong(string);
		}
		return value;
	}

	@ApiMethod
	public static double tryParseDouble(
			final String doubleString,
			final double defaultValue) {

		double result = defaultValue;
		try {
			result = Double.parseDouble(doubleString);
		} catch (final Exception ignored) {
		}
		return result;
	}

	@ApiMethod
	public static double tryParseDouble(
			final String doubleString,
			final double defaultValue,
			final double limit) {

		double d = tryParseDouble(doubleString, defaultValue);
		if (Double.isNaN(d) || Math.abs(d) > Math.abs(limit)) {
			d = defaultValue;
		}
		return d;
	}

	@ApiMethod
	public static byte[] tryParseByteArrayFromHexString(
			final String str) {

		byte[] byteArray;
		try {
			final String strNoSpaces = StringUtils.remove(str, ' ');
			final int length = strNoSpaces.length();
			final byte[] data = new byte[length / 2];
			for (int i = 0; i < length; i += 2) {

				data[i / 2] = (byte) ((Character.digit(strNoSpaces.charAt(i), 16) << 4) +
						Character.digit(strNoSpaces.charAt(i + 1), 16));
			}
			byteArray = data;

		} catch (final Exception ignored) {
			byteArray = new byte[] {};
		}
		return byteArray;
	}

	@ApiMethod
	public static byte[] tryParseByteArrayFromBinaryString(
			final String str) {

		byte[] byteArray;
		try {
			final String strNoSpaces = StringUtils.remove(str, ' ');
			final int length = strNoSpaces.length();
			final byte[] data = new byte[length / 8];
			for (int i = 0; i < length; i += 8) {

				final String byteString = strNoSpaces.substring(i, i + 8);
				data[i / 8] = StrUtils.tryParseByteFromBinaryString(byteString);
			}
			byteArray = data;

		} catch (final Exception ignored) {
			byteArray = new byte[] {};
		}
		return byteArray;
	}

	@ApiMethod
	public static String stackTraceToString(
			final StackTraceElement[] stackTraceElementArray) {

		final StringBuilder sbStackTrace = new StringBuilder();
		for (int i = 0; i < stackTraceElementArray.length; i++) {

			final StackTraceElement stackTraceElement = stackTraceElementArray[i];
			sbStackTrace.append(stackTraceElement);
			if (i < stackTraceElementArray.length - 1) {
				sbStackTrace.append(System.lineSeparator());
			}
		}
		return sbStackTrace.toString();
	}
}
