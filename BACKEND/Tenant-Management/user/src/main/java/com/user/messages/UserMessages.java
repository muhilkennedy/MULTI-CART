package com.user.messages;

/**
 * @author Muhil Localization keys
 */
public enum UserMessages {

	INVALID_ACCESS("user.invalidAccess"), INACTIVE("user.inactive"), TOKEN_VALIDATION_FAILED("user.validationFailed"),
	TOKEN_EXPIRED("user.tokenExpired"), TOKEN_MISSIG("user.tokenMissing"), PERMISSION_DENIED("user.denied");

	String key;

	UserMessages(String code) {
		this.key = code;
	}

	public String getKey() {
		return key;
	}

}
