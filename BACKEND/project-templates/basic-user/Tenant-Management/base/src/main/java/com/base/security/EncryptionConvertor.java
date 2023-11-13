package com.base.security;

import org.springframework.stereotype.Component;

import com.platform.exception.EncryptionException;
import com.platform.util.EncryptionUtil;

import jakarta.persistence.AttributeConverter;

/**
 * @author muhil
 */
@Component
public class EncryptionConvertor implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String s) {
		try {
			return EncryptionUtil.encrypt(s);
		} catch (EncryptionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String s) {
		try {
			return EncryptionUtil.decrypt(s);
		} catch (EncryptionException e) {
			throw new RuntimeException(e);
		}
	}
}
