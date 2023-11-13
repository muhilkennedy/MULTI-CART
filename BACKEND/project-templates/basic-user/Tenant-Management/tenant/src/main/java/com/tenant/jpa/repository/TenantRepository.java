package com.tenant.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tenant.entity.Tenant;

/**
 * @author Muhil
 *
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

	String getTenantByUniqueNameQuery = "select * from Tenant where uniquename = :uniqueName";

	@Query(value = getTenantByUniqueNameQuery, nativeQuery = true)
	Tenant findTenantByUniqueName(@Param("uniqueName") String uniqueName);
}
