package com.product.service;

import com.base.service.BaseService;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;
import com.product.messages.ProductInfoRequest;
import com.product.messages.ProductInventoryRequest;
import com.product.messages.ProductRequest;

public interface ProductService extends BaseService {

	Product createProduct(ProductRequest request);

	ProductInfo createProductInfo(ProductInfoRequest request);

	ProductInventory addOrUpdateProductInventory(ProductInventoryRequest request);

	ProductInventory getProductByBarCode(String barCode);

}
