package com.utils.string.size;

import com.utils.string.StrUtils;

public final class SizeUtils {

	private SizeUtils() {
	}

	public static String humanReadableByteCountBin(
			final long size) {

		final String humanReadableByteCount;

		final long kb = 1024;
		final long mb = kb * 1024;
		final long gb = mb * 1024;
		final long tb = gb * 1024;
		final long pb = tb * 1024;
		final long eb = pb * 1024;

		if (size < kb) {
			humanReadableByteCount = sizeDoubleToString(size) + " B";
		} else if (size < mb) {
			humanReadableByteCount = sizeDoubleToString((double) size / kb) + " KB";
		} else if (size < gb) {
			humanReadableByteCount = sizeDoubleToString((double) size / mb) + " MB";
		} else if (size < tb) {
			humanReadableByteCount = sizeDoubleToString((double) size / gb) + " GB";
		} else if (size < pb) {
			humanReadableByteCount = sizeDoubleToString((double) size / tb) + " TB";
		} else if (size < eb) {
			humanReadableByteCount = sizeDoubleToString((double) size / pb) + " PB";
		} else {
			humanReadableByteCount = sizeDoubleToString((double) size / eb) + " EB";
		}
		return humanReadableByteCount;
	}

	private static String sizeDoubleToString(
			final double d) {

		return StrUtils.doubleToString(d, 0, 3, true);
	}
}
