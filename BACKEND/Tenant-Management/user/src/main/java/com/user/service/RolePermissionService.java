package com.user.service;

import java.util.List;

import com.user.entity.Employee;
import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.messages.RoleRequest;

/**
 * @author Muhil
 * interface used for both service and dao.(Bad Idea)
 */
public interface RolePermissionService {

	List<Role> findAllRoles();

	List<Permission> findAllPermission();

	List<Permission> findAllPermission(Long roleId);

	default Role createRole(RoleRequest roleRequest) {
		return null;
	}

	default Employee addRolesToEmployee(Employee employee, List<Long> roleIds) {
		return null;
	}

	Role findRoleById(Long rootId);

	default Role toggleRoleStatus(Role role) {
		return null;
	}

	List<Role> getAllEmployeeRoles(Long employeeId);

	default Employee removeRolesForEmployee(Employee employee, List<Long> roleIds) {
		return null;
	}

}
