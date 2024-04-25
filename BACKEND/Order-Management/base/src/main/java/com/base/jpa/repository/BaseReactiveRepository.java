package com.base.jpa.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author Muhil
 * implement to handle tenant based data partition for reactive queries.
 * 
 */
public interface BaseReactiveRepository<T> extends R2dbcRepository<T, Long> {

}
