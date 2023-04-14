package com.utils.obj;

public final class ObjUtils {

	private ObjUtils() {
	}

	/**
	 * similar to Objects.requireNonNullElse but skips the check if the default object is null or not
	 * 
	 * @param obj
	 *            the object that can be null or not
	 * @param defaultObj
	 *            default value to be returned if obj is null
	 * @param <T>
	 *            type of obj
	 * @return defaultObj if obj is null, obj otherwise
	 */
	public static <
			T> T nonNullElse(
					final T obj,
					final T defaultObj) {

		final T result;
		if (obj != null) {
			result = obj;
		} else {
			result = defaultObj;
		}
		return result;
	}
}
