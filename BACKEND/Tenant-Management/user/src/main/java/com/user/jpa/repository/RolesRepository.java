package com.user.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.Role;

/**
 * @author muhil
 */
@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

}
