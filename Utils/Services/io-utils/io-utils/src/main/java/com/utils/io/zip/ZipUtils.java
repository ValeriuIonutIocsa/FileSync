package com.utils.io.zip;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;

public final class ZipUtils {

	private ZipUtils() {
	}

	@ApiMethod
	public static FileSystem createNewZipFileSystem(
			final String zipFilePathString,
			final boolean useTempFile) throws Exception {

		final Map<String, Object> env = new HashMap<>();
		env.put("create", "true");
		env.put("useTempFile", useTempFile);
		return createZipFileSystem(zipFilePathString, env);
	}

	@ApiMethod
	public static FileSystem openZipFileSystem(
			final String zipFilePathString,
			final boolean useTempFile) throws Exception {

		final Map<String, Object> env = new HashMap<>();
		env.put("create", "false");
		env.put("useTempFile", useTempFile);
		return createZipFileSystem(zipFilePathString, env);
	}

	private static FileSystem createZipFileSystem(
			final String zipFilePathString,
			final Map<String, ?> env) throws Exception {

		final String archiveUriString;
		final String zipFilePathUriString = Paths.get(zipFilePathString).toUri().toString();
		if (zipFilePathUriString.startsWith("file:///")) {
			archiveUriString = "jar:" + zipFilePathUriString;
		} else {
			archiveUriString = "jar:" + StringUtils.replaceOnce(zipFilePathUriString, "file://", "file://///");
		}
		final URI archiveUri = URI.create(archiveUriString);
		return FileSystems.newFileSystem(archiveUri, env);
	}
}
