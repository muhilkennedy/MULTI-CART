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
	private Long permissionid;
	
	@Column(name = "ROLEID")
	private Long roleid;

	@ManyToOne
	@JoinColumn(name = "PERMISSIONID", nullable = false, insertable = false, updatable = false)
	private Permission permission;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	private Role role;

	public RolePermission() {
		super();
	}
	
	public RolePermission(Long permission, Long role) {
		super();
		this.permissionid = permission;
		this.roleid = role;
	}
	
	public RolePermission(Permission permission, Role role) {
		super();
		this.permission = permission;
		this.role = role;
		setPermissionid(permission.getRootid());
		setRoleid(role.getRootId());
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

	public Long getPermissionid() {
		return permissionid;
	}

	public void setPermissionid(Long permissionid) {
		this.permissionid = permissionid;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

}