package com.user.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Employee;

/**
 * @author Muhil
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	String findByEmailQuery = "select emp from Employee emp where emailid=:emailId";

	@Query(findByEmailQuery)
	Employee findByEmailId(@Param("emailId") String emailId);

	String findUserForLoginQuery = "select emp from Employee emp where emailid=:emailId or mobile=:mobile or uniquename=:uniqueName";

	@Query(findUserForLoginQuery)
	Employee findUserForLogin(@Param("emailId") String emailId, @Param("mobile") String mobile,
			@Param("uniqueName") String uniqueName);

	String findByUniqueNameQuery = "select emp from Employee emp where uniquename=:uniqueName";

	@Query(findByUniqueNameQuery)
	Employee findByUniqueName(@Param("uniqueName") String uniqueName);

	String findEmployeesWithPermissionQuery = "select e from Employee e join EmployeeRole er on e.rootid=er.employeeid inner join Role r on er.roleid=r.rootid "
			+ "inner join RolePermission rp on r.rootid=rp.roleid inner join Permission p on p.rootid=rp.permissionid where p.permission= :permission";

	@Query(findEmployeesWithPermissionQuery)
	List<Employee> findEmployeesWithPermission(@Param("permission") String permission);

	String findEmployeeWithPermissionQuery = "select e from Employee e join EmployeeRole er on e.rootid=er.employeeid inner join Role r on er.roleid=r.rootid "
			+ "inner join RolePermission rp on r.rootid=rp.roleid inner join Permission p on p.rootid=rp.permissionid where p.permission= :permission and e.rootid= :rootId";

	@Query(findEmployeeWithPermissionQuery)
	Employee findEmployeeWithPermission(@Param("permission") String permission, @Param("rootId") Long rootId);

}
