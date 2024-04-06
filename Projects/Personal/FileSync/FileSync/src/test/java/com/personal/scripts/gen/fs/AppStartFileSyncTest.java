package com.personal.scripts.gen.fs;

import org.junit.jupiter.api.Test;

import com.utils.io.folder_copiers.FactoryFolderCopier;
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
			final String srcFolderPathString = "D:\\IVI_MISC\\Tmp\\FileSync\\SrcFolder";
			final String dstFolderPathString = "D:\\IVI_MISC\\Tmp\\FileSync\\DstFolder";

			final String origDstFolderPathString = "D:\\IVI_MISC\\Tmp\\FileSync\\DstFolder_ORIG";
			FactoryFolderCopier.getInstance().copyFolder(
					origDstFolderPathString, dstFolderPathString, true, true, true);

			args = new String[] {
					"CLI",
					srcFolderPathString,
					dstFolderPathString
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
