package com.platform.util;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.time.DateUtils;

import com.platform.configuration.PlatformConfiguration;

public class FileUtil {

	private static final String ROOT_TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
	
	static {
		File file = new File(getTempDirectory());
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * @see This method needs to be called everytime after a temp file/Dir is
	 *      created in order keep the storage optimized.
	 * @param file to be deleted
	 * @return true if successfully removed.
	 */
	public static boolean deleteDirectoryOrFile(File dir) {
		if (dir != null) {
			if (dir.isDirectory()) {
				File[] children = dir.listFiles();
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteDirectoryOrFile(children[i]);
					if (!success) {
						return false;
					}
				}
			}
			Log.platform.warn("Removing Dir - " + dir.getPath());
			return dir.delete();
		}
		return false;
	}

	/**
	 * Cleanup tmp directory contents older than 24 hours
	 */
	public static void cleanUpTempDirectory() {
		Date dateToDelete = DateUtils.addDays(new Date(), -1);
		File tempDirectory = new File(FileUtil.getTempDirectory());
		if (!tempDirectory.exists()) {
			tempDirectory.mkdirs();
		}
		Iterator<File> filesToDelete = FileUtils.iterateFiles(tempDirectory, new AgeFileFilter(dateToDelete),
				TrueFileFilter.INSTANCE);
		while (filesToDelete.hasNext()) {
			File file = filesToDelete.next();
			Log.platform.warn("Deleting obsolete temp file : {}", file.getPath());
			FileUtils.deleteQuietly(file);
		}
	}

	public static String getFileExtensionFromName(String fileName) {
		return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
	}

	public static String getFileExtension(String fileName) {
		return "." + FilenameUtils.getExtension(fileName);
	}

	public static String getFileName(String fileName) {
		return FilenameUtils.getBaseName(fileName);
	}

	public static String getFileBaseName(File file) {
		return FilenameUtils.getBaseName(file.getName());
	}

	public static File createCloneTempFile(File file) throws IOException {
		return File.createTempFile(getFileBaseName(file), getFileExtension(file.getName()));
	}

	public static File createTempFile(String prefix, String suffix) throws IOException {
		return new File(FileUtil.getTempDirectory() + File.separator + prefix + System.nanoTime()
				+ suffix);
	}

	public static File createTempFile(String prefix, String suffix, File directory) throws IOException {
		return new File(directory.getPath() + prefix + System.nanoTime() + suffix);
	}

	public static File createTempFile() throws IOException {
		return new File(FileUtil.getTempDirectory() + File.separator + System.nanoTime() + ".tmp");
	}

	public static String getTempDirectory() {
		return ROOT_TEMP_DIRECTORY + File.separator + "PLATFORM" + File.separator;
	}

	public static String getTempDirectoryForTenant() {
		return ROOT_TEMP_DIRECTORY + File.separator + PlatformConfiguration.getAppName() + File.separator;
	}

	public static File crreateFileinTempDirectory(String fileNamewithExtension) {
		return new File(FileUtil.getTempDirectory() + getFileName(fileNamewithExtension)
				+ getFileExtension(fileNamewithExtension));
	}

	public static String sanitizeDirPath(String dir) {
		StringBuffer buffer = new StringBuffer();
		if (dir.charAt(0) != File.separator.charAt(0)) {
			buffer.append(File.separator);
		}
		buffer.append(dir);
		if (dir.charAt(dir.length() - 1) != File.separator.charAt(0)) {
			buffer.append(File.separator);
		}
		return buffer.toString();
	}

	public static String findContentTypeFromFileName(String fileName) {
		return URLConnection.guessContentTypeFromName(fileName);
	}

	public static void moveFile(String sourcePath, String destPath) throws IOException {
		try {
			Files.move(Paths.get(sourcePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
			Log.platform.info("File moved from {} to {}", sourcePath, destPath);
		} catch (IOException e) {
			Log.platform.error("Exception moving file from {} to {} : {}", sourcePath, destPath, e);
			throw e;
		}
	}

}
