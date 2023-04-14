package com.utils.net.proxy.url_conn;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public interface UrlConnectionOpener {

	void configureProperties();

	URLConnection openURLConnection(
			URL url) throws IOException;
}
