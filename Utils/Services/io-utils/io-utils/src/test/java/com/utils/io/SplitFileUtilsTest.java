package com.utils.io;

import org.junit.jupiter.api.Test;

import com.utils.string.StrUtils;

class SplitFileUtilsTest {

	@Test
	void testSplitFile() {

		final String filePathString;
		final long sizeOfChunk;
		final int input = StrUtils.tryParsePositiveInt("2");
		if (input == 1) {
			filePathString = "D:\\casdev\\td5\\da\\mda\\000\\DAMDA_000U0_000\\" +
					"_FS_DAMDA_000U0_NORMAL\\out\\code\\_dwarf\\FS_DAMDA_000U0_000.elf_DWARF.txt";
			sizeOfChunk = 400 * 1024 * 1024;

		} else if (input == 2) {
			filePathString = "D:\\VT_IVI_MISC\\Tmp\\CRO\\ProjectAnalyzer\\Outputs\\FS_DAMDA_000U0_000.txt";
			sizeOfChunk = 200 * 1024 * 1024;

		} else {
			throw new RuntimeException();
		}

		SplitFileUtils.splitFile(filePathString, sizeOfChunk);
	}
}
