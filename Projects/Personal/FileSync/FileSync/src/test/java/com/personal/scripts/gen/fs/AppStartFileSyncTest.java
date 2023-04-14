package com.personal.scripts.gen.fs;

import org.junit.jupiter.api.Test;

class AppStartFileSyncTest {

	@Test
	void testMain() {

		final String[] args;
		final int input = Integer.parseInt("1");
		if (input == 1) {
			args = new String[] { "D:\\tmp\\FileSync\\FileSyncSettings.xml" };

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
