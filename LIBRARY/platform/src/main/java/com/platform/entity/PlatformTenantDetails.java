package com.platform.entity;

/**
 * @author Muhil
 *
 */
public class PlatformTenantDetails {

	private String tenantContact;
	private String tenantEmail;
	private String tenantStreet;
	private String tenantCity;
	private String tenantPin;
	private String tagLine;

	private boolean active;
	private long timeUpdated;
	private long timeCreated;
	private String modifiedBy;
	private String createdBy;
	private long version;

	public String getTenantContact() {
		return tenantContact;
	}

	public void setTenantContact(String tenantContact) {
		this.tenantContact = tenantContact;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getTenantStreet() {
		return tenantStreet;
	}

	public void setTenantStreet(String tenantStreet) {
		this.tenantStreet = tenantStreet;
	}

	public String getTenantCity() {
		return tenantCity;
	}

	public void setTenantCity(String tenantCity) {
		this.tenantCity = tenantCity;
	}

	public String getTenantPin() {
		return tenantPin;
	}

	public void setTenantPin(String tenantPin) {
		this.tenantPin = tenantPin;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getTimeUpdated() {
		return timeUpdated;
	}

	public void setTimeUpdated(long timeUpdated) {
		this.timeUpdated = timeUpdated;
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
