package com.user.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.EmployeeRole;

/**
 * @author muhil
 */
@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

	String deleteEmployeeRolesQuery = "delete from EmployeeRole where role.rootid in :roleIds and employee.rootid = :employeeId";
	
	@Modifying
	@Query(deleteEmployeeRolesQuery)
	void deleteEmployeeRoles(@Param("roleIds") List<Long> roleIds, @Param("employeeId") Long employeeId);
	
}
