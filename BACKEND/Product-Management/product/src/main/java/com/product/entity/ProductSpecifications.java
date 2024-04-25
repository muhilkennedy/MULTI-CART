package com.product.entity;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PRODUCTSPECIFICATIONS")
@ClassMetaProperty(code = "PSPEC")
public class ProductSpecifications extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCTINFOID")
	private Long productinfoid;

	@Column(name = "SPECIFICATIONS", length = 5000)
	@Convert(converter = ProductSpecsConvertor.class)
	private ProductSpecs specifications;

	@Column(name = "IMAGES", length = 5000)
	@Convert(converter = ImagesConvertor.class)
	private ProductImages images;

	public Long getProductInfoId() {
		return productinfoid;
	}

	public void setProductInfoId(Long productInfoId) {
		this.productinfoid = productInfoId;
	}

	public ProductSpecs getSpecifications() {
		return specifications;
	}

	public void setSpecifications(ProductSpecs specifications) {
		this.specifications = specifications;
	}

	public ProductImages getImages() {
		return images;
	}

	public void setImages(ProductImages images) {
		this.images = images;
	}

}
