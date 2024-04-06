package com.personal.scripts.gen.fs;

import org.junit.jupiter.api.Test;

import com.utils.test.TestInputUtils;

class AppStartFileSyncTest {

	@Test
	void testMain() {

		final String[] args;
		final int input = TestInputUtils.parseTestInputNumber("11");
		if (input == 1) {
			args = new String[] { "D:\\tmp\\FileSync\\FileSyncSettings.xml" };
		} else if (input == 2) {
			args = new String[] { "C:\\IVI\\Apps\\FileSync\\FileSyncSettings_PcToExt_IVI.xml" };

		} else if (input == 11) {
			args = new String[] {
					"CLI",
					"D:\\IVI_MISC\\Tmp\\FileSync\\SrcFolder",
					"D:\\IVI_MISC\\Tmp\\FileSync\\DstFolder"
			};

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
