package com.base.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.entity.BaseEntity;

/**
 * @author Muhil
 * Has to be implemented by all DAO.
 *
 */
public interface BaseDaoService {
	
	public BaseEntity save(BaseEntity obj);

	public BaseEntity saveAndFlush(BaseEntity obj);

	public BaseEntity findById(Long rootId);

	public void delete(BaseEntity obj);

	public List<?> findAll();

	default public Page<?> findAll(Pageable pageable) {
		return null;
	};

	void deleteById(Long rootId);

}
