package com.i18n.util;

import java.util.Locale;
import java.util.stream.Stream;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 *
 */
@Component
public class LocaleUtil {

	private static MessageSource messageSource;

	@Autowired
	private MessageSource msgSource;
	
	public static enum SupportedLanguageCodes { en, ta };

	@PostConstruct
	private void setMessageSouce() {
		LocaleUtil.messageSource = msgSource;
	}

	public static Locale getValidLocale(String localeCode) {
		return LocaleUtils.toLocale(localeCode);
	}

	public static String getLocalisedString(String key, String[] paramValues, Locale locale) {
		return messageSource.getMessage(key, paramValues,
				(locale == null) ? Locale.ENGLISH : LocaleUtils.toLocale(locale));
	}

	public static String getLocalisedString(String key, String[] paramValues, String locale) {
		return messageSource.getMessage(key, paramValues,
				StringUtils.isAllBlank(locale) ? Locale.ENGLISH : LocaleUtils.toLocale(locale));
	}
	
	public static boolean isSupportedLanguage(String code) {
		return Stream.of(SupportedLanguageCodes.values()).filter(lang -> lang.name().equals(code)).findAny().isPresent();
	}

}
