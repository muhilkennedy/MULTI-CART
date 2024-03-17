package com.product.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.service.BaseService;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;
import com.product.messages.ProductInfoRequest;
import com.product.messages.ProductInventoryRequest;
import com.product.messages.ProductPageResponse;
import com.product.messages.ProductRequest;
import com.product.messages.ProductResponse;

/**
 * @author Muhil 
 */
public interface ProductService extends BaseService {

	Product createProduct(ProductRequest request);

	ProductInfo createProductInfo(ProductInfoRequest request);

	ProductInventory addOrUpdateProductInventory(ProductInventoryRequest request);
	
	List<ProductInventory> getAllProductInventory(long infoId);
	
	List<ProductInventory> getActiveProductInventory(long infoId);

	Product updateProductImage(Long productId, Long infoId, File image) throws IOException;

	List searchProductByName(String text);

	ProductPageResponse<ProductResponse> findAllAdvanced(Pageable pageable, boolean includeInactive, Long categoryId, Long supplierId);

	Page<Product> findAllActive(Pageable pageable);

	ProductResponse findProductByBarcode(String barcode);

	Product toggleProductState(Long productId);

}
