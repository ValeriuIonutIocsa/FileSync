package com.utils.net.proxy.url_conn;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import com.utils.net.proxy.ProxyUtils;
import com.utils.net.proxy.settings.ProxySettings;

class UrlConnectionOpenerProxy extends AbstractUrlConnectionOpener {

	private final ProxySettings proxySettings;

	UrlConnectionOpenerProxy(
			final ProxySettings proxySettings) {

		this.proxySettings = proxySettings;
	}

	@Override
	public void configureProperties() {

		System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");

		final Authenticator authenticator = new Authenticator() {

			@Override
			public PasswordAuthentication getPasswordAuthentication() {

				final String httpUsername = proxySettings.getHttpUsername();
				final String httpPassword = proxySettings.getHttpPassword();
				return new PasswordAuthentication(httpUsername, httpPassword.toCharArray());
			}
		};
		Authenticator.setDefault(authenticator);
	}

	@Override
	public URLConnection openURLConnection(
			final URL url) throws IOException {

		final Proxy proxy = ProxyUtils.createProxy(proxySettings);
		return url.openConnection(proxy);
	}
}
