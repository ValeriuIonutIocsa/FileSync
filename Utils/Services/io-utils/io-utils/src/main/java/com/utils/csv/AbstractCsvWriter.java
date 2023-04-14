package com.utils.csv;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;
import com.utils.io.StreamUtils;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.io.ro_flag_clearers.FactoryReadOnlyFlagClearer;
import com.utils.log.Logger;

public abstract class AbstractCsvWriter implements CsvWriter {

	private final String name;
	private final String outputPathString;

	protected AbstractCsvWriter(
			final String name,
			final String outputPathString) {

		this.name = name;
		this.outputPathString = outputPathString;
	}

	@Override
	@ApiMethod
	public boolean writeCsv() {

		boolean success = false;
		if (StringUtils.isNotBlank(name)) {

			Logger.printProgress("writing " + name + ":");
			Logger.printLine(outputPathString);
		}

		FactoryFolderCreator.getInstance().createParentDirectories(outputPathString, false, true);
		FactoryReadOnlyFlagClearer.getInstance().clearReadOnlyFlagFile(outputPathString, false, true);
		try (PrintStream printStream = StreamUtils.openPrintStream(
				outputPathString, false, StandardCharsets.UTF_8)) {

			printStream.println("\"sep=,\"");
			write(printStream);

			success = true;

		} catch (final Exception exc) {
			if (StringUtils.isNotBlank(name)) {
				Logger.printError("failed to write " + name);
				Logger.printException(exc);
			}
		}
		return success;
	}

	protected abstract void write(
			PrintStream printStream);
}
