package com.platform.util;

import org.apache.http.util.Asserts;

public class EnvPropertiesUtil {
	
	public static String getEnvironmentValue(final String key) {
		String value = System.getenv(key);
		Log.platform.debug("Requested for Key - {} :: value - {}", key, value);
		Asserts.notBlank(value, "ENVIROMENT_VARIABLE " + key + " NOT_DEFINED");
		return value;
	}

}
