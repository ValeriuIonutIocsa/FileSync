package com.utils.net;

import java.net.DatagramSocket;
import java.net.InetAddress;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

public final class FactoryLocalHostIpAddress {

	private static final String LOOP_BACK_ADDRESS = "8.8.8.8";

	private FactoryLocalHostIpAddress() {
	}

	@ApiMethod
	public static LocalHostIpAddress newInstance() {

		LocalHostIpAddress localHostIpAddress = null;
		try {
			try (DatagramSocket datagramSocket = new DatagramSocket()) {

				final InetAddress loopBackInetAddress = InetAddress.getByName(LOOP_BACK_ADDRESS);
				datagramSocket.connect(loopBackInetAddress, 1);
				final InetAddress localAddress = datagramSocket.getLocalAddress();
				final String ipAddress = localAddress.getHostAddress();
				if (ipAddress != null) {
					localHostIpAddress = new LocalHostIpAddress(ipAddress);
				}
			}

		} catch (final Exception exc) {
			Logger.printError("failed to create local host IP address");
			Logger.printException(exc);
		}
		return localHostIpAddress;
	}
}
