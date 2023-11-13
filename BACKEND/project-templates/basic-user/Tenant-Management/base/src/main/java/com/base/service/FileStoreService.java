package com.base.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		if(fs.isPresent()) {
			if(fs.get().getStoretype().equals(StoreType.INTERNAL.name())) {
				InputStream is = getClass().getResourceAsStream(fs.get().getMediaurl());
				File tempFile = File.createTempFile(FileStore.class.getSimpleName() + fs.get().getRootId(),
						".".concat(fs.get().getFileExtention()));
			    FileUtils.copyInputStreamToFile(is, tempFile);
			    return tempFile;
			}
			else if (fs.get().getStoretype().equals(StoreType.GCP.name())) {
				return StorageService.getStorage(StoreType.GCP)
						.readFile(Optional.of(fs.get().getBlobinfo(BlobId.class)));
			}
		}
		throw new UnsupportedOperationException();
	}
	
	public FileStore uploadToFileStore(StoreType type, File file, boolean aclRestricted) throws IOException {
		FileStore fs = new FileStore();
		switch (type) {
		case GCP:
			fs.setBlobinfo(StorageService.getStorage(type).saveFile(file, aclRestricted));
			break;
		case NFS:
			fs.setMediaurl(StorageService.getStorage(type).saveFile(file));
			break;
		case INTERNAL:
		default:
			throw new UnsupportedOperationException();
		}
		fs.setStoretype(type.name());
		fs.setFileExtention(FileUtil.getFileExtensionFromName(file.getName()));
		fileStoreRepo.save(fs);
		return fs;
	}

}
