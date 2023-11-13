package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import com.platform.util.EnvPropertiesUtil;

/**
 * @author muhil
 *
 */
public class PropertiesUtil {

	private static Environment env;
	
	public static String KEY_DB_SECRET = "encryption.db.secret";
	public static String KEY_JWT_SECRET = "encryption.jwt.secret";
	public static String KEY_FILE_SECRET = "encryption.file.secret";

	public static void initialize(Environment env) {
		setEnvironment(env);
	}
	
	private static void setEnvironment(Environment env) {
		PropertiesUtil.env = env;
	}

	public static String getProperty(String key) {
		return env.getProperty(key);
	}

	public static boolean getBooleanProperty(String key) {
		return env.getProperty(key, Boolean.class);
	}

	public static int getIntProperty(String key) {
		return env.getProperty(key, Integer.class);
	}
	
	public static String getEnvironmentValue(final String key) {
		String value = System.getenv(key);
		Assert.isTrue(StringUtils.isAllBlank(value), "ENVIROMENT_VARIABLE_" + key + "_NOT_DEFNINED");
		return value;
	}
	
	public static boolean isProdDeployment() {
		if (env.getProperty("spring.profiles.active").equals("prod")) {
			return true;
		}
		return false;
	}
	
	public static String getDBEncryptionSecret() {
		String key = env.getProperty(KEY_DB_SECRET);
		if(!StringUtils.isAllBlank(key)) {
			return key;
		}
		return EnvPropertiesUtil.getEnvironmentValue("DB_SECRET");
	}
	
	public static String getJWTSecret() {
		String key = env.getProperty(KEY_JWT_SECRET);
		if(!StringUtils.isAllBlank(key)) {
			return key;
		}
		return EnvPropertiesUtil.getEnvironmentValue("JWT_SECRET");
	}
	
	public static String getFileSecret() {
		String key = env.getProperty(KEY_FILE_SECRET);
		if(!StringUtils.isAllBlank(key)) {
			return key;
		}
		return EnvPropertiesUtil.getEnvironmentValue("FILE_SECRET");
	}

	public static String getDefaultDirectory() {
		return System.getProperty("default.dir");
	}


}
