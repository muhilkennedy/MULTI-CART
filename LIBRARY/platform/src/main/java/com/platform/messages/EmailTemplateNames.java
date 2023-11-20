package com.platform.messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author muhil 
 * These will be default .ftl names (email templates for freemarker).
 * custom place holders for the template should be listed here.
 */
public enum EmailTemplateNames {

	EMPLOYEE_REGISTRATION(new String[] { "tenantLogo", "userName", "password", "mobile", "email", "tenantAdminUrl" }),
	FORGOT_PASSWORD(new String[] { "tenantLogo", "userName", "uniqueName", "mobile", "email", "forgotPasswordUrl" }),
	CUSTOMER_REGISTRATION(new String[] { "tenantLogo", "tenanThumbnail", "tenantName", "tenantClientUrl", "tenantAddress",
			"tenantContact" });

	private String[] placeHolders;

	EmailTemplateNames(String[] args) {
		this.placeHolders = args;
	}

	public static Optional<EmailTemplateNames> getTemplateName(String name) {
		return Stream.of(EmailTemplateNames.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
	}
	
	public static Map<String, List<String>> getAvailableTemplateNames() {
		Map<String, List<String>> map = new HashMap<>();
		Stream.of(EmailTemplateNames.values())
				.forEach(type -> map.put(type.name(), Arrays.asList(type.getPlaceHolders())));
		return map;
	}

	public String[] getPlaceHolders() {
		return placeHolders;
	}

}
