package com.platform.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.platform.exception.EncryptionException;

/**
 * @author Muhil
 */
public class EncryptionUtil {

	private static String ALGORITHM = "AES/CBC/PKCS5PADDING";

	public static String hash_SHA256(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return Base64.encodeBase64String(md.digest(input.getBytes(StandardCharsets.UTF_8)));
	}

	public static String hash_SHA512(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		return Base64.encodeBase64String(md.digest(input.getBytes(StandardCharsets.UTF_8)));
	}

	public static String encrypt_AES(String value) throws EncryptionException {
		try {
			IvParameterSpec iv = new IvParameterSpec(
					Base64.decodeBase64(PlatformPropertiesUtil.getDbSecretInitVector()));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(PlatformPropertiesUtil.getDbSecret()),
					"AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} catch (Exception ex) {
			Log.platform.error("Exception encrypting String {} : {}", value, ex);
			throw new EncryptionException();
		}
	}

	public static String decrypt_AES(String encrypted) throws EncryptionException {
		try {
			IvParameterSpec iv = new IvParameterSpec(
					Base64.decodeBase64(PlatformPropertiesUtil.getDbSecretInitVector()));
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(PlatformPropertiesUtil.getDbSecret()),
					"AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			return new String(original);
		} catch (Exception ex) {
			Log.platform.error("Exception decrypting String {} : {}", encrypted, ex);
			throw new EncryptionException();
		}
	}

	// to provide additional security init vector is manipulated dynamically so
	// patterns cannot be found on encrypted text
	public static String encrypt_AES_Secure(String value) throws EncryptionException {
		try {
			String hashValue = hash_SHA256(value);
			IvParameterSpec iv = new IvParameterSpec(hashValue.substring(0, 17).getBytes());
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(PlatformPropertiesUtil.getDbSecret()),
					"AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} catch (Exception ex) {
			Log.platform.error("Exception encrypting String {} : {}", value, ex);
			throw new EncryptionException();
		}
	}

	public static String decrypt_AES_Secure(String encrypted, String hashIVKey) throws EncryptionException {
		try {
			IvParameterSpec iv = new IvParameterSpec(hashIVKey.substring(0, 17).getBytes());
			SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(PlatformPropertiesUtil.getDbSecret()),
					"AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			return new String(original);
		} catch (Exception ex) {
			Log.platform.error("Exception decrypting String {} : {}", encrypted, ex);
			throw new EncryptionException();
		}
	}

	public static IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}
}
