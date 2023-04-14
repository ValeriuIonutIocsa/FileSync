package com.utils.net.proxy.url_conn;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

class UrlConnectionOpenerRegular extends AbstractUrlConnectionOpener {

	UrlConnectionOpenerRegular() {
	}

	@Override
	public void configureProperties() {
	}

	@Override
	public URLConnection openURLConnection(
			final URL url) throws IOException {
		return url.openConnection();
	}
}
