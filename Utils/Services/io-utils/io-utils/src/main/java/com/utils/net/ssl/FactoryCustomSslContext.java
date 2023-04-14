package com.utils.net.ssl;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.utils.annotations.ApiMethod;

public final class FactoryCustomSslContext {

	public enum KeyStoreType {
		JKS, PKCS12
	}

	private FactoryCustomSslContext() {
	}

	@ApiMethod
	public static CustomSslContext newInstance(
			final KeyStoreType keyStoreType,
			final InputStream keyStream,
			final String keyStorePassword,
			final String keyPassword,
			final InputStream trustStream,
			final String trustStorePassword) throws Exception {

		final KeyStore keyStore = KeyStore.getInstance(keyStoreType.name());
		final char[] keyStorePasswordCharArray;
		if (keyStorePassword != null) {
			keyStorePasswordCharArray = keyStorePassword.toCharArray();
		} else {
			keyStorePasswordCharArray = null;
		}
		keyStore.load(keyStream, keyStorePasswordCharArray);

		final KeyManagerFactory keyFactory =
				KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		final char[] keyPasswordCharArray;
		if (keyPassword != null) {
			keyPasswordCharArray = keyPassword.toCharArray();
		} else {
			keyPasswordCharArray = null;
		}
		keyFactory.init(keyStore, keyPasswordCharArray);
		final KeyManager[] keyManagers = keyFactory.getKeyManagers();

		final KeyStore trustStore = KeyStore.getInstance(keyStoreType.name());
		final char[] trustStorePasswordCharArray;
		if (trustStorePassword != null) {
			trustStorePasswordCharArray = trustStorePassword.toCharArray();
		} else {
			trustStorePasswordCharArray = null;
		}
		trustStore.load(trustStream, trustStorePasswordCharArray);

		final TrustManagerFactory trustFactory =
				TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustFactory.init(trustStore);
		final TrustManager[] trustManagers = trustFactory.getTrustManagers();

		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(keyManagers, trustManagers, null);

		X509TrustManager x509TrustManager = null;
		if (trustManagers.length > 0) {

			final TrustManager trustManager = trustManagers[0];
			if (trustManager instanceof X509TrustManager) {
				x509TrustManager = (X509TrustManager) trustManager;
			}
		}
		if (x509TrustManager == null) {
			x509TrustManager = createDefaultX509TrustManager();
		}

		return new CustomSslContext(sslContext, x509TrustManager);
	}

	private static X509TrustManager createDefaultX509TrustManager() {

		return new X509TrustManager() {

			@Override
			public void checkClientTrusted(
					final java.security.cert.X509Certificate[] chain,
					final String authType) {
			}

			@Override
			public void checkServerTrusted(
					final java.security.cert.X509Certificate[] chain,
					final String authType) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}
		};
	}
}
