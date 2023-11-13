package com.tenant.reactive.repository;


import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.jpa.repository.BaseReactiveRepository;
import com.tenant.entity.TenantSubscription;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author muhil
 */
@Repository
public interface TenantSubscriptionR2Repository extends BaseReactiveRepository<TenantSubscription> {

	String getTenantSubscriptionQuery = "select * from TenantSubscription where tenantid = :tenantId";

	@Query(value = getTenantSubscriptionQuery)
	Flux<TenantSubscription> findllTenantSubscription(@Param("tenantId") Long tenantId);

	String getActiveTenantSubscriptionQuery = "select * from TenantSubscription where tenantid = :tenantId and active=true";

	@Query(value = getActiveTenantSubscriptionQuery)
	Mono<TenantSubscription> findActiveTenantSubscription(@Param("tenantId") Long tenantId);

}
