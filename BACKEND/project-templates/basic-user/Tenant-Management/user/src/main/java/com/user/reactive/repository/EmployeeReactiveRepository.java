package com.user.reactive.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.Employee;

/**
 * @author Muhil
 *
 */
@Repository
public interface EmployeeReactiveRepository extends R2dbcRepository<Employee, Long> {

}
