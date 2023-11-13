package com.user.entity;

import java.io.Serializable;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "PERMISSION")
@ClassMetaProperty(code = "PERMISSION")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROOTID")
	private Long rootid;

	@Column(name = "PERMISSION")
	private String permission;

	@Column(name = "ACTIVE")
	private boolean active;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getRootid() {
		return rootid;
	}

	public void setRootid(Long rootid) {
		this.rootid = rootid;
	}

}
