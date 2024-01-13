package com.user.entity;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import com.base.entity.MultiTenantEntity;
import com.base.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.annotations.PIIData;
import com.platform.entity.UserBaseObject;
import com.platform.security.AttributeEncryptor;
import com.platform.user.Permissions;
import com.platform.util.EncryptionUtil;
import com.platform.util.PlatformUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

/**
 * @author Muhil Kennedy
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ClassMetaProperty(code = "USR")
public class User extends MultiTenantEntity implements UserBaseObject {

	private static final long serialVersionUID = 1L;

	@FullTextField
	@Column(name = "UNIQUENAME", updatable = false)
	private String uniquename;

	@FullTextField
	@Column(name = "FNAME")
	private String fname;
	
	@FullTextField
	@Column(name = "LNAME")
	private String lname;

	@PIIData(allowedRolePermissions = {Permissions.ADMIN, Permissions.MANAGE_USERS}, visibleCharacters = 4)
	@Column(name = "MOBILE")
	@Convert(converter = AttributeEncryptor.class)
	private String mobile;

	@JsonIgnore
	@Column(name = "MOBILEHASH")
	private String mobilehash;

	@FullTextField
	@PIIData(allowedRolePermissions = {Permissions.ADMIN, Permissions.MANAGE_USERS})
	//TODO: impl has field and compare @Convert(converter = AttributeEncryptor.class)
	@Column(name = "EMAILID")
	private String emailid;

	@JsonIgnore
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "LOCALE")
	private String locale;

	@Column(name = "TIMEZONE")
	private String timezone;
	
	public User() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@PrePersist
	private void preProcess() throws SQLException {
		if (StringUtils.isBlank(timezone)) {
			this.timezone = "IST";
		}
		if (StringUtils.isBlank(locale)) {
			this.locale = "en_US";
		}
		if (StringUtils.isEmpty(uniquename)) {
			try {
				generateUniqueName();
			} catch (SQLException e) {
				Log.user.error("Exception generating unique name for user {}", e);
				throw e;
			}
		}
		if (!StringUtils.isBlank(mobile)) {
			updateMobileHash();
		}
	}

	public void updateMobileHash() {
		try {
			this.mobilehash = EncryptionUtil.hash_SHA256(mobile);
		} catch (NoSuchAlgorithmException e) {
			Log.user.error("Exception generating hash for user {}", e);
			throw new RuntimeException(e);
		}
	}

	protected void generateUniqueName() throws SQLException {
		// NO-OP
	}

	@Override
	public String getUniqueId() {
		return this.uniquename;
	}

	@Override
	public Set<Permissions> getUserPermissions() {
		return Collections.<Permissions>emptySet();
	}

	public boolean isSystemUser() {
		return getRootid() == PlatformUtil.SYSTEM_USER_ROOTID;
	}

}
