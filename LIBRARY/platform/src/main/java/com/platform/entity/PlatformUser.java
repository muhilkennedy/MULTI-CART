package com.platform.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.platform.user.Permissions;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 * generic user object
 */
public class PlatformUser extends PlatformBaseEntity implements UserBaseObject {

	private String uniquename;
	private String fname;
	private String lname;
	private String mobile;
	private String emailid;
	private String locale;
	private String timezone;
	private List<String> userPermissions;

	@Override
	public Long getObjectId() {
		return getRootid();
	}
	
	@Override
	public String getUniqueId() {
		return getUniquename();
	}
	
	public static PlatformUser getSystemUser() {
		// Default SYSTEM user with all permissions.
		PlatformUser user = new PlatformUser();
		user.setUniquename(PlatformUtil.INTERNAL_SYSTEM);
		user.setRootid(PlatformUtil.SYSTEM_USER_ROOTID);
		user.setUserPermissions(Permissions.stream().map(permission -> permission.name()).toList());
		return user;
	}

	@Override
	public Set<Permissions> getUserPermissions() {
		return userPermissions.stream().map(permission -> Permissions.getPermissionIfValid(permission))
				.collect(Collectors.toSet());
	}

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
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

	public void setUserPermissions(List<String> userPermissions) {
		this.userPermissions = userPermissions;
	}
	
}
