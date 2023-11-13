package com.user.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.EmployeeRole;

/**
 * @author muhil
 */
@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

}
