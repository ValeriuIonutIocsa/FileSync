package com.utils.crypt;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.utils.log.Logger;

public final class EncryptionUtils {

	private static final String SECRET_KEY = "boooooooooom!!!!";
	private static final String SALT = "ssshhhhhhhhhhh!!!!";
	private static final byte[] IV = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private EncryptionUtils() {
	}

	public static byte[] encrypt(
			final byte[] byteArray) {

		byte[] encryptedByteArray = null;
		try {
			final Cipher cipher = createEncryptCipher();
			if (cipher != null) {
				encryptedByteArray = cipher.doFinal(byteArray);
			}

		} catch (final Exception exc) {
			Logger.printError("failed to encrypt");
			Logger.printException(exc);
		}
		return encryptedByteArray;
	}

	public static byte[] decrypt(
			final byte[] encryptedByteArray) {

		byte[] decryptedByteArray = null;
		try {
			final Cipher cipher = createDecryptCipher();
			if (cipher != null) {
				decryptedByteArray = cipher.doFinal(encryptedByteArray);
			}

		} catch (final Exception exc) {
			Logger.printError("failed to decrypt");
			Logger.printException(exc);
		}
		return decryptedByteArray;
	}

	public static Cipher createEncryptCipher() {
		return createCipher(Cipher.ENCRYPT_MODE);
	}

	public static Cipher createDecryptCipher() {
		return createCipher(Cipher.DECRYPT_MODE);
	}

	private static Cipher createCipher(
			final int encryptMode) {

		Cipher cipher = null;
		try {
			final IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

			final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			final KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(),
					SALT.getBytes(StandardCharsets.UTF_8), 65_536, 256);
			final SecretKey tmp = factory.generateSecret(spec);
			final SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(encryptMode, secretKey, ivParameterSpec);

		} catch (final Exception exc) {
			Logger.printError("failed to create cipher");
			Logger.printException(exc);
		}
		return cipher;
	}
}
