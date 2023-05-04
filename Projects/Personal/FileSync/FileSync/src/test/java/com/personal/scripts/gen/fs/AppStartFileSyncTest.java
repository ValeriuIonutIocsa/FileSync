package com.personal.scripts.gen.fs;

import org.junit.jupiter.api.Test;

class AppStartFileSyncTest {

	@Test
	void testMain() {

		final String[] args;
		final int input = Integer.parseInt("2");
		if (input == 1) {
			args = new String[] { "D:\\tmp\\FileSync\\FileSyncSettings.xml" };
		} else if (input == 2) {
			args = new String[] { "C:\\IVI\\Apps\\FileSync\\FileSyncSettings_PcToExt_IVI.xml" };

		} else if (input == 101) {
			args = new String[] { "" };
		} else if (input == 102) {
			args = new String[] { "-help" };

		} else {
			throw new RuntimeException();
		}

		AppStartFileSync.main(args);
	}
}
