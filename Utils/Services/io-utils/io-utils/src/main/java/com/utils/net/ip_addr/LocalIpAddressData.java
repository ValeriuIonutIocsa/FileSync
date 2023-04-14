package com.utils.net.ip_addr;

import com.utils.string.StrUtils;

public class LocalIpAddressData {

	private final String ipAddress;
	private final String displayName;

	LocalIpAddressData(
			final String ipAddress,
			final String displayName) {

		this.ipAddress = ipAddress;
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getDisplayName() {
		return displayName;
	}
}
