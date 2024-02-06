package com.user.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Role;

/**
 * @author muhil
 */
@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
	
	String findAllEmployeeRolesQuery = "select r from Role r where rootid in (select er.roleid from EmployeeRole er where employeeid = :employeeId)";
	
	@Query(findAllEmployeeRolesQuery)
	List<Role> findAllEmployeeRoles(@Param("employeeId") Long employeeId);

}
