package com.utils.net.proxy.url_conn;

import com.utils.string.StrUtils;

public abstract class AbstractUrlConnectionOpener implements UrlConnectionOpener {

	AbstractUrlConnectionOpener() {
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
