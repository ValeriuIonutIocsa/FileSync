package com.utils.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import com.utils.annotations.ApiMethod;
import com.utils.log.Logger;

public final class PathUtils {

	private PathUtils() {
	}

	@ApiMethod
	public static String createRootPath() {

		final String rootPath;
		if (SystemUtils.IS_OS_WINDOWS) {
			rootPath = "D:\\";
		} else {
			rootPath = "/mnt/d";
		}
		return rootPath;
	}

	@ApiMethod
	public static String computePath(
			final String firstPathString,
			final String... otherPathStringArray) {

		return computePath(null, false, null, false, true,
				firstPathString, otherPathStringArray);
	}

	@ApiMethod
	public static String computeNamedPath(
			final String pathName,
			final String firstPathString,
			final String... otherPathStringArray) {

		return computePath(pathName, false, null, false, true,
				firstPathString, otherPathStringArray);
	}

	@ApiMethod
	public static String computeNormalizedPath(
			final String pathName,
			final String firstPathString,
			final String... otherPathStringArray) {

		return computePath(pathName, true, null, true, true,
				firstPathString, otherPathStringArray);
	}

	@ApiMethod
	public static String computeAbsolutePath(
			final String pathName,
			final String rootFolderPathString,
			final String firstPathString,
			final String... otherPathStringArray) {

		return computePath(pathName, true, rootFolderPathString, true, true,
				firstPathString, otherPathStringArray);
	}

	@ApiMethod
	public static String computePath(
			final String pathName,
			final boolean absolute,
			final String rootFolderPathString,
			final boolean normalize,
			final boolean verbose,
			final String firstPathString,
			final String... otherPathStringArray) {

		String pathString = null;
		try {
			Path path = Paths.get(firstPathString, otherPathStringArray);

			if (absolute) {

				if (rootFolderPathString == null) {
					path = path.toAbsolutePath();
				} else {
					if (!path.isAbsolute()) {
						path = Paths.get(rootFolderPathString, path.toString());
					}
				}
			}

			pathString = path.toString();

			if (normalize) {
				pathString = FilenameUtils.normalize(pathString);
			}

		} catch (final Exception exc) {

			if (verbose) {

				final StringBuilder sbErrorMessage = new StringBuilder("failed to compute ");
				if (pathName != null) {
					sbErrorMessage.append(pathName).append(' ');
				}
				sbErrorMessage.append("path:")
						.append(System.lineSeparator()).append(firstPathString);
				for (final String otherPathString : otherPathStringArray) {
					sbErrorMessage.append(System.lineSeparator()).append(otherPathString);
				}
				Logger.printError(sbErrorMessage);
				Logger.printException(exc);
			}
		}
		return pathString;
	}

	@ApiMethod
	public static String computeFileName(
			final Path path) {

		final String pathString = String.valueOf(path);
		return computeFileName(pathString);
	}

	@ApiMethod
	public static String computeFileName(
			final String pathString) {

		final Path path = Paths.get(pathString);
		final Path fileName = path.getFileName();
		return fileName.toString();
	}

	@ApiMethod
	public static String computeParentPath(
			final String pathString) {

		return computeParentPath(pathString, 1);
	}

	@ApiMethod
	public static String computeParentPath(
			final String pathString,
			final int levelsToGoUp) {

		String folderPathString = null;
		try {
			Path parentPath = Paths.get(pathString);
			for (int i = 0; i < levelsToGoUp; i++) {

				if (parentPath != null) {
					parentPath = parentPath.getParent();
				} else {
					break;
				}
			}
			if (parentPath != null) {
				folderPathString = parentPath.toString();
			}

		} catch (final Exception exc) {
			Logger.printError("failed to compute parent path string");
			Logger.printException(exc);
		}
		return folderPathString;
	}

	@ApiMethod
	public static String computeExtension(
			final Path path) {

		final String pathString = path.toString();
		return computeExtension(pathString);
	}

	@ApiMethod
	public static String computeExtension(
			final String pathString) {

		return FilenameUtils.getExtension(pathString);
	}

	@ApiMethod
	public static String computePathWoExt(
			final Path path) {

		String pathWoExt = null;
		if (path != null) {

			final String pathString = path.toString();
			pathWoExt = computePathWoExt(pathString);
		}
		return pathWoExt;
	}

	@ApiMethod
	public static String computePathWoExt(
			final String pathString) {

		return FilenameUtils.removeExtension(pathString);
	}

	@ApiMethod
	public static String computeFileNameWoExt(
			final Path path) {

		String fileNameWoExt = null;
		if (path != null) {

			final String pathString = path.toString();
			fileNameWoExt = computeFileNameWoExt(pathString);
		}
		return fileNameWoExt;
	}

	@ApiMethod
	public static String computeFileNameWoExt(
			final String pathString) {

		final String fileName = computeFileName(pathString);
		return FilenameUtils.getBaseName(fileName);
	}

	@ApiMethod
	public static String appendFileNameSuffix(
			final String pathString,
			final String suffix) {

		final String resultPathString;
		final String pathStringWoExt = PathUtils.computePathWoExt(pathString);
		final String extension = PathUtils.computeExtension(pathString);
		if (StringUtils.isNotEmpty(extension)) {
			resultPathString = pathStringWoExt + suffix + "." + extension;
		} else {
			resultPathString = pathStringWoExt + suffix;
		}
		return resultPathString;
	}

	@ApiMethod
	public static String computeRelativePath(
			final String fromPathString,
			final String toPathString) {

		String relativePathString = null;
		try {
			final Path fromPath = Paths.get(fromPathString);
			final Path toPath = Paths.get(toPathString);
			final Path relativePath = fromPath.relativize(toPath);
			relativePathString = relativePath.toString();

		} catch (final Exception exc) {
			Logger.printError("failed to compute relative path string");
			Logger.printException(exc);
		}
		return relativePathString;
	}
}
