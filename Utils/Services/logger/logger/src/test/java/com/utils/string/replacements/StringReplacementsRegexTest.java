package com.utils.string.replacements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringReplacementsRegexTest {

	@Test
	void testPerformReplacements() {

		final String str = "abc@orig1@bcd@origa@cdb@orig2@xyz";
		final String expectedStr = "abc_NUM_bcd_CHAR_cdb_NUM_xyz";

		final StringReplacements stringReplacements = new StringReplacementsRegex();
		stringReplacements.addReplacement("@orig[0-9]@", "_NUM_");
		stringReplacements.addReplacement("@orig[a-z]@", "_CHAR_");

		final String outStr = stringReplacements.performReplacements(str);
		Assertions.assertEquals(expectedStr, outStr);
	}
}
