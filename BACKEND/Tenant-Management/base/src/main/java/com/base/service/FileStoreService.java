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
import com.google.cloud.storage.BlobId;
import com.platform.messages.StoreType;
import com.platform.service.StorageService;
import com.platform.util.FileUtil;

/**
 * @author Muhil
 */
@Service
public class FileStoreService {

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

	public FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted) throws IOException {
		return uploadToFileStore(type, file, aclRestricted, null);
	}
	
	public FileStore uploadToFileStore(File file, boolean aclRestricted) throws IOException {
		return uploadToFileStore(StorageService.defaultStore(), file, aclRestricted, null);
	}

	public FileStore uploadToFileStore(File file, boolean aclRestricted, String directory) throws IOException {
		return uploadToFileStore(StorageService.defaultStore(), file, aclRestricted, directory);
	}
	
	public FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted, String directory) throws IOException {
		FileStore fs = new FileStore();
		switch (type) {
		case GCP:
			fs.setBlobInfo(StorageService.getStorage(type).saveFile(file, directory, aclRestricted));
			break;
		case NFS:
			fs.setMediaurl(StorageService.getStorage(type).saveFile(file, directory));
			break;
		default:
			throw new UnsupportedOperationException();
		}
		fs.setStoretype(type.name());
		fs.setFileName(file.getName());
		fs.setFileExtention(FileUtil.getFileExtensionFromName(file.getName()));
		fileStoreRepo.save(fs);
		return fs;
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
		return fileStoreRepo.save(fileStore);
	}

}
