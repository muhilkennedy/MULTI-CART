package com.product.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.jpa.respository.ProductInfoRepository;
import com.product.jpa.respository.ProductRepository;

/**
 * @author muhil
 */
@Service
public class ProductDao implements BaseDaoService {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductInfoRepository productInfoRepository;

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
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	public Page<Product> findAllActive(Pageable pageable) {
		return productRepository.findAllActiveProducts(pageable);
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

	public ProductInfo saveProductInfo(ProductInfo productInfo) {
		return productInfoRepository.save(productInfo);
	}

	public ProductInfo findProductInfoById(Long productInfoId) {
		return productInfoRepository.findById(productInfoId).get();
	}
	
	public Page<Product> findAll(Pageable pageable, boolean includeInactive, Long categoryId, Long supplierId) {
		if (includeInactive) {
			return productRepository.findAllProductsIncludeInactive(supplierId, categoryId, pageable);
		} else {
			return productRepository.findAllProducts(supplierId, categoryId, pageable);
		}
	}
	
	public Product findProductByBarcode(String barcode) {
		return productRepository.findProductByBarcode(barcode);
	}

}
