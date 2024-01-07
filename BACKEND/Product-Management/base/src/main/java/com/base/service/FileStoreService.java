package com.base.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.entity.FileStore;
import com.base.jpa.repository.FileStoreRepository;
import com.base.server.BaseSession;
import com.google.cloud.storage.BlobId;
import com.platform.messages.StoreType;
import com.platform.service.StorageService;
import com.platform.util.FileUtil;

/**
 * @author Muhil
 */
@Service
public class FileStoreService {
	
	private static String CLIENT_FILESTORE_PATH = "/FILE_EXPLORER";

	@Autowired
	private FileStoreRepository fileStoreRepo;

	public File getFileById(Long id) throws IOException {
		Optional<FileStore> fs = fileStoreRepo.findById(id);
		if (fs.isPresent()) {
			if (fs.get().getStoretype().equals(StoreType.NFS.name())) {
				return StorageService.getStorage(StoreType.NFS).readFile(Optional.of(fs.get().getMediaurl()));
			} else if (fs.get().getStoretype().equals(StoreType.GCP.name())) {
				return StorageService.getStorage(StoreType.GCP).readFile(Optional.of(fs.get().getBlobInfo()));
			}
		}
		throw new UnsupportedOperationException();
	}

	public Optional<FileStore> getFileStoreById(Long id) {
		return fileStoreRepo.findById(id);
	}
	
	public List<FileStore> getAllFilesStored() {
		return fileStoreRepo.findAll();
	}
	
	public Page<FileStore> getAllFilesStored(Pageable pageable) {
		return fileStoreRepo.findAll(pageable);
	}
	
	public Page<FileStore> getAllClientUploadedFilesStored(Pageable pageable) {
		return fileStoreRepo.findAllClientFiles(BaseSession.getUser().getObjectId(), pageable);
	}
	
	public FileStore uploadClientFileToFileStore(File file, boolean aclRestricted) throws IOException {
		return uploadToFileStore(StorageService.defaultStore(), file, aclRestricted, CLIENT_FILESTORE_PATH, true);
	}
	
	public Long getTotalSizeUtilizedByUser() throws IOException {
		return fileStoreRepo.findTotalFileSize(BaseSession.getUser().getObjectId());
	}

	public FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted) throws IOException {
		return uploadToFileStore(type, file, aclRestricted, null, false);
	}
	
	public FileStore uploadToFileStore(File file, boolean aclRestricted) throws IOException {
		return uploadToFileStore(StorageService.defaultStore(), file, aclRestricted, null, false);
	}

	public FileStore uploadToFileStore(File file, boolean aclRestricted, String directory) throws IOException {
		return uploadToFileStore(StorageService.defaultStore(), file, aclRestricted, directory, false);
	}
	
	public FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted, String directory)
			throws IOException {
		return uploadToFileStore(type, file, aclRestricted, directory, false);
	}

	public FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted, String directory,
			boolean clientFile) throws IOException {
		FileStore fs = new FileStore();
		switch (type) {
		case GCP:
			BlobId blobId = (BlobId) StorageService.getStorage(type).saveFile(file, directory, aclRestricted);
			fs.setBlobInfo(blobId);
			fs.setMediaurl(StorageService.getStorage(type).getFileUrl(Optional.of(blobId)));
			break;
		case NFS:
			fs.setMediaurl(StorageService.getStorage(type).saveFile(file, directory));
			break;
		default:
			throw new UnsupportedOperationException();
		}
		fs.setAcl(aclRestricted);
		fs.setStoretype(type.name());
		fs.setFileName(file.getName());
		fs.setFileExtention(FileUtil.getFileExtensionFromName(file.getName()));
		fs.setClientfile(clientFile);
		fs.setSize(file.length());
		return fileStoreRepo.save(fs);
	}

	public FileStore updateFileStore(FileStore fileStore, File file) throws IOException {
		return updateFileStore(fileStore, file, null);
	}
	
	public FileStore updateFileStore(FileStore fileStore, File file, String directory) throws IOException {
		Optional<StoreType> type = StoreType.findType(fileStore.getStoretype());
		switch (type.get()) {
		case GCP:
			BlobId blobId = (BlobId) StorageService.getStorage(type.get()).saveFile(file, directory, fileStore.isAcl());
			fileStore.setBlobInfo(blobId);
			break;
		case NFS:
			String path = StorageService.getStorage(type.get()).saveFile(file, directory);
			fileStore.setMediaurl(path);
			break;
		default:
			throw new UnsupportedOperationException();
		}
		fileStore.setFileName(file.getName());
		fileStore.setFileExtention(FileUtil.getFileExtensionFromName(file.getName()));
		fileStore.setSize(file.length());
		return fileStoreRepo.save(fileStore);
	}
	
	public void deleteFile(Long fileId) throws IOException {
		FileStore fs = getFileStoreById(fileId).get();
		if (fs != null) {
			Optional<StoreType> type = StoreType.findType(fs.getStoretype());
			StorageService.getStorage(type.get()).deleteFile(Optional.of(fs.getBlobInfo()));
			fileStoreRepo.delete(fs);
		}
	}

}
