package com.utils.string.replacements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringReplacementsRegularTest {

	@Test
	void testPerformReplacements() {

		final String str = "abc@orig1@bcd@orig2@cdb@orig1@xyz";
		final String expectedStr = "abc1bcd2cdb1xyz";

		final StringReplacements stringReplacements = new StringReplacementsRegular();
		stringReplacements.addReplacement("@orig1@", "1");
		stringReplacements.addReplacement("@orig2@", "2");

		final String outStr = stringReplacements.performReplacements(str);
		Assertions.assertEquals(expectedStr, outStr);
	}
}
