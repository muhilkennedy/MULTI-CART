package com.user.entity;

import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "ROLEPERMISSION")
@ClassMetaProperty(code = "RP")
public class RolePermission extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "PERMISSIONID")
	private int permissionid;
	
	@Column(name = "ROLEID")
	private String roleid;

	@ManyToOne
	@JoinColumn(name = "PERMISSIONID", nullable = false, insertable = false, updatable = false)
	private Permission permission;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	private Role role;

	public int getPermissionId() {
		return permissionid;
	}

	public void setPermissionId(int permissionId) {
		this.permissionid = permissionId;
	}

	public String getRoleId() {
		return roleid;
	}

	public void setRoleId(String roleId) {
		this.roleid = roleId;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}