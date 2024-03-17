package com.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.product.entity.Category;
import com.product.service.CategoryService;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> getAllCategories() {
		GenericResponse<Category> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(categoryService.getAllCategories()).build();
	}*/

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> getAllCategories() {
		GenericResponse<Category> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(categoryService.getFlattenedCategories()).build();
	}
	
	@GetMapping(value = "/search/{searchtext}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> searchCategoriesd(@PathVariable("searchtext") String searchText) {
		GenericResponse<Category> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(categoryService.searchCategories(searchText)).build();
	}

}
