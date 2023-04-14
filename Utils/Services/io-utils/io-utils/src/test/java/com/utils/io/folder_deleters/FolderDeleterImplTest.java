package com.utils.io.folder_deleters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.io.IoUtils;
import com.utils.io.PathUtils;

class FolderDeleterImplTest {

	@Test
	void testDeleteFolder() {

		String folderPathString = "null";
		folderPathString = PathUtils.computePath(folderPathString);
		Assertions.assertTrue(IoUtils.directoryExists(folderPathString));
		new FolderDeleterImpl().deleteFolder(folderPathString, true);
		Assertions.assertFalse(IoUtils.directoryExists(folderPathString));
	}
}
