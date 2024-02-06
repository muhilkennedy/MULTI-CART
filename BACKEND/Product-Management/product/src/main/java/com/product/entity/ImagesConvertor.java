package com.product.entity;

import org.apache.commons.lang3.StringUtils;

import com.base.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.util.PlatformUtil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Muhil
 *
 */
@Converter(autoApply = true)
public class ImagesConvertor implements AttributeConverter<ProductImages, String> {

	@Override
	public String convertToDatabaseColumn(ProductImages attribute) {
		try {
			if (attribute == null) {
				return PlatformUtil.EMPTY_STRING;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			Log.product.error("Error converting to database column - {}", ex);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	@Override
	public ProductImages convertToEntityAttribute(String dbData) {
		try {
			if (StringUtils.isAllBlank(dbData)) {
				return new ProductImages();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(dbData, ProductImages.class);
		} catch (JsonProcessingException ex) {
			Log.product.error("Error converting to entity attribute - {}", ex);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

}
