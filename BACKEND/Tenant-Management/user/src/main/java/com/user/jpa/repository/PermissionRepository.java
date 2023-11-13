package com.user.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Permission;

/**
 * @author muhil
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	String findPermissionForRoleQuery = "select p from Permission p inner join RolePermission rp on p.rootid=rp.permissionid inner join Role r on r.rootid=rp.roleid where r.rootid = :roleId";

	@Query(findPermissionForRoleQuery)
	List<Permission> findPermissionForRole(@Param("roleId") Long roleId);
}
