package com.platform.email;

import java.util.Properties;

/**
 * @author Muhil
 *
 */
public class EmailConnectionProperties {
	
	private final Properties emailProperties;
	private final Properties authProps;
	
	public EmailConnectionProperties(Properties emailProperties, Properties authProps) {
		this.emailProperties = emailProperties;
		this.authProps = authProps;
	}

	public Properties getEmailProperties() {
		return emailProperties;
	}

	public Properties getAuthProps() {
		return authProps;
	}

}
