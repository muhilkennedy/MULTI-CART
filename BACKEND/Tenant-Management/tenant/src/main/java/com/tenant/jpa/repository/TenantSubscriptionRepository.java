package com.tenant.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tenant.entity.TenantSubscription;

/**
 * @author Muhil
 */
@Repository
public interface TenantSubscriptionRepository extends JpaRepository<TenantSubscription, Long> {

	String findTSQuery = "select tenantid from TenantSubscription where active = false and startdate >= :date";

	@Query(value = findTSQuery, nativeQuery = true)
	List<Long> findTS(@Param("date") String date);
	
	String findExpireTSQuery = "select tenantid from TenantSubscription where active = true and enddate <= :date";

	@Query(value = findExpireTSQuery, nativeQuery = true)
	List<Long> findExpireTS(@Param("date") String date);

}
