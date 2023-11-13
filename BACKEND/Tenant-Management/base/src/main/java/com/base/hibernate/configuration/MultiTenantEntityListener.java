package com.base.hibernate.configuration;

import org.springframework.util.Assert;

import com.base.entity.MultiTenantEntity;
import com.base.server.BaseSession;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;

/**
 * @author Muhil
 * manipulate basic required multitenant fields before persist. 
 *
 */
public class MultiTenantEntityListener {

	@PrePersist
	private void prePersist(Object object) {
		if (object instanceof MultiTenantEntity) {
			MultiTenantEntity entity = (MultiTenantEntity) object;
			Assert.isTrue(BaseSession.getTenant() != null, "Tenant is not set. Please setup base session.");
			entity.setTenantid(BaseSession.getTenantId());
		}
	}

	@PreRemove
	private void preRemove(Object object) {
		if (object instanceof MultiTenantEntity) {
			MultiTenantEntity entity = (MultiTenantEntity) object;
			Assert.isTrue(BaseSession.getTenant() != null, "Tenant is not set. Please setup base session.");
			entity.setTenantid(BaseSession.getTenantId());
		}
	}

}
