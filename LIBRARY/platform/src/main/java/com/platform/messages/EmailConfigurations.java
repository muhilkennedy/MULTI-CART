package com.platform.messages;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author muhil
 * Custom Email configuration
 */
public enum EmailConfigurations {
			
	MAIL_SMTP_HOST("mail.smtp.host"), 
	MAIL_SMTP_PORT("mail.smtp.port"),
	MAIL_TRANSPORT_PROTOCOL("mail.transport.protocol"),
	MAIL_SMTP_AUTH("mail.smtp.auth"),
	MAIL_SMTP_START_TLS("mail.smtp.starttls.enable"),
	MAIL_SMTP_SSL("mail.smtp.ssl.enable"),
	MAIL_USER_ID("app.email.id"),
	MAIL_USER_PASSWORD("app.email.password");
	
	private String property;
	
	EmailConfigurations(String property){
		this.property = property;
	}

	public String getProperty() {
		return property;
	}
	
	public static Optional<EmailConfigurations> getConfigByName(String name) {
		return stream().filter(config -> config.name().equalsIgnoreCase(name)).findFirst();
	}
	
	public static Stream<EmailConfigurations> stream() {
		return Stream.of(EmailConfigurations.values());
	}

}
