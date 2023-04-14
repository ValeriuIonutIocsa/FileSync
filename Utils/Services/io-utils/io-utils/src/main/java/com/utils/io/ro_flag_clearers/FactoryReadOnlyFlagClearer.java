package com.utils.io.ro_flag_clearers;

public final class FactoryReadOnlyFlagClearer {

	private static ReadOnlyFlagClearer instance = new ReadOnlyFlagClearerImpl();

	private FactoryReadOnlyFlagClearer() {
	}

	public static ReadOnlyFlagClearer getInstance() {
		return instance;
	}

	public static void setInstance(
			final ReadOnlyFlagClearer instance) {
		FactoryReadOnlyFlagClearer.instance = instance;
	}
}
