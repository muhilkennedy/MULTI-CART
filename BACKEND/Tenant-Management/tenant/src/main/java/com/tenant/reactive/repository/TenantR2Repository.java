package com.tenant.reactive.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.tenant.entity.Tenant;

@Repository
public interface TenantR2Repository extends R2dbcRepository<Tenant, Long> {

}
