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
public class ProductInventory extends MultiTenantEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "QUANTITY")
	private int quantity;

}
