package com.utils.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.string.env.EnvProviderTest;
import com.utils.string.env.EnvUtils;

class EnvUtilsTest {

	@Test
	void testReplaceEnvironmentVariables() {

		EnvProviderTest.setEnv("ENV1", "1111");
		EnvProviderTest.setEnv("ENV2", "2222");
		EnvProviderTest.setEnv("ENV3", "3333");

		final String str;
		final String expectedOutStr;
		final int input = Integer.parseInt("1");
		if (input == 1) {
			str = "abcd%ENV1%xyz";
			expectedOutStr = "abcd1111xyz";
		} else if (input == 2) {
			str = "%ENV1%abc%ENV2%bcd%ENV3%";
			expectedOutStr = "1111abc2222bcd3333";
		} else if (input == 3) {
			str = "abc%%%ENV1%x%%y%%z";
			expectedOutStr = "abc%1111x%y%z";
		} else {
			throw new RuntimeException();
		}

		final String outStr = EnvUtils.replaceEnvironmentVariables(str);
		Assertions.assertEquals(expectedOutStr, outStr);
	}
}
