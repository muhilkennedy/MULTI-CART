package com.product.entity;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PRODUCTINVENTORY")
@ClassMetaProperty(code = "PINV")
public class ProductSupplierInfo  extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "PRODUCTID")
	private Long productid;
	
	@Column(name = "SUPPLIERID")
	private Long supplierid;

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public Long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}

}
