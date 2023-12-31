package com.user.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.jpa.repository.BaseReactiveRepository;
import com.user.entity.Employee;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Repository
public interface EmployeeReactiveRepository extends BaseReactiveRepository<Employee> {

	String findByTenantQuery = "select * from Employee where tenantid=:tenantId";

	@Query(findByTenantQuery)
	Flux<Employee> findAll(@Param("tenantId") Long tenantId);
	
	String findUserIdsByTenantQuery = "select rootid from Employee where tenantid=:tenantId";

	@Query(findUserIdsByTenantQuery)
	Flux<Long> findUserIdsByTenant(@Param("tenantId") Long tenantId);


}
