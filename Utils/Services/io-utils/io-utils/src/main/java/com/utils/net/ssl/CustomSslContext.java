package com.utils.net.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import com.utils.string.StrUtils;

public class CustomSslContext {

	private final SSLContext sslContext;
	private final X509TrustManager x509TrustManager;

	CustomSslContext(
			final SSLContext sslContext,
			final X509TrustManager x509TrustManager) {

		this.sslContext = sslContext;
		this.x509TrustManager = x509TrustManager;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public SSLContext getSslContext() {
		return sslContext;
	}

	public X509TrustManager getX509TrustManager() {
		return x509TrustManager;
	}
}
