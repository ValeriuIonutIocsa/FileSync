package com.utils.string;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

final class ReflectionToString {

	private ReflectionToString() {
	}

	static String work(
			final Object obj) {

		final StringBuilder sb = new StringBuilder();
		work(obj, new ArrayList<>(), sb);
		return sb.toString();
	}

	private static void work(
			final Object obj,
			final List<Object> objList,
			final StringBuilder sb) {

		try {
			final Class<?> cls = obj.getClass();
			appendClassInfoRec(obj, cls, objList, sb);

		} catch (final Exception ignored) {
		}
	}

	private static boolean appendClassInfoRec(
			final Object obj,
			final Class<?> cls,
			final List<Object> objList,
			final StringBuilder sb) throws Exception {

		final boolean appended;
		if (cls == null || Object.class.equals(cls)) {
			appended = false;

		} else {
			final String clsSimpleName = cls.getSimpleName();
			sb.append(clsSimpleName).append(" { ");

			final Class<?> superClass = cls.getSuperclass();
			final boolean superClassAppended =
					appendClassInfoRec(obj, superClass, objList, sb);

			final Field[] declaredFields = cls.getDeclaredFields();
			boolean first = true;
			for (final Field field : declaredFields) {

				final boolean staticField = Modifier.isStatic(field.getModifiers());
				if (!staticField) {

					final String fieldName = field.getName();
					Object fieldValue = null;
					if (obj instanceof Enum<?>) {

						if ("name".equals(fieldName)) {
							final Enum<?> objEnum = (Enum<?>) obj;
							fieldValue = objEnum.name();

						} else if ("ordinal".equals(fieldName)) {
							final Enum<?> objEnum = (Enum<?>) obj;
							fieldValue = objEnum.ordinal();
						}
					}
					if (fieldValue == null) {

						field.setAccessible(true);
						fieldValue = field.get(obj);
					}
					if (first) {

						if (superClassAppended) {
							sb.append(", ");
						}
						first = false;

					} else {
						sb.append(", ");
					}
					sb.append(fieldName).append("=\"");
					appendFieldValue(fieldValue, objList, sb);
					sb.append('"');
				}
			}

			sb.append(" }");
			appended = true;
		}
		return appended;
	}

	private static void appendFieldValue(
			final Object fieldValue,
			final List<Object> objList,
			final StringBuilder sb) {

		if (fieldValue == null) {
			sb.append("null");

		} else if (fieldValue.getClass().isArray()) {
			appendArray(fieldValue, objList, sb);

		} else if (fieldValue instanceof Collection<?>) {
			final Collection<?> collection = (Collection<?>) fieldValue;
			appendCollection(collection, objList, sb);

		} else if (fieldValue instanceof Map<?, ?>) {
			final Map<?, ?> map = (Map<?, ?>) fieldValue;
			appendMap(map, objList, sb);

		} else {
			appendObject(fieldValue, objList, sb);
		}
	}

	private static void appendArray(
			final Object array,
			final List<Object> objList,
			final StringBuilder sb) {

		final int arrayLength = Array.getLength(array);
		sb.append("Array[").append(arrayLength).append("] {");
		for (int i = 0; i < Math.min(arrayLength, 3); i++) {

			final Object arrayElement = Array.get(array, i);
			sb.append(' ');
			appendObject(arrayElement, objList, sb);
			if (i < 2) {
				sb.append(',');
			}
		}
		if (arrayLength <= 3) {
			sb.append(' ');
		} else {
			sb.append(", ... ");
		}
		sb.append('}');
	}

	private static void appendCollection(
			final Collection<?> collection,
			final List<Object> objList,
			final StringBuilder sb) {

		final int collectionSize = collection.size();
		sb.append("Collection[").append(collectionSize).append("] {");
		int i = 0;
		for (final Object collectionElement : collection) {

			if (i == 3) {
				break;
			}

			sb.append(' ');
			appendObject(collectionElement, objList, sb);
			if (i < 2) {
				sb.append(',');
			}
			i++;
		}
		if (collectionSize <= 3) {
			sb.append(' ');
		} else {
			sb.append(", ... ");
		}
		sb.append('}');
	}

	private static void appendMap(
			final Map<?, ?> map,
			final List<Object> objList,
			final StringBuilder sb) {

		final int mapSize = map.size();
		sb.append("Map[").append(mapSize).append("] {");
		int i = 0;
		for (final Map.Entry<?, ?> mapEntry : map.entrySet()) {

			if (i == 3) {
				break;
			}

			sb.append(' ');
			final Object key = mapEntry.getKey();
			appendObject(key, objList, sb);
			sb.append('=');
			final Object value = mapEntry.getValue();
			appendObject(value, objList, sb);
			if (i < 2) {
				sb.append(',');
			}
			i++;
		}
		if (mapSize <= 3) {
			sb.append(' ');
		} else {
			sb.append(", ... ");
		}
		sb.append('}');
	}

	private static void appendObject(
			final Object obj,
			final List<Object> objList,
			final StringBuilder sb) {

		final Class<?> objCls = obj.getClass();
		final boolean partOfJre = checkPartOfJre(objCls);
		if (partOfJre) {

			String str = obj.toString();
			str = StringUtils.replace(str, "\n", "\\n");
			str = StringUtils.replace(str, "\r", "\\r");
			sb.append(str);

		} else {
			final boolean visitedBefore = checkVisitedBefore(obj, objList);
			if (visitedBefore) {

				final String objClsSimpleName = objCls.getSimpleName();
				sb.append(objClsSimpleName);

			} else {
				if (sb.length() > 1_000) {

					final String objClsSimpleName = objCls.getSimpleName();
					sb.append(objClsSimpleName);

				} else {
					objList.add(obj);
					work(obj, objList, sb);
				}
			}
		}
	}

	private static boolean checkPartOfJre(
			final Class<?> cls) {

		final String clsName = cls.getName();
		return clsName.startsWith("java.") || clsName.startsWith("javax.") ||
				clsName.startsWith("com.sun.");
	}

	private static boolean checkVisitedBefore(
			final Object object,
			final List<Object> objList) {

		boolean visitedBefore = false;
		for (final Object objListObject : objList) {

			if (object == objListObject) {

				visitedBefore = true;
				break;
			}
		}
		return visitedBefore;
	}
}
