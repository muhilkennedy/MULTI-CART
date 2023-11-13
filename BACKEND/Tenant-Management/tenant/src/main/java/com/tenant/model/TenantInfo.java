package com.tenant.model;

import java.io.Serializable;

/**
 * @author Muhil
 *
 */
public class TenantInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String adminUrl;
	private String clientUrl;
	private String fssai;
	private String gstin;
	private String gmapUrl;
	private String logoUrl;
	private String logoThumbnail;

	public String getFssai() {
		return fssai;
	}

	public void setFssai(String fssai) {
		this.fssai = fssai;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getGmapUrl() {
		return gmapUrl;
	}

	public void setGmapUrl(String gmapUrl) {
		this.gmapUrl = gmapUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoThumbnail() {
		return logoThumbnail;
	}

	public void setLogoThumbnail(String logoThumbnail) {
		this.logoThumbnail = logoThumbnail;
	}

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