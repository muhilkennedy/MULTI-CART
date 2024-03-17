package com.product.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.base.util.CacheUtil;
import com.product.entity.ProductInventory;
import com.product.jpa.respository.ProductInventoryRepository;

/**
 * @author Muhil
 */
@Service
public class ProductInventoryDao implements BaseDaoService {

	@Autowired
	private ProductInventoryRepository inventoryRepo;
	
	@Autowired
	private CacheManager cacheManager;
	
	private void evictProductInvByBarCode(String barCode) {
		cacheManager.getCache(CacheUtil.INVENTORY_CACHE_NAME).evictIfPresent(barCode);
	}

	@Override
	@CachePut(value = CacheUtil.INVENTORY_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity save(BaseEntity obj) {
		ProductInventory pInv = (ProductInventory) obj;
		evictProductInvByBarCode(pInv.getBarcode());
		return inventoryRepo.save(pInv);
	}

	@Override
	@CachePut(value = CacheUtil.INVENTORY_CACHE_NAME, key = "#obj.rootid")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		ProductInventory pInv = (ProductInventory) obj;
		evictProductInvByBarCode(pInv.getBarcode());
		return inventoryRepo.saveAndFlush(pInv);
	}

	@Override
	@Cacheable(value = CacheUtil.INVENTORY_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return inventoryRepo.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.INVENTORY_CACHE_NAME, key = "#obj.rootid")
	public void delete(BaseEntity obj) {
		ProductInventory pInv = (ProductInventory) obj;
		evictProductInvByBarCode(pInv.getBarcode());
		inventoryRepo.delete((ProductInventory) obj);
	}

	@Override
	public List<?> findAll() {
		return inventoryRepo.findAll();
	}

	@Override
	@CacheEvict(value = CacheUtil.INVENTORY_CACHE_NAME, key = "#obj.rootid")
	public void deleteById(Long rootId) {
		inventoryRepo.deleteById(rootId);
	}
	
	@Cacheable(value = CacheUtil.INVENTORY_CACHE_NAME, key = "#barcode")
	public ProductInventory findByBarCode(String barcode) {
		return inventoryRepo.findByBarCode(barcode);
	}
	
	public List<ProductInventory> getAllProductInventory(long infoId) {
		return inventoryRepo.findAllProductInventory(infoId);
	}

	public List<ProductInventory> getActiveProductInventory(long infoId) {
		return inventoryRepo.findActiveProductInventory(infoId);
	}

}
