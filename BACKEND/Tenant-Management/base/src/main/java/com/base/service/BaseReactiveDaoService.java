package com.base.service;

import java.util.List;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface BaseReactiveDaoService {

	public Flux<?> findAllReactive();
	
	public Flux<?> saveAll(List<?> entities);
	
	public void deleteAll(List<?> entities);
	
}
