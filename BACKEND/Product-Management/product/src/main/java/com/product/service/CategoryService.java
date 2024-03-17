package com.product.service;

import java.util.List;

import com.base.service.BaseService;
import com.product.entity.Category;
import com.product.messages.CategoryRequest;
import com.product.messages.FlattenedCategory;

/**
 * @author Muhil
 */
public interface CategoryService extends BaseService {

	Category createCategory(CategoryRequest request);
	
	void deleteCategory(Long rootId);

	List<Category> getAllCategories();
	
	List<FlattenedCategory> getFlattenedCategories();

	List searchCategories(String text);

}
