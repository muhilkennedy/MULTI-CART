package com.platform.security;

import org.apache.commons.lang3.StringUtils;

import com.platform.util.EncryptionUtil;
import com.platform.util.Log;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Muhil 
 * Encryption Standard for DB field encryption and decryption of
 * entity - @Convert(converter = ValueAttributeConverter.class)
 *
 */
@Converter(autoApply = true)
public class AttributeEncryptor implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String rawData) {
		if (StringUtils.isAllBlank(rawData)) {
			return rawData;
		}
		try {
			return EncryptionUtil.encrypt_AES(rawData);
		} catch (Exception e) {
			Log.platform.error("Exception encrypting field : {}", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		if (StringUtils.isAllBlank(dbData)) {
			return dbData;
		}
		try {
			return EncryptionUtil.decrypt_AES(dbData);
		} catch (Exception e) {
			Log.platform.error("Exception decrypting field {} : {}", dbData, e);
			throw new RuntimeException(e);
		}
	}

}
