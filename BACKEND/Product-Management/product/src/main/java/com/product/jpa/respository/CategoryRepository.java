package com.product.jpa.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.entity.Category;

/**
 * @author Muhil
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	String findAllBaseCategoryQuery = "select cat from Category cat where parent is null and active = true";

	@Query(findAllBaseCategoryQuery)
	List<Category> findAllBaseCategory();

	String findAllChildCategoryQuery = "select cat from Category cat where parent.rootid = :rootId and active = true";

	@Query(findAllChildCategoryQuery)
	List<Category> findAllChildCategory(@Param("rootId") Long rootId);

}
