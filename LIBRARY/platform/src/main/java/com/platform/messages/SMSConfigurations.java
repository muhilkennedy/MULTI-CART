package com.platform.messages;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Muhil
 */
public enum SMSConfigurations {
	
	SMS_HOST("sms.host"),
	SMS_OTP_TEMPLATE("sms.otp.template"),
	SMS_TXN_TEMPLATE("sms.otp.template");
	
	private String property;
	
	SMSConfigurations(String property){
		this.property = property;
	}

	public String getProperty() {
		return property;
	}
	
	public static Optional<SMSConfigurations> getConfigByName(String name) {
		return stream().filter(config -> config.name().equalsIgnoreCase(name)).findFirst();
	}
	
	public static Stream<SMSConfigurations> stream() {
		return Stream.of(SMSConfigurations.values());
	}
}
