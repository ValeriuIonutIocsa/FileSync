package com.utils.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

import com.utils.log.Logger;

public final class ListFileUtils {

	private ListFileUtils() {
	}

	public static void visitFiles(
			final String rootDirPathString,
			final Consumer<Path> visitDirectoryConsumer,
			final Consumer<Path> visitFileConsumer) {

		try {
			final Path rootDirPath = Paths.get(rootDirPathString);
			Files.walkFileTree(rootDirPath, new SimpleFileVisitor<>() {

				@Override
				public FileVisitResult preVisitDirectory(
						final Path dir,
						final BasicFileAttributes attrs) {

					final FileVisitResult fileVisitResult;
					if (rootDirPath.equals(dir)) {
						fileVisitResult = FileVisitResult.CONTINUE;
					} else {
						visitDirectoryConsumer.accept(dir);
						fileVisitResult = FileVisitResult.SKIP_SUBTREE;
					}
					return fileVisitResult;
				}

				@Override
				public FileVisitResult visitFile(
						final Path file,
						final BasicFileAttributes attrs) {

					visitFileConsumer.accept(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(
						final Path dir,
						final IOException exc) {

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(
						final Path file,
						final IOException exc) {

					Logger.printWarning("cannot visit \"" + file + "\" due to " +
							exc.getClass().getSimpleName());
					return FileVisitResult.CONTINUE;
				}
			});

		} catch (final Exception exc) {
			Logger.printError("failed to visit files recursively of directory:" +
					System.lineSeparator() + rootDirPathString);
			Logger.printException(exc);
		}
	}

	public static void visitFilesRecursively(
			final String rootDirPathString,
			final Consumer<Path> visitDirectoryConsumer,
			final Consumer<Path> visitFileConsumer) {

		try {
			final Path rootDirPath = Paths.get(rootDirPathString);
			Files.walkFileTree(rootDirPath, new SimpleFileVisitor<>() {

				@Override
				public FileVisitResult preVisitDirectory(
						final Path dir,
						final BasicFileAttributes attrs) {

					if (!rootDirPath.equals(dir)) {
						visitDirectoryConsumer.accept(dir);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						final Path file,
						final BasicFileAttributes attrs) {

					visitFileConsumer.accept(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(
						final Path dir,
						final IOException exc) {

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(
						final Path file,
						final IOException exc) {

					Logger.printWarning("cannot visit \"" + file + "\" due to " +
							exc.getClass().getSimpleName());
					return FileVisitResult.CONTINUE;
				}
			});

		} catch (final Exception exc) {
			Logger.printError("failed to visit files recursively of directory:" +
					System.lineSeparator() + rootDirPathString);
			Logger.printException(exc);
		}
	}
}
