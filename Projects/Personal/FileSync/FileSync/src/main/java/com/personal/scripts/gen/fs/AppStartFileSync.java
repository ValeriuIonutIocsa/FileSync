package com.personal.scripts.gen.fs;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.personal.scripts.gen.fs.settings.FactoryFileSyncSettings;
import com.personal.scripts.gen.fs.settings.FileSyncSettings;
import com.personal.scripts.gen.fs.settings.FolderPair;
import com.utils.io.PathUtils;
import com.utils.log.Logger;

final class AppStartFileSync {

	private AppStartFileSync() {
	}

	public static void main(
			final String[] args) {

		final Instant start = Instant.now();

		Logger.setDebugMode(true);

		Logger.printProgress("starting FileSync");

		if (args.length >= 1 && "-help".equals(args[0])) {

			final String helpMessage = createHelpMessage();
			Logger.printLine(helpMessage);
			System.exit(0);
		}

		final List<FolderPair> folderPairList;
		if (args.length >= 1 && "CLI".equals(args[0])) {

			if (args.length < 3) {

				final String helpMessage = createHelpMessage();
				Logger.printError("insufficient arguments" + System.lineSeparator() + helpMessage);
				System.exit(1);
			}

			final String srcFolderPathString =
					PathUtils.computeNormalizedPath("src folder", args[1]);
			Logger.printProgress("src folder path:");
			Logger.printLine(srcFolderPathString);

			final String dstFolderPathString =
					PathUtils.computeNormalizedPath("dst folder", args[2]);
			Logger.printProgress("dst folder path:");
			Logger.printLine(dstFolderPathString);

			final FolderPair folderPair =
					new FolderPair(srcFolderPathString, dstFolderPathString);

			folderPairList = new ArrayList<>();
			folderPairList.add(folderPair);

		} else {
			if (args.length < 1) {

				final String helpMessage = createHelpMessage();
				Logger.printError("insufficient arguments" + System.lineSeparator() + helpMessage);
				System.exit(1);
			}

			final String settingsFilePathString = args[0];
			Logger.printProgress("settings file path:");
			Logger.printLine(settingsFilePathString);

			final FileSyncSettings fileSyncSettings =
					FactoryFileSyncSettings.newInstance(settingsFilePathString);
			if (fileSyncSettings == null) {
				System.exit(2);
			}

			folderPairList = fileSyncSettings.getFolderPairList();
		}

		new SyncFoldersWorker().work(folderPairList);

		Logger.printFinishMessage(start);
	}

	private static String createHelpMessage() {

		return "usage: file_sync <settings_file_path>" + System.lineSeparator() +
				"file_sync CLI <src_folder_path> <dst_folder_path>";
	}
}
