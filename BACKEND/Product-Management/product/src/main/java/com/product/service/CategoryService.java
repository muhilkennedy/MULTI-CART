package com.product.service;

import java.util.List;

import com.base.service.BaseService;
import com.product.entity.Category;
import com.product.messages.CategoryRequest;

/**
 * @author Muhil
 */
public interface CategoryService extends BaseService {

	Category createCategory(CategoryRequest request);

	List<Category> getAllCategories();

}
