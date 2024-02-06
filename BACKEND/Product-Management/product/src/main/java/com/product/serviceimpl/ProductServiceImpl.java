package com.product.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.HibernateSearchService;
import com.product.dao.ProductDao;
import com.product.dao.ProductInventoryDao;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;
import com.product.messages.ProductInfoRequest;
import com.product.messages.ProductInventoryRequest;
import com.product.messages.ProductRequest;
import com.product.service.ProductService;

import jakarta.ws.rs.NotFoundException;

/**
 * @author muhil
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private HibernateSearchService searchService;

	@Autowired
	private ProductInventoryDao inventoryDao;

	@Override
	public BaseEntity findById(Long rootId) {
		return productDao.findById(rootId);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productDao.findAll(pageable);
	}

	@Override
	public Product createProduct(ProductRequest request) {
		Product prod = new Product();
		prod.setName(request.getName());
		prod.setCategoryid(request.getCategoryId());
		prod.setMeasurement(request.getMeasurement());
		prod.setDescription(request.getDescription());
		return (Product) productDao.save(prod);
	}

	@Override
	public ProductInfo createProductInfo(ProductInfoRequest request) {
		Product prod = (Product) findById(request.getProductId());
		if (prod == null) {
			throw new NotFoundException("Product Not Found");
		}
		ProductInfo info = new ProductInfo();
		info.setDiscount(request.getDiscount());
		info.setMrp(request.getMrp());
		info.setPrice(request.getPrice());
		info.setSize(request.getSize());
		info.setProduct(prod);
		productDao.saveProductInfo(info);
		return info;
	}

	@Override
	public ProductInventory addOrUpdateProductInventory(ProductInventoryRequest request) {
		ProductInfo prodInfo = productDao.findProductInfoById(request.getProductInfoId());
		if (prodInfo == null) {
			throw new NotFoundException("Product information Not Found");
		}
		ProductInventory inventory = inventoryDao.findByBarCode(request.getBarcode());
		if (inventory == null) {
			inventory = new ProductInventory();
		}
		inventory.setBarcode(request.getBarcode());
		inventory.setExpiry(request.getExpiry());
		inventory.setAvailablequantity(request.getAvailableQuantity());
		inventory.setMinimumstocklevel(request.getMinimumQuantity());
		inventory.setAutoPurchase(request.isAutoPurchase());
		inventory.setProductinfo(prodInfo);
		inventoryDao.save(inventory);
		return inventory;
	}
	
	@Override
	public ProductInventory getProductByBarCode(String barCode) {
		return inventoryDao.findByBarCode(barCode);
	}
	
	

}
