package com.utils.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class FactoryLocalHostLocalIpAddressDataTest {

	@Test
	void testNewInstance() {

		final LocalHostIpAddress localHostIpAddress = FactoryLocalHostIpAddress.newInstance();
		Assertions.assertNotNull(localHostIpAddress);

		Logger.printNewLine();
		Logger.printLine(localHostIpAddress);
	}
}
