package com.product.jpa.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.entity.ProductSpecifications;

/**
 * @author Muhil
 */
@Repository
public interface  ProductSpecificationsRepository extends JpaRepository<ProductSpecifications, Long> {

	String findByProductInfoQuery = "select specs from ProductSpecifications specs where productinfoid=:infoId";

	@Query(findByProductInfoQuery)
	ProductSpecifications findByProductInfo(@Param("infoId") Long infoId);
	
	
}
