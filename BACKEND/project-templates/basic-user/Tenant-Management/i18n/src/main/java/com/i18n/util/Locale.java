package com.i18n.util;

import org.apache.commons.lang3.LocaleUtils;

/**
 * @author Muhil
 *
 */
public class Locale {

	public static java.util.Locale getValidLocale(String localeCode) {
		return LocaleUtils.toLocale(localeCode);
	}
	
}
