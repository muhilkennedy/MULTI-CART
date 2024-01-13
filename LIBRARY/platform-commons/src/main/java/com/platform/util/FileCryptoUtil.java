package com.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.platform.exception.CryptoException;

/**
 * @author Muhil
 * Encrytion and decryption utils
 * TODO: move to EncryptionUtil.java
 */
public class FileCryptoUtil {
	
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void encrypt(String key, File inputFile, File outputFile) throws CryptoException {
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}
	
	public static File encrypt(String key, File inputFile) throws CryptoException, IOException {
		File outputFile = File.createTempFile(FileUtil.getFileBaseName(inputFile), FileUtil.getFileExtension(inputFile.getName()));
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
		return outputFile;
	}

	public static void decrypt(String key, File inputFile, File outputFile) throws CryptoException {
		doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}
	
	public static File decrypt(String key, File inputFile) throws CryptoException, IOException {
		File outputFile = FileUtil.createCloneTempFile(inputFile);
		doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
		return outputFile;
	}

	private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
		try (FileInputStream inputStream = new FileInputStream(inputFile);
				FileOutputStream outputStream = new FileOutputStream(outputFile)) {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			byte[] outputBytes = cipher.doFinal(inputBytes);
			outputStream.write(outputBytes);
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException ex) {
			Log.platform.error(String.format("Failed to %s with error %s",
					cipherMode == Cipher.ENCRYPT_MODE ? "encrypt" : "decrypt", ex.getMessage()));
			throw new CryptoException(ex);
		}
	}
	
	public static Key getSecureRandomKey(int keySize) {
	    byte[] secureRandomKeyBytes = new byte[keySize / 8];
	    SecureRandom secureRandom = new SecureRandom();
	    secureRandom.nextBytes(secureRandomKeyBytes);
	    return new SecretKeySpec(secureRandomKeyBytes, ALGORITHM);
	}
	
	public static Key getKeyFromKeyGenerator(int keySize) throws NoSuchAlgorithmException {
	    KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
	    keyGenerator.init(keySize);
	    return keyGenerator.generateKey();
	}
	
	public static Key getPasswordBasedKey(int keySize, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    byte[] salt = new byte[100];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(salt);
	    PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 1000, keySize);
	    SecretKey pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(pbeKeySpec);
	    return new SecretKeySpec(pbeKey.getEncoded(), ALGORITHM);
	}
	
}
