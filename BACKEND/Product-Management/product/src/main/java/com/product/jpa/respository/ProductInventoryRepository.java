package com.product.jpa.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.entity.ProductInventory;

/**
 * @author Muhil
 */
@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
	
	String findByBarCodeQuery = "select inv from ProductInventory inv where barcode=:barcode";

	@Query(findByBarCodeQuery)
	ProductInventory findByBarCode(@Param("barcode") String barCode);
	
	String findAllProductInventoryQuery = "select inv from ProductInventory inv where productinfo.rootid=:infoId";

	@Query(findAllProductInventoryQuery)
	List<ProductInventory> findAllProductInventory(@Param("infoId") Long infoId);
	
	String findActiveProductInventoryQuery = "select inv from ProductInventory inv where active=true and productinfo.rootid=:infoId";

	@Query(findActiveProductInventoryQuery)
	List<ProductInventory> findActiveProductInventory(@Param("infoId") Long infoId);

}
