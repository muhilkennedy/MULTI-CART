package com.tenant.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.base.service.BaseService;
import com.tenant.entity.Tenant;
import com.tenant.messages.TenantRequestBody;

/**
 * @author Muhil
 *
 */
public interface TenantService extends BaseService {
	
	Tenant findByUniqueName(String uniqueName);
	
	List<Tenant> findAllTenants();
	
	Tenant createTenant(TenantRequestBody tenantModel);
	
	Tenant uploadLogo(File logo) throws IOException;
	
}
