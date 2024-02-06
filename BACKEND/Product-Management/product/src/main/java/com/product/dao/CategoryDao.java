package com.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.base.service.BaseDaoService;
import com.base.util.CacheUtil;
import com.product.entity.Category;
import com.product.jpa.respository.CategoryRepository;
import com.product.messages.FlattenedCategory;

/**
 * @author Muhil
 */
@Service
public class CategoryDao implements BaseDaoService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CacheManager cacheManager;
	
	@Override
	@CachePut(value = CacheUtil.CATEGORY_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity save(BaseEntity obj) {
		evictCategoriesByTenantUniqueName(BaseSession.getTenantUniqueName());
		return repository.save((Category) obj);
	}

	@Override
	@CachePut(value = CacheUtil.CATEGORY_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		evictCategoriesByTenantUniqueName(BaseSession.getTenantUniqueName());
		return repository.saveAndFlush((Category) obj);
	}

	@Override
	@Cacheable(value = CacheUtil.CATEGORY_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return repository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.CATEGORY_CACHE_NAME, key = "#obj.rootid")
	public void delete(BaseEntity obj) {
		repository.delete((Category) obj);
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

	public List<Category> findAll(String tenantUniqueName) {
		return repository.findAll();
	}
	
	@Override
	public void deleteById(Long rootId) {
		evictCategoriesByTenantUniqueName(BaseSession.getTenantUniqueName());
		repository.deleteById(rootId);
	}
	
	private void evictCategoriesByTenantUniqueName(String uniqueName) {
		cacheManager.getCache(CacheUtil.CATEGORY_CACHE_NAME).evictIfPresent(uniqueName);
	}
	
	/**
	 * @param tenantUniqueName
	 * @return
	 * Note: introduced just for the sake of caching
	 */
	@Cacheable(value = CacheUtil.CATEGORY_CACHE_NAME, key = "#tenantUniqueName")
	public List<FlattenedCategory> getFlattenedCategories(String tenantUniqueName) {
		List<FlattenedCategory> categories = new ArrayList<FlattenedCategory>();
		List<Category> baseCategories = repository.findAllBaseCategory();
		for (Category category : baseCategories) {
			categories.add(getChildCategoriesRecursively(category.getRootid()));
		}
		return categories;
	}
	
	public FlattenedCategory getChildCategoriesRecursively(Long categoryId) {
		Category parent = (Category) findById(categoryId);
		FlattenedCategory fCategory = new FlattenedCategory();
		fCategory.setName(parent.getName());
		fCategory.setRootId(parent.getRootid());
		List<Category> childCategories = repository.findAllChildCategory(categoryId);
		List<FlattenedCategory> fChildCategory = new ArrayList<FlattenedCategory>();
		if (childCategories != null) {
			for (Category childCategory : childCategories) {
				fChildCategory.add(getChildCategoriesRecursively(childCategory.getRootid()));
			}
		}
		fCategory.setChildren(fChildCategory);
		return fCategory;
	}

}
