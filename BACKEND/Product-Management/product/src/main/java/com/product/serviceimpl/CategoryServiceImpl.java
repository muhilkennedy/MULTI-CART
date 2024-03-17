package com.product.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.base.service.HibernateSearchService;
import com.product.dao.CategoryDao;
import com.product.entity.Category;
import com.product.messages.CategoryRequest;
import com.product.messages.FlattenedCategory;
import com.product.service.CategoryService;

/**
 * @author Muhil
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private HibernateSearchService searchService;

	@Override
	public BaseEntity findById(Long rootId) {
		return categoryDao.findById(rootId);
	}

	@Override
	public Category createCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		if (request.getParentId() != null) {
			Category parent = (Category) findById(request.getParentId());
			if (parent != null) {
				category.setParent(parent);
			}
		}
		return (Category) categoryDao.save(category);
	}
	
	@Override
	public List<Category> getAllCategories(){
		return categoryDao.findAll(BaseSession.getTenantUniqueName());
	}

	@Override
	public List<FlattenedCategory> getFlattenedCategories() {
		return categoryDao.getFlattenedCategories(BaseSession.getTenantUniqueName());
	}

	@Override
	public void deleteCategory(Long rootId) {
		categoryDao.deleteById(rootId);
	}
	
	@Override
	public List searchCategories(String text) {
		return searchService.fuzzySearch(Category.class, text, Integer.MAX_VALUE, Category.KEY_NAME);
	}
}
