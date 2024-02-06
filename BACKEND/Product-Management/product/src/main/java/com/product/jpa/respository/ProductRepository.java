package com.product.jpa.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Product;

/**
 * @author Muhil 
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
