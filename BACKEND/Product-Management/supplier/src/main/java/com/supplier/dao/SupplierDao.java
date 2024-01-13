package com.supplier.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.supplier.entity.Supplier;
import com.supplier.jpa.repository.SupplierRepository;

/**
 * @author muhil
 */
@Service
public class SupplierDao implements BaseDaoService {
	
	@Autowired
	private SupplierRepository repository;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return repository.save((Supplier) obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return repository.saveAndFlush((Supplier) obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return repository.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		repository.delete((Supplier) obj);
	}

	@Override
	public List<Supplier> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteById(Long rootId) {
		repository.deleteById(rootId);
	}

}
