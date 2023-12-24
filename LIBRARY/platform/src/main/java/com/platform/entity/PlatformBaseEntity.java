package com.platform.entity;

/**
 * @author muhil
 */
public class PlatformBaseEntity implements BaseObject {

	private Long rootid;
	private boolean active;
	private long timeupdated;
	private long timecreated;
	private String modifiedby;
	private String createdby;
	private long version;

	public Long getRootid() {
		return rootid;
	}

	public void setRootid(Long rootid) {
		this.rootid = rootid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getTimeupdated() {
		return timeupdated;
	}

	public void setTimeupdated(long timeupdated) {
		this.timeupdated = timeupdated;
	}

	public long getTimecreated() {
		return timecreated;
	}

	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public Long getObjectId() {
		return rootid;
	}

}
