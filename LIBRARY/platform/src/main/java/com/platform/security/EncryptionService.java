package com.platform.security;

import java.io.File;
import java.io.IOException;

import com.platform.exception.CryptoException;
import com.platform.exception.EncryptionException;
import com.platform.util.EncryptionUtil;
import com.platform.util.FileCryptoUtil;
import com.platform.util.Log;
import com.platform.util.PlatformPropertiesUtil;

/**
 * @author Muhil
 *
 */
public class EncryptionService {

	private static EncryptionService instance;

	private String fileKey;

	private EncryptionService() throws Exception {
		this.fileKey = PlatformPropertiesUtil.getFileSecret();
	}

	public static EncryptionService getInstance() {
		if (instance == null) {
			synchronized (EncryptionService.class) {
				try {
					instance = new EncryptionService();
				} catch (Exception e) {
					Log.platform.error("Failed to instantiate Encryption Service!");
				}
			}
		}
		return instance;
	}

	public File encryptFile(File inputFile) throws CryptoException, IOException {
		return FileCryptoUtil.encrypt(fileKey, inputFile);
	}

	public File decrypFile(File inputFile) throws CryptoException, IOException {
		return FileCryptoUtil.decrypt(fileKey, inputFile);
	}

	public String encryptField(String value) throws EncryptionException {
		return EncryptionUtil.encrypt_AES(value);
	}

	public String decryptField(String encryptedValue) throws EncryptionException {
		return EncryptionUtil.decrypt_AES(encryptedValue);
	}

}
