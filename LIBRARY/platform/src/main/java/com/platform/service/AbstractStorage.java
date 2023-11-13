package com.platform.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.platform.messages.StoreType;

/**
 * @author Muhil
 *
 */
public interface AbstractStorage {

	File readFile(Optional<?> blobIdOrFilePath) throws IOException;
	
	boolean deleteFile(Optional<?> blobIdOrFilePath) throws IOException;

	String saveFile(File file) throws IOException;

	String saveFile(File file, String dir) throws IOException;

	Object saveFile(File file, boolean isInternalOnly) throws IOException;

	Object saveFile(File file, String dir, boolean isInternalOnly) throws IOException;

	default String getConfigKey() {
		return null;
	};

	default String getDefaultBucketKey() {
		return null;
	};

	default void updateTenantConfig(Long tenantId, String gcpConfig, String gcpBucket) throws IOException {
		// NO-OP
	}

}
