package com.base.service;

import java.util.List;
import java.util.Optional;

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

	void deleteById(Long rootId);

}
