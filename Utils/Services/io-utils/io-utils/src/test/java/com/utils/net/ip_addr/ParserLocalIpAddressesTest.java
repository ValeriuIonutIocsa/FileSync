package com.utils.net.ip_addr;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class ParserLocalIpAddressesTest {

	@Test
	void testWork() {

		final ParserLocalIpAddresses parserLocalIpAddresses = new ParserLocalIpAddresses();
		parserLocalIpAddresses.work();

		final List<LocalIpAddressData> localIpAddressDataList =
				parserLocalIpAddresses.getLocalIpAddressDataList();
		Assertions.assertFalse(localIpAddressDataList.isEmpty());

		Logger.printNewLine();
		for (final LocalIpAddressData localIpAddressData : localIpAddressDataList) {
			Logger.printLine(localIpAddressData);
		}
	}
}
