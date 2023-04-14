package com.utils.net.proxy.url_conn;

import com.utils.net.proxy.settings.FactoryProxySettings;
import com.utils.net.proxy.settings.ProxySettings;

public final class FactoryUrlConnectionOpener {

	private FactoryUrlConnectionOpener() {
	}

	public static UrlConnectionOpener newInstance() {

		final UrlConnectionOpener urlConnectionOpener;
		final ProxySettings proxySettings = FactoryProxySettings.newInstance();
		if (proxySettings != null) {
			urlConnectionOpener = new UrlConnectionOpenerProxy(proxySettings);
		} else {
			urlConnectionOpener = new UrlConnectionOpenerRegular();
		}
		return urlConnectionOpener;
	}
}
