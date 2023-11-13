package com.tenant.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Muhil
 *
 */
public class TenantInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> allowedOrigins;
	private String fssai;
	private String gstin;
	private String gmapUrl;
	private String logoUrl;
	private String logoThumbnail;

	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(List<String> allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

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

}
