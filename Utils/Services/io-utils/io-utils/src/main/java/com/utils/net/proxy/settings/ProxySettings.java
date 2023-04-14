package com.utils.net.proxy.settings;

import com.utils.string.StrUtils;

public class ProxySettings {

	private final String httpHost;
	private final int httpPort;
	private final String httpUsername;
	private final String httpPassword;

	ProxySettings(
			final String httpHost,
			final int httpPort,
			final String httpUsername,
			final String httpPassword) {

		this.httpHost = httpHost;
		this.httpPort = httpPort;
		this.httpUsername = httpUsername;
		this.httpPassword = httpPassword;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public String getHttpHost() {
		return httpHost;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public String getHttpUsername() {
		return httpUsername;
	}

	public String getHttpPassword() {
		return httpPassword;
	}
}
