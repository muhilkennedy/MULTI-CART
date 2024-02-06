package com.product.jpa.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.entity.ProductInfo;

/**
 * @author Muhil
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
	

}
