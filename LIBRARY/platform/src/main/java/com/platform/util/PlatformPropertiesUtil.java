package com.platform.util;

import java.util.Arrays;
import java.util.List;

import com.platform.configuration.PlatformConfiguration;

/**
 * @author Muhil
 *
 */
public class PlatformPropertiesUtil {
	
	public static final String KEY_TENANT_FRONTDOOR = "service.tm.frontdoor";
	public static final String KEY_USER_FRONTDOOR = "service.user.frontdoor";
	
	public static final String KEY_MAIL_ENABLED = "app.email.enabled";
	public static final String KEY_MAIL_HOST = "mail.smtp.host";
	public static final String KEY_MAIL_PORT = "mail.smtp.port";
	public static final String KEY_MAIL_AUTH = "mail.smtp.auth";
	public static final String KEY_MAIL_SOCKET_CLASS = "mail.smtp.socketFactoryClass";
	public static final String KEY_MAIL_SSL_STATUS = "mail.smtp.starttls.enable";
	public static final String KEY_MAIL_PROTOCOL = "mail.transport.protocol";
	public static final String KEY_DEFAULT_EMAIL = "app.admin.email";
	public static final String KEY_DEFAULT_PASSWORD = "app.admin.email.password";
	public static final String KEY_MAIL_TRUST = "mail.smtp.ssl.trust";
	public static final String KEY_MAIL_SSL = "mail.smtp.ssl.enable";
	
	public static final String KEY_DB_SECRET = "encryption.db.secret";
	public static final String KEY_FILE_SECRET = "encryption.file.secret";
	public static final String KEY_JWT_SECRET = "encryption.jwt.secret";
	public static final String KEY_DB_INITVECTOR = "encrypyion.db.initvector";
	
	public static final String KEY_AV_ENABLED = "app.security.clamav.enabled";
	public static final String KEY_AV_HOST = "app.security.clamav.host";
	public static final String KEY_AV_PORT = "app.security.clamav.port";
	
	public static List<String> getMandatoryFrontdoorProperties() {
		return Arrays.asList(KEY_TENANT_FRONTDOOR, KEY_USER_FRONTDOOR);
	}

	public static List<String> getMandatoryEmailProperties() {
		return Arrays.asList(KEY_DEFAULT_EMAIL, KEY_DEFAULT_PASSWORD, KEY_MAIL_AUTH, KEY_MAIL_HOST, KEY_MAIL_PORT,
				KEY_MAIL_SSL_STATUS, KEY_MAIL_PROTOCOL, KEY_MAIL_ENABLED, KEY_MAIL_TRUST, KEY_MAIL_SSL);
	}
	
	public static List<String> getMandatorySecretsProperties() {
		return Arrays.asList(KEY_DB_SECRET, KEY_FILE_SECRET, KEY_JWT_SECRET, KEY_DB_INITVECTOR);
	}
	
	public static List<String> getMandatoryAVProperties() {
		return Arrays.asList(KEY_AV_ENABLED, KEY_AV_HOST, KEY_AV_PORT);
	}

	public static String getTMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(KEY_TENANT_FRONTDOOR);
	}

	public static String getUMFrontDoorUrl() {
		return PlatformConfiguration.getFrontDoorProperties().getProperty(KEY_USER_FRONTDOOR);
	}
	
	public static String getDefaultEmail() {
		return PlatformConfiguration.getEmailProperties().getProperty(KEY_DEFAULT_EMAIL);
	}
	
	public static String getDefaultEmailPassword() {
		return PlatformConfiguration.getEmailProperties().getProperty(KEY_DEFAULT_PASSWORD);
	}
	
	public static String getDbSecret() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_DB_SECRET);
	}

	public static String getDbSecretInitVector() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_DB_INITVECTOR);
	}

	public static String getFileSecret() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_FILE_SECRET);
	}

	public static String getJwtSecret() {
		return PlatformConfiguration.getEncryptionProperties().getProperty(KEY_JWT_SECRET);
	}

}
