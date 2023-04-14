package com.utils.hash;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

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
		final int input = Integer.parseInt("2");
		if (input == 1) {

			filePathString = "D:\\p\\0g\\0a3\\911\\0g0a3_0u0_911\\work\\3R\\t1\\lib\\libt1base.a";
			algorithm = "SHA-256";
			expectedHash = "239BA426BC125B8E28C12AA4CB9E09DBAB33F3E7E760C2103DD9CC4119F0CB3E";

		} else if (input == 2) {
			filePathString = "C:\\Users\\uid39522\\OneDrive - Vitesco Technologies\\" +
					"_VALERIU_\\ProjectAnalyzer\\3_Executable\\5.0.40\\ProjectAnalyzer.jar";
			algorithm = "SHA-256";
			expectedHash = "49732733b7208191f1de3b1be1f1588e1da44dd09ff24d279d082e782a773065";

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
