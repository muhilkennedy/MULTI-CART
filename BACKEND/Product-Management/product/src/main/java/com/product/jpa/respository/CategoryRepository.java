package com.product.jpa.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Category;

/**
 * @author Muhil
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
