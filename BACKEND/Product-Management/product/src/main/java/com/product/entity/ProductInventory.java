package com.product.entity;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PRODUCTINVENTORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "barcode", "tenantid" }) })
@ClassMetaProperty(code = "PINV")
@Indexed(index = "product_inv_index")
public class ProductInventory extends MultiTenantEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCTINFOID", referencedColumnName = "ROOTID", nullable = false, updatable = false)
	private ProductInfo productinfo;
	
	@FullTextField
	@Column(name = "BARCODE")
	private String barcode;
	
	@Column(name = "AVAILABLEQUANTITY")
	private int availablequantity;
	
	@Column(name = "MINIMUMSTOCKLEVEL")
	private int minimumstocklevel;
	
	@Column(name = "AUTOPURCHASE")
	private boolean autoPurchase;
	
	@Column(name = "EXPIRY")
	private String expiry;

	public ProductInfo getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(ProductInfo productinfo) {
		this.productinfo = productinfo;
	}

	public int getAvailablequantity() {
		return availablequantity;
	}

	public void setAvailablequantity(int availablequantity) {
		this.availablequantity = availablequantity;
	}

	public int getMinimumstocklevel() {
		return minimumstocklevel;
	}

	public void setMinimumstocklevel(int minimumstocklevel) {
		this.minimumstocklevel = minimumstocklevel;
	}

	public boolean isAutoPurchase() {
		return autoPurchase;
	}

	public void setAutoPurchase(boolean autoPurchase) {
		this.autoPurchase = autoPurchase;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	
}
