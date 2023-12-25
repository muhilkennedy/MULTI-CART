package com.base.entity;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "NOTIFICATIONTOKEN")
@ClassMetaProperty(code = "NTK")
public class Notificationtoken extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "USERID")
	private Long userid;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "DEVICEINFO")
	private String deviceinfo;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeviceinfo() {
		return deviceinfo;
	}

	public void setDeviceinfo(String deviceinfo) {
		this.deviceinfo = deviceinfo;
	}

}
