package com.tenant.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.tenant.entity.Tenant;
import com.tenant.messages.TenantRequestBody;
import com.tenant.service.TenantService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("tenant/admin")
@ValidateUserToken
public class TenantAdminController {
	
	@Autowired
	private TenantService tenantService;
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> createTenant(HttpServletRequest request,
			@RequestBody @Valid TenantRequestBody tenant) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = tenantService.createTenant(tenant);
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/logo", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantLogo(@RequestParam("picture") MultipartFile picture) throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = tenantService.uploadLogo(BaseUtil.generateFileFromMutipartFile(picture));
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}

}
