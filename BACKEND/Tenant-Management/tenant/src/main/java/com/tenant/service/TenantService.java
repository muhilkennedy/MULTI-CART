package com.tenant.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.base.service.BaseService;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;
import com.tenant.messages.TenantRequestBody;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface TenantService extends BaseService {
	
	Tenant findByUniqueName(String uniqueName);
	
	List<Tenant> findAllTenants();
	
	Tenant createTenant(TenantRequestBody tenantModel);
	
	Tenant uploadLogo(File logo) throws IOException;
	
	void toggleTenantState(String uniqueName);

	Flux<TenantSubscription> getAllTenantSubscription(Long tenantId);

	TenantSubscription getActiveTenantSubscription(Long tenantId);

	TenantSubscription addTenantSubscription(TenantRequestBody tenantModel);

	void checkAndRenewTenant();

	void createStorageConfig(String config, String defaultBucket, String type) throws IOException;

	Tenant updateAllowedOrigins(String adminUrl, String clientUrl);
	
	Tenant updateTenantName(String value);
	
	Tenant updateTenantEmail(String value);
	
	Tenant updateTenantContact(String value);
	
	Tenant updateTenantTagline(String value);
	
	Tenant updateTenantStreet(String value);
	
	Tenant updateTenantCity(String value);
	
	Tenant updateTenantPincode(String value);
	
	Tenant updateTenantFssai(String value);
	
	Tenant updateTenantGstin(String value);
	
	Tenant updateTenantGmap(String value);
	
}
