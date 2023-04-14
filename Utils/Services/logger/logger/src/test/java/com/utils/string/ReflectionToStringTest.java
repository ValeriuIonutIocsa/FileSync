package com.utils.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class ReflectionToStringTest {

	@Test
	void testWork() {

		final String[] field2 = {
				"abc",
				"bcd",
				"cde",
				"efg"
		};
		final List<String> field3 = new ArrayList<>();
		field3.add("1");
		field3.add("2");
		field3.add("3");
		field3.add("4");
		field3.add("5");
		final Map<String, String> field4 = new HashMap<>();
		field4.put("k1", "v1");
		field4.put("k2", "v2");
		field4.put("k3", "v3");
		field4.put("k4", "v4");
		field4.put("k5", "v5");

		final Object obj;
		final int input = Integer.parseInt("3");
		if (input == 1) {

			obj = new TestClass1(12, field2, field3, field4);

		} else if (input == 2) {

			final TestClass3 testClass3 = new TestClass3("bcd", field3);
			obj = new TestClass2(1, field2, field3, field4, 2, testClass3, "abc");

		} else if (input == 3) {

			final TestClass4 testClass4 = new TestClass4(null);
			testClass4.testClass5 = new TestClass5(testClass4);
			obj = testClass4;

		} else {
			throw new RuntimeException();
		}

		final String str = ReflectionToString.work(obj);
		Logger.printLine(str);
	}

	@SuppressWarnings("all")
	private static class TestClass1 {

		final int field1;
		final String[] field2;
		final List<String> field3;
		final Map<String, String> field4;

		TestClass1(
				final int field1,
				final String[] field2,
				final List<String> field3,
				final Map<String, String> field4) {

			this.field1 = field1;
			this.field2 = field2;
			this.field3 = field3;
			this.field4 = field4;
		}
	}

	@SuppressWarnings("all")
	private static class TestClass2 extends TestClass1 {

		final int field5;
		final TestClass3 field6;
		final String field7;

		TestClass2(
				final int field1,
				final String[] field2,
				final List<String> field3,
				final Map<String, String> field4,
				final int field5,
				final TestClass3 field6,
				final String field7) {

			super(field1, field2, field3, field4);

			this.field5 = field5;
			this.field6 = field6;
			this.field7 = field7;
		}
	}

	@SuppressWarnings("all")
	private static class TestClass3 {

		final String field1;
		final List<String> field2;

		TestClass3(
				final String field1,
				final List<String> field2) {

			this.field1 = field1;
			this.field2 = field2;
		}
	}

	@SuppressWarnings("all")
	private static class TestClass4 {

		TestClass5 testClass5;

		TestClass4(
				final TestClass5 testClass5) {

			this.testClass5 = testClass5;
		}
	}

	@SuppressWarnings("all")
	private static class TestClass5 {

		TestClass4 testClass4;

		TestClass5(
				final TestClass4 testClass4) {

			this.testClass4 = testClass4;
		}
	}
}
