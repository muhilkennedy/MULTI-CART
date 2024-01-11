package com.product.jpa.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entity.Product;

/**
 * @author Muhil 
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
