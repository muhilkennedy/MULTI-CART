package com.product.jpa.respository;

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

}
