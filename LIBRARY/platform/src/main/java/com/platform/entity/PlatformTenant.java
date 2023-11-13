package com.platform.entity;

import java.util.List;

/**
 * @author Muhil
 *
 */
public class PlatformTenant implements TenantBaseObject {

	private Long rootId;
	private String tenantUniqueName;
	private String tenantName;
	private String locale;
	private String timeZone;
	private boolean purgeTenant;

	// common base fields
	private boolean active;
	private long timeUpdated;
	private long timeCreated;
	private String modifiedBy;
	private String createdBy;
	private long version;

	private PlatformTenantDetails tenantDetail;

	private List<TenantOrigin> tenantOrigin;
	
	@Override
	public Long getObjectId() {
		return this.rootId;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public String getTenantUniqueName() {
		return tenantUniqueName;
	}

	public void setTenantUniqueName(String tenantUniqueName) {
		this.tenantUniqueName = tenantUniqueName;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public boolean isPurgeTenant() {
		return purgeTenant;
	}

	public void setPurgeTenant(boolean purgeTenant) {
		this.purgeTenant = purgeTenant;
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

	public PlatformTenantDetails getTenantDetail() {
		return tenantDetail;
	}

	public void setTenantDetail(PlatformTenantDetails tenantDetail) {
		this.tenantDetail = tenantDetail;
	}

	public List<TenantOrigin> getTenantOrigin() {
		return tenantOrigin;
	}

	public void setTenantOrigin(List<TenantOrigin> tenantOrigin) {
		this.tenantOrigin = tenantOrigin;
	}

	@Override
	public Long getTenantRootId() {
		return getRootId();
	}
	
	@Override
	public String getUniqueId() {
		return tenantUniqueName;
	}

}
