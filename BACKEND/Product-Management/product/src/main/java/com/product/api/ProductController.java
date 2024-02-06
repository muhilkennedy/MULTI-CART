package com.product.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.util.BaseUtil;
import com.platform.messages.GenericResponse;
import com.product.entity.Product;
import com.product.service.ProductService;
import com.platform.messages.Response;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<Product>> getProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "25") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "timecreated") String sortByColumn) throws IOException {
		GenericResponse<Page<Product>> response = new GenericResponse<>();
		Pageable pageable = BaseUtil.getPageable(sortByColumn, pageNumber, pageSize);
		Page<Product> page = (Page<Product>) productService.findAll(pageable);
		if (page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Product> getProductInfo(@PathVariable("id") Long productId) throws IOException {
		GenericResponse<Product> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData((Product) productService.findById(productId)).build();
	}

}
