package com.platform.entity;

/**
 * @author Muhil
 *
 */
public class TenantOrigin {

	private String origin;
	private boolean active;
	private long timeUpdated;
	private long timeCreated;
	private String modifiedBy;
	private String createdBy;
	private long version;
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
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
