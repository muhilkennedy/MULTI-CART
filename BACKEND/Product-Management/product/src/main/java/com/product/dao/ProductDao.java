package com.product.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.product.entity.Product;
import com.product.jpa.respository.ProductRepository;

/**
 * @author muhil
 */
@Service
public class ProductDao implements BaseDaoService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return productRepository.save((Product) obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return productRepository.saveAndFlush((Product) obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return productRepository.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		productRepository.delete((Product) obj);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public void deleteById(Long rootId) {
		productRepository.deleteById(rootId);
	}

}
