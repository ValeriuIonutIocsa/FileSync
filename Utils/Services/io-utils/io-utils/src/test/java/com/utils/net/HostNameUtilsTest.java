package com.utils.net;

import org.junit.jupiter.api.Test;

import com.utils.log.Logger;

class HostNameUtilsTest {

	@Test
	void testFindHostName() {

		final String hostName = HostNameUtils.findHostName();
		Logger.printLine(hostName);
	}
}
