package com.product.jpa.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.entity.Product;

/**
 * @author Muhil
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	String findAllActiveProductsQuery = "select prod from Product prod where active = true";

	@Query(findAllActiveProductsQuery)
	Page<Product> findAllActiveProducts(Pageable pageale);

	String findAllProductsQuery = "select prod from Product prod where active = true and (:supplierid is null or supplierid = :supplierid) and (:categoryid is null or categoryid = :categoryid)";

	@Query(findAllProductsQuery)
	Page<Product> findAllProducts(@Param("supplierid") Long supplierId, @Param("categoryid") Long categoryId,
			Pageable pageale);

	String findAllProductsIncludeInactiveQuery = "select prod from Product prod where (:supplierid is null or supplierid = :supplierid) and (:categoryid is null or categoryid = :categoryid)";

	@Query(findAllProductsIncludeInactiveQuery)
	Page<Product> findAllProductsIncludeInactive(@Param("supplierid") Long supplierId,
			@Param("categoryid") Long categoryId, Pageable pageale);

	String findProductByBarcodeQuery = "select prod from Product as prod inner join ProductInfo as info on prod.rootid = info.product.rootid "
			+ "inner join ProductInventory as inv on info.rootid = inv.productinfo.rootid where inv.barcode = :barcode";

	@Query(findProductByBarcodeQuery)
	Product findProductByBarcode(@Param("barcode") String barCode);

}
