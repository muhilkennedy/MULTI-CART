package com.product.api;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.product.entity.Category;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;
import com.product.messages.ProductInfoRequest;
import com.product.messages.ProductInventoryRequest;
import com.product.messages.ProductPageResponse;
import com.product.messages.ProductRequest;
import com.product.messages.ProductResponse;
import com.product.service.CategoryService;
import com.product.service.ProductService;
import com.product.util.ProductMeasurement;
import com.supplier.entity.Supplier;
import com.supplier.service.SupplierService;

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
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductResponse> getCompleteProductDetails(@PathVariable("id") Long productId)
			throws IOException {
		GenericResponse<ProductResponse> response = new GenericResponse<>();
		Product product = (Product) productService.findById(productId);
		if (product != null) {
			ProductResponse prodResponse = new ProductResponse(product);
			prodResponse.setCategory((Category) categoryService.findById(product.getCategoryid()));
			prodResponse.setSupplierid((Supplier) supplierService.findById(product.getSupplierid()));
			prodResponse.getInfos().stream().forEach(info -> {
				info.setInventory(productService.getAllProductInventory(info.getRootid()));
			});
			return response.setStatus(Response.Status.OK).setData(prodResponse).build();
		}
		return response.setStatus(Response.Status.OK).build();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductPageResponse<ProductResponse>> getProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "25") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "timecreated") String sortByColumn,
			@RequestParam(value = "includeInactive", required = false) boolean includeInactive,
			@RequestParam(value = "supplierId", required = false) Long supplierId,
			@RequestParam(value = "categoryId", required = false) Long categoryId) throws IOException {
		GenericResponse<ProductPageResponse<ProductResponse>> response = new GenericResponse<>();
		Pageable pageable = BaseUtil.getPageable(sortByColumn, pageNumber, pageSize);
		ProductPageResponse<ProductResponse> page = productService.findAllAdvanced(pageable, includeInactive,
				categoryId, supplierId);
		return response.setStatus(page.getSize() > 0 ? Response.Status.OK : Response.Status.NO_CONTENT).setData(page)
				.build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PRODUCTS })
	@GetMapping(value = "/search/{barcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductResponse> getProductByBarcode(@PathVariable("barcode") String barCode) {
		GenericResponse<ProductResponse> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(productService.findProductByBarcode(barCode)).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.EDIT_PRODUCTS })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Product> addProduct(@Valid @RequestBody ProductRequest request) {
		GenericResponse<Product> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(productService.createProduct(request)).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.EDIT_PRODUCTS })
	@PutMapping(value="/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Product> toggleProductStatus(@PathVariable("id") Long productId) {
		GenericResponse<Product> response = new GenericResponse<>();
		productService.toggleProductState(productId);
		return response.setStatus(Response.Status.OK).build();
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
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.EDIT_PRODUCTS })
	@PostMapping(value = "/image/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Product> uploadProductImage(@RequestParam("file") MultipartFile file,
			@PathVariable("productId") Long productId, @RequestParam("infoId") Long infoId)
			throws IllegalStateException, IOException {
		GenericResponse<Product> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(
				productService.updateProductImage(productId, infoId, BaseUtil.generateFileFromMutipartFile(file)))
				.build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PRODUCTS, Permissions.EDIT_PRODUCTS})
	@GetMapping(value = "/inventory/{infoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductInventory> getProductInventory(@PathVariable("infoId") Long infoId) {
		GenericResponse<ProductInventory> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(productService.getAllProductInventory(infoId))
				.build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PRODUCTS })
	@GetMapping(value = "/measurements", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ProductMeasurement> getMeasurements() {
		GenericResponse<ProductMeasurement> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(Stream.of(ProductMeasurement.values()).toList())
				.build();
	}

}
