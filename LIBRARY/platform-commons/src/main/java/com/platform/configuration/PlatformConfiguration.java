package com.platform.configuration;

import java.util.Objects;
import java.util.Properties;

import org.apache.http.util.Asserts;

import com.platform.util.PlatformPropertiesUtil;

/**
 * @author Muhil Builder class to initialize default properties.
 *
 */
public class PlatformConfiguration {

	private static Properties frontDoorProperties;
	private static Properties appProperties;
	private static Properties emailProperties;
	private static Properties encryptionProperties;
	private static Properties avProperties;
	private static String appName;
	private static boolean isMultitenantApp;

	public PlatformConfiguration(Properties frontDoorProperties, Properties appProperties, Properties emailProperties,
			Properties encryptionProperties, Properties avProperties, String appName, Boolean isMultitenantApp) {
		PlatformConfiguration.frontDoorProperties = frontDoorProperties;
		PlatformConfiguration.appProperties = appProperties;
		PlatformConfiguration.emailProperties = emailProperties;
		PlatformConfiguration.encryptionProperties = encryptionProperties;
		PlatformConfiguration.isMultitenantApp = isMultitenantApp;
		PlatformConfiguration.appName = appName;
		PlatformConfiguration.avProperties = avProperties;
	}

	public static Properties getFrontDoorProperties() {
		return frontDoorProperties;
	}

	public static void setFrontDoorProperties(Properties frontDoorProperties) {
		PlatformConfiguration.frontDoorProperties = frontDoorProperties;
	}

	public static Properties getAppProperties() {
		return appProperties;
	}

	public static void setAppProperties(Properties appProperties) {
		PlatformConfiguration.appProperties = appProperties;
	}

	public static Properties getEmailProperties() {
		return emailProperties;
	}

	public static void setEmailProperties(Properties emailProperties) {
		PlatformConfiguration.emailProperties = emailProperties;
	}

	public static Properties getEncryptionProperties() {
		return encryptionProperties;
	}

	public static String getAppName() {
		return appName;
	}

	public static void setAppName(String appName) {
		PlatformConfiguration.appName = appName;
	}

	public static boolean isMultitenantApp() {
		return isMultitenantApp;
	}

	public static void setMultitenantApp(boolean isMultitenantApp) {
		PlatformConfiguration.isMultitenantApp = isMultitenantApp;
	}

	public static void setEncryptionProperties(Properties encryptionProperties) {
		PlatformConfiguration.encryptionProperties = encryptionProperties;
	}

	public static Properties getAvProperties() {
		return avProperties;
	}

	public static void setAvProperties(Properties avProperties) {
		PlatformConfiguration.avProperties = avProperties;
	}

	public static class Builder {
		private Properties frontDoorProperties;
		private Properties appProperties;
		private Properties emailProperties;
		private Properties encryptionProperties;
		private String appName;
		private Boolean isMultitenantApp;
		private Properties avProperties;

		public Builder() {
		}

		public PlatformConfiguration.Builder withFrontDoorProperties(Properties frontDoorProperties) {
			this.frontDoorProperties = frontDoorProperties;
			return this;
		}

		public PlatformConfiguration.Builder withAppProperties(Properties appProperties) {
			this.appProperties = appProperties;
			return this;
		}

		public PlatformConfiguration.Builder withEmailProperties(Properties emailProperties) {
			this.emailProperties = emailProperties;
			return this;
		}

		public PlatformConfiguration.Builder withEncryptionProperties(Properties encProperties) {
			this.encryptionProperties = encProperties;
			return this;
		}

		public PlatformConfiguration.Builder withAppName(String appName) {
			this.appName = appName;
			return this;
		}

		public PlatformConfiguration.Builder withAVProperties(Properties avProps) {
			this.avProperties = avProps;
			return this;
		}

		public PlatformConfiguration.Builder isMultitenantApp(boolean isMultitenantApp) {
			this.isMultitenantApp = isMultitenantApp;
			return this;
		}

		public PlatformConfiguration build() {
			Asserts.notBlank(this.appName, "Register with Application Name");
			Objects.requireNonNull(this.isMultitenantApp,
					"Multitenant Application yes/no (base tenant info will be set based on this)");
			Objects.requireNonNull(this.frontDoorProperties, "Front door url properties cannot be null");
			Objects.requireNonNull(this.emailProperties, "Email configuration properties cannot be null");
			Objects.requireNonNull(this.encryptionProperties, "Encryption configuration properties cannot be null");
			Objects.requireNonNull(this.avProperties, "Antivirus configuration properties cannot be null");
			// validate frontdoor urls
			PlatformPropertiesUtil.getMandatoryFrontdoorProperties().parallelStream().forEach(property -> {
				Asserts.notBlank(this.frontDoorProperties.getProperty(property),
						String.format("Property %s ", property));
			});
			PlatformPropertiesUtil.getMandatoryEmailProperties().parallelStream().forEach(property -> {
				Asserts.notBlank(this.emailProperties.getProperty(property), String.format("Property %s ", property));
			});
			PlatformPropertiesUtil.getMandatorySecretsProperties().parallelStream().forEach(property -> {
				Asserts.notBlank(this.encryptionProperties.getProperty(property),
						String.format("Property %s ", property));
			});
			return new PlatformConfiguration(frontDoorProperties, appProperties, emailProperties, encryptionProperties,
					avProperties, appName, isMultitenantApp);
		}

	}

}
