package com.product.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.product.dao.ProductDao;
import com.product.entity.Product;
import com.product.messages.ProductRequest;
import com.product.service.ProductService;

/**
 * @author muhil
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	@Override
	public BaseEntity findById(Long rootId) {
		return productDao.findById(rootId);
	}
	
	public Product createProduct(ProductRequest request) {
		return null;
	}

}
