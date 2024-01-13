package com.platform.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;

import com.platform.session.PlatformBaseSession;
import com.platform.util.FileUtil;

public class NFSStorageService implements AbstractStorage {

	private static NFSStorageService instance;
	private final String nfsPath;
	private final File defaultDirectory;

	private NFSStorageService(String nfsMountPath, String serviceName) throws IOException {
		this.nfsPath = nfsMountPath;
		this.defaultDirectory = Files.createDirectories(Paths.get(nfsMountPath + File.separator + serviceName))
				.toFile();
	}

	public static void init(String nfsMountPath, String serviceName) throws IOException {
		if (instance == null) {
			synchronized (NFSStorageService.class) {
				instance = new NFSStorageService(nfsMountPath, serviceName);
			}
		}
	}

	public static NFSStorageService getInstance() {
		Asserts.notNull(instance, "NFS Storage Instance is not initialised");
		return instance;
	}

	@Override
	public File readFile(Optional<?> filePath) throws IOException {
		if (!StringUtils.isAllBlank(filePath.get().toString())) {
			File srcFile = new File(filePath.get().toString());
			File tempFile = FileUtil.createTempFile(FileUtil.getFileName(srcFile.getName()),
					FileUtil.getFileExtension(srcFile.getName()));
			FileUtils.copyFile(srcFile, tempFile);
			return tempFile;
		}
		return null;
	}

	@Override
	public String saveFile(File file) throws IOException {
		File destFile = new File(defaultDirectory,
				PlatformBaseSession.getTenantUniqueName() + File.separator + file.getName());
		FileUtils.copyFile(file, destFile);
		return destFile.getPath();
	}

	@Override
	public String saveFile(File file, String dir) throws IOException {
		File destFile = new File(defaultDirectory.getPath() + File.separator + PlatformBaseSession.getTenantUniqueName()
				+ File.separator + dir, file.getName());
		FileUtils.copyFile(file, destFile);
		return destFile.getPath();
	}

	@Override
	public String saveFile(File file, boolean isInternalOnly) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String saveFile(File file, String dir, boolean isInternalOnly) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteFile(Optional<?> blobIdOrFilePath) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String getDefaultBucketKey() {
		return this.nfsPath;
	}

}
