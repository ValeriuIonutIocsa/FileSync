package com.utils.hash;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.log.Logger;
import com.utils.string.StrUtils;

class HashUtilsTest {

	@Test
	public void testCreateHashAlgorithmList() {

		final List<String> hashAlgorithmList = HashUtils.createHashAlgorithmList();

		Logger.printNewLine();
		Logger.printLine("hash algorithms:");
		for (final String hashAlgorithm : hashAlgorithmList) {
			Logger.printLine(hashAlgorithm);
		}
	}

	@Test
	public void testComputeFileHash() {

		final String filePathString;
		final String algorithm;
		final String expectedHash;
		final int input = StrUtils.tryParsePositiveInt("2");
		if (input == 1) {

			filePathString = "D:\\p\\0g\\0a3\\911\\0g0a3_0u0_911\\work\\3R\\t1\\lib\\libt1base.a";
			algorithm = "SHA-256";
			expectedHash = "239BA426BC125B8E28C12AA4CB9E09DBAB33F3E7E760C2103DD9CC4119F0CB3E";

		} else if (input == 2) {
			filePathString = "D:\\casdev\\td5\\bm\\g12\\lb4\\BMG12_0U0_LB4_022\\" +
					"_FS_BMG12_0U0_NORMAL\\out\\code\\FS_BMG12_0U0_LB4_022.elf";
			algorithm = "SHA-256";
			expectedHash = "f133a932295037dd7fe2cd5d35bb1ca7de95324eba24104628f396430be894a1";

		} else {
			throw new RuntimeException();
		}

		Logger.printStatus("file path:");
		Logger.printLine(filePathString);

		Logger.printStatus("expected hash:");
		Logger.printLine(expectedHash);

		final String hash = HashUtils.computeFileHash(filePathString, algorithm);
		Logger.printStatus("hash:");
		Logger.printLine(hash);

		Assertions.assertEquals(expectedHash, hash);
	}
}
