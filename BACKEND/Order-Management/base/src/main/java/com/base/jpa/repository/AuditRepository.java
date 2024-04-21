package com.base.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base.entity.Audit;
/**
 * @author Muhil
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
	

}
