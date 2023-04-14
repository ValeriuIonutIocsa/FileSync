package com.utils.net.ip_addr;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.utils.log.Logger;

public class ParserLocalIpAddresses {

	private final List<LocalIpAddressData> localIpAddressDataList;

	public ParserLocalIpAddresses() {

		localIpAddressDataList = new ArrayList<>();
	}

	public void work() {

		try {
			Logger.printProgress("parsing PC IP addresses");

			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {

				final NetworkInterface networkInterface = networkInterfaces.nextElement();
				final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {

					final InetAddress inetAddress = inetAddresses.nextElement();
					if (inetAddress instanceof Inet4Address) {

						final boolean loopBackAddress = inetAddress.isLoopbackAddress();
						if (!loopBackAddress) {

							String ipAddress = inetAddress.toString();
							if (ipAddress.length() > 0 && ipAddress.charAt(0) == '/') {
								ipAddress = ipAddress.substring(1);
							}
							final String displayName = networkInterface.getDisplayName();
							final LocalIpAddressData localIpAddressData =
									new LocalIpAddressData(ipAddress, displayName);
							localIpAddressDataList.add(localIpAddressData);
						}
					}
				}
			}

		} catch (final Exception exc) {
			Logger.printLine("failed to parse PC IP addresses");
			Logger.printException(exc);
		}
	}

	public List<LocalIpAddressData> getLocalIpAddressDataList() {
		return localIpAddressDataList;
	}
}
