package com.user.messages;

import java.util.List;

/**
 * @author muhil
 */
public class RoleRequest {

	private String name;
	private List<Long> permissionIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

}
