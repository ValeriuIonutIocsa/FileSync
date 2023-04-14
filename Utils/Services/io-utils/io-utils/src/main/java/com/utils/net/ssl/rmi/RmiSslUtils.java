package com.utils.net.ssl.rmi;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.ResourceFileUtils;
import com.utils.io.StreamUtils;
import com.utils.io.WriterUtils;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.log.Logger;

public final class RmiSslUtils {

	private RmiSslUtils() {
	}

	public static void configureSettingsResources(
			final String password,
			final String keyStoreResourceFilePath,
			final String trustStoreResourceFilePath) {

		try {
			final String keyStorePathString = copyCertificateResources(keyStoreResourceFilePath);
			final String trustStorePathString = copyCertificateResources(trustStoreResourceFilePath);

			configureSettingsCommon(password, keyStorePathString, trustStorePathString);

		} catch (final Exception exc) {
			Logger.printError("failed to configure RMI SSL settings");
			Logger.printException(exc);
		}
	}

	private static String copyCertificateResources(
			final String certificateResourceFilePath) throws Exception {

		final String certificatePathString = PathUtils.computePath(SystemUtils.USER_HOME,
				"JavaCertificates", certificateResourceFilePath);
		if (!IoUtils.fileExists(certificatePathString)) {

			FactoryFolderCreator.getInstance().createParentDirectories(certificatePathString, true);

			try (InputStream inputStream =
					ResourceFileUtils.resourceFileToInputStream(certificateResourceFilePath);
					OutputStream outputStream = StreamUtils.openOutputStream(certificatePathString)) {
				IOUtils.copy(inputStream, outputStream);
			}

		} else {
			try (InputStream inputStream =
					ResourceFileUtils.resourceFileToInputStream(certificateResourceFilePath);
					OutputStream outputStream = StreamUtils.openOutputStream(certificatePathString)) {
				IOUtils.copy(inputStream, outputStream);
			} catch (final Exception ignored) {
			}
		}
		return certificatePathString;
	}

	public static void configureSettingsByteArrays(
			final String password,
			final byte[] keyStoreByteArray,
			final String keyStoreResourceFilePath,
			final byte[] trustStoreByteArray,
			final String trustStoreResourceFilePath) {

		try {
			final String keyStorePathString =
					copyCertificateString(keyStoreByteArray, keyStoreResourceFilePath);
			final String trustStorePathString =
					copyCertificateString(trustStoreByteArray, trustStoreResourceFilePath);

			configureSettingsCommon(password, keyStorePathString, trustStorePathString);

		} catch (final Exception exc) {
			Logger.printError("failed to configure RMI SSL settings");
			Logger.printException(exc);
		}
	}

	private static String copyCertificateString(
			final byte[] certificateByteArray,
			final String certificateResourceFilePath) {

		final String certificatePathString = PathUtils.computePath(SystemUtils.USER_HOME,
				"JavaCertificates", certificateResourceFilePath);
		if (!IoUtils.fileExists(certificatePathString)) {

			FactoryFolderCreator.getInstance().createParentDirectories(certificatePathString, true);

			WriterUtils.byteArrayToFile(certificateByteArray, certificatePathString);

		} else {
			try {
				WriterUtils.byteArrayToFile(certificateByteArray, certificatePathString);
			} catch (final Exception ignored) {
			}
		}
		return certificatePathString;
	}

	public static void configureSettingsCommon(
			final String password,
			final String keyStorePathString,
			final String trustStorePathString) {

		System.setProperty("javax.net.ssl.debug", "all");

		System.setProperty("javax.net.ssl.keyStore", keyStorePathString);
		System.setProperty("javax.net.ssl.keyStorePassword", password);

		System.setProperty("javax.net.ssl.trustStore", trustStorePathString);
		System.setProperty("javax.net.ssl.trustStorePassword", password);
	}
}
