package com.utils.crypt;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.io.PathUtils;
import com.utils.io.ReaderUtils;
import com.utils.io.StreamUtils;
import com.utils.log.Logger;

class EncryptionUtilsTest {

	@Test
	void testEncryptAndDecrypt() throws Exception {

		final String originalString = "how to do in java.com";

		final String encryptedFilePathString =
				PathUtils.computePath(PathUtils.createRootPath(), "tmp", "test.encrypted");
		Logger.printProgress("generating encrypted file:");
		Logger.printLine(encryptedFilePathString);

		final Cipher encryptCipher = EncryptionUtils.createEncryptCipher();
		try (OutputStream outputStream = new CipherOutputStream(
				StreamUtils.openBufferedOutputStream(encryptedFilePathString), encryptCipher)) {
			outputStream.write(originalString.getBytes(StandardCharsets.UTF_8));
		}

		final String decryptedString;
		final Cipher decryptCipher = EncryptionUtils.createDecryptCipher();
		try (InputStream inputStream = new CipherInputStream(
				StreamUtils.openBufferedInputStream(encryptedFilePathString), decryptCipher)) {
			decryptedString = ReaderUtils.inputStreamToString(inputStream, StandardCharsets.UTF_8.name());
		}

		Assertions.assertEquals(originalString, decryptedString);
	}

	@Test
	void testEncryptAndDecryptPropertiesFile() throws Exception {

		final Properties properties = new Properties();
		final String key = "key111";
		final String value = "value111";
		properties.put(key, value);

		final String encryptedFilePathString =
				PathUtils.computePath(PathUtils.createRootPath(), "tmp", "test_properties.encrypted");
		Logger.printProgress("generating encrypted file:");
		Logger.printLine(encryptedFilePathString);

		final Cipher encryptCipher = EncryptionUtils.createEncryptCipher();
		try (OutputStream outputStream = new CipherOutputStream(
				StreamUtils.openOutputStream(encryptedFilePathString), encryptCipher)) {
			properties.store(outputStream, "test properties file for encryption");
		}

		final Properties decryptedProperties = new Properties();
		final Cipher decryptCipher = EncryptionUtils.createDecryptCipher();
		try (InputStream inputStream = new CipherInputStream(
				StreamUtils.openInputStream(encryptedFilePathString), decryptCipher)) {
			decryptedProperties.load(inputStream);
		}

		final String decryptedValue = decryptedProperties.getProperty(key);
		Assertions.assertEquals(value, decryptedValue);
	}
}
