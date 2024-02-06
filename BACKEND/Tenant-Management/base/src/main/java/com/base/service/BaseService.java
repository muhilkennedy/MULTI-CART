package com.base.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.entity.BaseEntity;

/**
 * @author Muhil
 *
 */
public interface BaseService {

	public BaseEntity findById(Long rootId);
	
	default Page<?> findAll(Pageable pageable){
		return null;
	}

	default void refreshObjectInCache(BaseEntity entity) {
		Optional.empty();
	};
	
}
