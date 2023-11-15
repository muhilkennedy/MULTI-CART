package com.tenant.messages;

import jakarta.validation.constraints.NotBlank;

/**
 * @author muhil
 */
public class TenantOriginRequest {

	@NotBlank
	private String adminUrl;
	@NotBlank
	private String clientUrl;

	public String getAdminUrl() {
		return adminUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public String getClientUrl() {
		return clientUrl;
	}

	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

}
