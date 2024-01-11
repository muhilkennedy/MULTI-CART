package com.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.product.entity.Category;
import com.product.messages.CategoryRequest;
import com.product.service.CategoryService;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/category")
@ValidateUserToken
public class CategoryAdminController {

	@Autowired
	private CategoryService categoryService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> addCategory(@RequestBody CategoryRequest request) {
		GenericResponse<Category> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(categoryService.createCategory(request)).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Category> getAllCategories(@RequestBody CategoryRequest request) {
		GenericResponse<Category> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(categoryService.getAllCategories()).build();
	}

}
