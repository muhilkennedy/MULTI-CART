package com.platform.entity;

/**
 * @author Muhil
 *
 */
public class PlatformTenantDetails extends PlatformBaseEntity {

	private String contact;
	private String emailid;
	private String street;
	private String city;
	private String pincode;
	private String tagline;
	private PlatformTenantInfo details;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public PlatformTenantInfo getDetails() {
		return details;
	}

	public void setDetails(PlatformTenantInfo details) {
		this.details = details;
	}

}

//The json field in tenant entity.
class PlatformTenantInfo {

	private String adminUrl;
	private String clientUrl;
	private boolean initialSetUpDone;
	private String fssai;
	private String gstin;
	private String gmapUrl;
	private String logoUrl;
	private String logoThumbnail;

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

	public boolean isInitialSetUpDone() {
		return initialSetUpDone;
	}

	public void setInitialSetUpDone(boolean initialSetUpDone) {
		this.initialSetUpDone = initialSetUpDone;
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