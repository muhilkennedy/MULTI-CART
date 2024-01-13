package com.product.entity;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "SUPPLIERPRODUCTS")
@ClassMetaProperty(code = "SUPD")
public class SupplierProducts extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

}
