package com.platform.email;

import java.util.Properties;

/**
 * @author Muhil
 * Default system level email configuration
 */
public class EmailSystem {

	private static EmailConnectionProperties emailConnectionProperties;

	public EmailSystem(EmailConnectionProperties emailConnectionProperties) {
		EmailSystem.emailConnectionProperties = emailConnectionProperties;
	}

	public static class Builder {
		private EmailConnectionProperties emailProperties;

		public Builder() {
		}

		public EmailSystem.Builder withEmailProperties(EmailConnectionProperties emailProperties) {
			this.emailProperties = emailProperties;
			return this;
		}

		public EmailSystem build() {
			return new EmailSystem(emailProperties);
		}

	}
	
	public static Properties getDefaultEmailConfig() {
		return emailConnectionProperties.getEmailProperties();
	}
	
	public static String getDefaultEmailId() {
		return (String) emailConnectionProperties.getAuthProps().get("app.admin.email");
	}
	
	public static String getDefaultEmailPassword() {
		return (String) emailConnectionProperties.getAuthProps().get("app.admin.email.password");
	}

}
