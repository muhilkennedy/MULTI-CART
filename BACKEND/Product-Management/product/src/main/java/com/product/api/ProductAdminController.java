package com.product.api;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;
import com.product.messages.ProductInfoRequest;
import com.product.messages.ProductInventoryRequest;
import com.product.messages.ProductRequest;
import com.product.service.ProductService;
import com.product.util.ProductMeasurement;

import jakarta.validation.Valid;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/product")
@ValidateUserToken
public class ProductAdminController {

	@Autowired
	private ProductService productService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.EDIT_PRODUCTS })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Product> addProduct(@Valid @RequestBody ProductRequest request) {
		GenericResponse<Product> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(productService.createProduct(request)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.EDIT_PRODUCTS })
	@PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductInfo> addProductInfo(@Valid @RequestBody ProductInfoRequest request) {
		GenericResponse<ProductInfo> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(productService.createProductInfo(request)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.EDIT_PRODUCTS })
	@PostMapping(value = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductInventory> addProductInventory(@Valid @RequestBody ProductInventoryRequest request) {
		GenericResponse<ProductInventory> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(productService.addOrUpdateProductInventory(request))
				.build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PRODUCTS, Permissions.POINT_OF_SALE })
	@GetMapping(value = "/{barcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductInventory> getProduct(@PathVariable("barcode") String barCode) {
		GenericResponse<ProductInventory> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(productService.getProductByBarCode(barCode)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PRODUCTS })
	@GetMapping(value = "/measurements", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductMeasurement> getMeasurements() {
		GenericResponse<ProductMeasurement> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(Stream.of(ProductMeasurement.values()).toList())
				.build();
	}

}
