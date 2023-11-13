package com.tenant.messages;

/**
 * @author Muhil
 * Localization keys
 */
public enum TenantMessages {

	INACTVE("tenant.inactive"), INVALID("tenant.invalid");

	String key;

	TenantMessages(String code) {
		this.key = code;
	}

	public String getKey() {
		return key;
	}

}
