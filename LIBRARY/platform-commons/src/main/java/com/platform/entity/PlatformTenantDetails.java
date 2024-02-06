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