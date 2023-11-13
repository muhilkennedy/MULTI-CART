package com.user.api;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.user.entity.Permission;
import com.user.entity.Role;
import com.user.messages.RoleRequest;
import com.user.service.RolePermissionService;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("role")
@ValidateUserToken
public class RoleAndPermissionController {
	
	@Autowired
	private RolePermissionService rpService;
	
	@UserPermission(values = { Permissions.ADMIN })
	@GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Role> fetchRoles(@RequestParam(value = "tenantId", required = false) Long tenantId) throws SQLException {
		GenericResponse<Role> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(rpService.findAllRoles()).build();
	}

	@UserPermission(values = { Permissions.ADMIN })
	@GetMapping(value = "/fetch/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Permission> fetchPermissions(@RequestParam(value = "roleId", required = false) Long roleId) throws SQLException {
		GenericResponse<Permission> response = new GenericResponse<>();
		//TODO: filter superuser permission if user doesnt have superuser permission
		if(roleId == null || roleId == 0) {
			return response.setStatus(Response.Status.OK).setDataList(rpService.findAllPermission()).build();
		}
		return response.setStatus(Response.Status.OK).setDataList(rpService.findAllPermission(roleId)).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Role> createRole(@RequestParam(value = "tenantId", required = false) Long tenantId, @RequestBody RoleRequest role) {
		GenericResponse<Role> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(rpService.createRole(role)).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/toggle", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Role> toggleRoleStatus(@RequestParam("id") Long roleId) {
		GenericResponse<Role> response = new GenericResponse<>();
		Role role = rpService.findRoleById(roleId);
		if (role == null) {
			return response.setStatus(Response.Status.NO_CONTENT).setData(role).build();
		}
		rpService.toggleRoleStatus(role);
		return response.setStatus(Response.Status.OK).setData(role).build();
	}
	

}
