package com.utils.net;

import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;
import com.utils.string.StrUtils;

public class LocalHostIpAddress {

	private static final String RMI_SERVER_HOST_NAME_PROPERTY_NAME = "java.rmi.server.hostname";

	private final String ipAddress;

	LocalHostIpAddress(
			final String ipAddress) {

		this.ipAddress = ipAddress;
	}

	@ApiMethod
	public void setRmiHostName() {

		final String rmiServerHostName = System.getProperty(RMI_SERVER_HOST_NAME_PROPERTY_NAME);
		if (StringUtils.isBlank(rmiServerHostName)) {
			System.setProperty(RMI_SERVER_HOST_NAME_PROPERTY_NAME, ipAddress);
		}
	}

	@Override
	public String toString() {
		return StrUtils.reflectionToString(this);
	}
}
