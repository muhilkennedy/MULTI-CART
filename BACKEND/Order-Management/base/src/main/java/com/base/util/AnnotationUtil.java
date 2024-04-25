package com.base.util;

import org.apache.commons.lang3.StringUtils;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
public class AnnotationUtil {

	public static String getClassMetaCode(Object object) {
		ClassMetaProperty metaProp = object.getClass().getAnnotation(ClassMetaProperty.class);
		if (metaProp == null) {
			return object.getClass().getSimpleName();
		}
		return StringUtils.isNotBlank(metaProp.code()) ? metaProp.code() : object.getClass().getSimpleName();
	}
	
	public static String getClassTableName(Object object) {
		Table metaProp = object.getClass().getAnnotation(Table.class);
		if (metaProp == null) {
			return object.getClass().getSimpleName();
		}
		return StringUtils.isNotBlank(metaProp.name()) ? metaProp.name() : object.getClass().getSimpleName();
	}


}
