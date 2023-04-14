package com.utils.net.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

import com.utils.annotations.ApiMethod;
import com.utils.net.proxy.settings.ProxySettings;

public final class ProxyUtils {

	private ProxyUtils() {
	}

	@ApiMethod
	public static Proxy createProxy(
			final ProxySettings proxySettings) {

		final String httpHost = proxySettings.getHttpHost();
		final int httpPort = proxySettings.getHttpPort();
		final SocketAddress socketAddress = new InetSocketAddress(httpHost, httpPort);
		return new Proxy(Proxy.Type.HTTP, socketAddress);
	}
}
