package com.tenant.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.entity.ConfigType;
import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;
import com.tenant.messages.StorageConfigRequest;
import com.tenant.messages.TenantOriginRequest;
import com.tenant.messages.TenantRequestBody;
import com.tenant.service.TenantService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/tenant")
@ValidateUserToken
public class TenantAdminController {
	
	@Autowired
	private TenantService tenantService;
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> createTenant(HttpServletRequest request,
			@RequestBody @Valid TenantRequestBody tenant)
			throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = tenantService.createTenant(tenant);
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}

	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/logo", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantLogo(@RequestParam("picture") MultipartFile picture,
			@RequestParam(value = "tenantId", required = false) Long tenantId)
			throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		Tenant newTenant = tenantService.uploadLogo(BaseUtil.generateFileFromMutipartFile(picture));
		return response.setStatus(Response.Status.CREATED).setData(newTenant).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> getTenant(@RequestParam("tenantUniqueName") String uniqueName)
			throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.findByUniqueName(uniqueName)).build();
	}

	@UserPermission(values = Permissions.SUPER_USER)
	@GetMapping(value = "/alltenants", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> getAllTenants() throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(tenantService.findAllTenants()).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PatchMapping(value = "/toggle", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> toggleTenant(@RequestParam("tenantUniqueName") String uniqueName) throws IllegalStateException, IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		tenantService.toggleTenantState(uniqueName);
		return response.setStatus(Response.Status.OK).build();
	}

	@UserPermission(values = Permissions.SUPER_USER)
	@GetMapping(value = "/subscriptions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<TenantSubscription> getAllTenantSubscription(@RequestParam("tenantId") Long tenantId) throws IllegalStateException, IOException {
		return tenantService.getAllTenantSubscription(tenantId);
	}

	@UserPermission(values = Permissions.SUPER_USER)
	@PutMapping(value = "/addsubscription", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<TenantSubscription> addSubscription(@RequestParam("tenantId") Long tenantId, @RequestBody @Valid TenantRequestBody tenantBody) throws IllegalStateException, IOException {
		GenericResponse<TenantSubscription> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.addTenantSubscription(tenantBody)).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/storage/addconfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ConfigType> addStorageConfig(@RequestParam("tenantId") Long tenantId,
			@RequestBody @Valid StorageConfigRequest configRequest) throws IOException{
		GenericResponse<ConfigType> response = new GenericResponse<>();
		tenantService.createStorageConfig(configRequest.getConfig(), configRequest.getBucket(), configRequest.getType());
		return response.setStatus(Response.Status.OK).build();
	}
	
	@UserPermission(values = Permissions.SUPER_USER)
	@PostMapping(value = "/origins", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantUrls(@RequestParam("tenantId") Long tenantId,
			@RequestBody @Valid TenantOriginRequest tenantRequest) throws IOException{
		GenericResponse<Tenant> response = new GenericResponse<>();
		tenantService.updateAllowedOrigins(tenantRequest.getAdminUrl(), tenantRequest.getClientUrl());
		return response.setStatus(Response.Status.OK).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantName(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantName(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantContact(
			@RequestParam(value = "tenantId", required = false) Long tenantId, @RequestParam("value") String value)
			throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantContact(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantEmail(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantEmail(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/tagline", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantTagLine(
			@RequestParam(value = "tenantId", required = false) Long tenantId, @RequestParam("value") String value)
			throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantTagline(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/street", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantStreet(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantStreet(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/city", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantCity(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantCity(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/pincode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantPincode(
			@RequestParam(value = "tenantId", required = false) Long tenantId, @RequestParam("value") String value)
			throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantPincode(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/gstin", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantGstin(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantGstin(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/fssai", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantFssai(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantFssai(value)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PutMapping(value = "/gmap", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> updateTenantGmap(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam("value") String value) throws IOException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(tenantService.updateTenantGmap(value)).build();
	}
	
}
