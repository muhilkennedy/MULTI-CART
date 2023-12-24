package com.platform.entity;

import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 *
 */
public class PlatformTenant extends PlatformBaseEntity implements TenantBaseObject {
	
	private String name;
	private String uniquename;
	private Long parent;
	private String locale;
	private String timezone;
	private boolean purgeTenant;

	private PlatformTenantDetails tenantDetail;
	
	public static PlatformTenant getSystemTenant() {
		PlatformTenant tenant = new PlatformTenant();
		tenant.setUniquename(PlatformUtil.INTERNAL_SYSTEM);
		tenant.setActive(true);
		tenant.setName("MKEN SYSTEM");
		tenant.setRootid(PlatformUtil.SYSTEM_REALM_ROOTID);
		return tenant;
	}
	
	@Override
	public Long getObjectId() {
		return this.getRootid();
	}
	
	@Override
	public Long getTenantRootId() {
		return getRootid();
	}
	
	@Override
	public String getUniqueId() {
		return uniquename;
	}

	@Override
	public String getTenantName() {
		return name;
	}

	@Override
	public String getTenantUniqueName() {
		return uniquename;
	}

	public boolean isPurgeTenant() {
		return purgeTenant;
	}

	public void setPurgeTenant(boolean purgeTenant) {
		this.purgeTenant = purgeTenant;
	}
	
	public PlatformTenantDetails getTenantDetail() {
		return tenantDetail;
	}

	public void setTenantDetail(PlatformTenantDetails tenantDetail) {
		this.tenantDetail = tenantDetail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	@Override
	public boolean isSystemTenant() {
		return getRootid() == PlatformUtil.SYSTEM_REALM_ROOTID;
	}
	
}
