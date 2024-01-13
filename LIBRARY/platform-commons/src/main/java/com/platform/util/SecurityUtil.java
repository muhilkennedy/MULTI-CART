package com.platform.util;

import java.util.Arrays;

import org.apache.commons.text.StringEscapeUtils;

public class SecurityUtil {
	
	public static final int PASSWORD_SALT_ROUNDS = 5;
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
	private static final String ALPHA_NUMERIC_SPECIAL_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz!@#$*";
	private static final String NUMERIC_STRING = "1234567890";
	
	private static final int randomCodeLength = 6;
	private static final int randomPasswordLength = 8;
	
	/**
	 * @return random password with pre-defined length and Alpha numeric characters.
	 */
	public static String generateRandomPassword() {
		StringBuilder builder = new StringBuilder();
		int count = randomPasswordLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_SPECIAL_STRING.length());
			builder.append(ALPHA_NUMERIC_SPECIAL_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String generateRandomCode() {
		StringBuilder builder = new StringBuilder();
		int count = randomCodeLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String generateRandomNumericCode() {
		StringBuilder builder = new StringBuilder();
		int count = randomCodeLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static String[] getEnumAsString(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}
	
	public static String sanitizeString(String value) {
		return StringEscapeUtils.escapeHtml4(value);
	}

}
