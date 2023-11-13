package com.user.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.platform.util.Log;
import com.platform.util.PlatformUtil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Muhil
 *
 */
@Converter(autoApply = true)
public class UserInfoConvertor implements AttributeConverter<UserInfo, String> {

	@Override
	public String convertToDatabaseColumn(UserInfo attribute) {
		try {
			if (attribute == null) {
				return PlatformUtil.EMPTY_STRING;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			Log.tenant.error("Error converting to database column - {}", ex);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	@Override
	public UserInfo convertToEntityAttribute(String dbData) {
		try {
			if (StringUtils.isNullOrEmpty(dbData)) {
				return new UserInfo();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(dbData, UserInfo.class);
		} catch (JsonProcessingException ex) {
			Log.tenant.error("Error converting to entity attribute - {}", ex);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

}
