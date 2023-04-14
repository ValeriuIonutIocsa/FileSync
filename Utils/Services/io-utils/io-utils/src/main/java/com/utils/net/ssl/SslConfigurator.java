package com.utils.net.ssl;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.utils.annotations.ApiMethod;

public final class SslConfigurator {

	public enum KeyStoreType {
		JKS, PKCS12
	}

	private SslConfigurator() {
	}

	@ApiMethod
	public static void work(
			final KeyStoreType keyStoreType,
			final InputStream keyStream,
			final String keyStorePassword,
			final String keyPassword,
			final InputStream trustStream,
			final String trustStorePassword) throws Exception {

		final KeyStore keyStore = KeyStore.getInstance(keyStoreType.name());
		final char[] keyStoreCharArray;
		if (keyStorePassword != null) {
			keyStoreCharArray = keyStorePassword.toCharArray();
		} else {
			keyStoreCharArray = null;
		}
		keyStore.load(keyStream, keyStoreCharArray);

		final KeyManagerFactory keyFactory =
				KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		final char[] keyCharArray;
		if (keyPassword != null) {
			keyCharArray = keyPassword.toCharArray();
		} else {
			keyCharArray = null;
		}
		keyFactory.init(keyStore, keyCharArray);
		final KeyManager[] keyManagers = keyFactory.getKeyManagers();

		final KeyStore trustStore = KeyStore.getInstance(keyStoreType.name());
		final char[] trustStoreCharArray;
		if (trustStorePassword != null) {
			trustStoreCharArray = trustStorePassword.toCharArray();
		} else {
			trustStoreCharArray = null;
		}
		trustStore.load(trustStream, trustStoreCharArray);

		final TrustManagerFactory trustFactory =
				TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustFactory.init(trustStore);
		final TrustManager[] trustManagers = trustFactory.getTrustManagers();

		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(keyManagers, trustManagers, null);
		SSLContext.setDefault(sslContext);
	}
}
