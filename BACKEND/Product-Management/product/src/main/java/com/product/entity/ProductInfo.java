package com.product.entity;

import java.math.BigDecimal;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PRODUCTINFO")
@ClassMetaProperty(code = "PINF")
@Indexed(index = "product_info_index")
public class ProductInfo extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	@FullTextField
	@Column(name = "BARCODE")
	private String barcode;
	
	@Column(name = "MRP")
	private int mrp;
	
	@Column(name = "DISCOUNT")
	private BigDecimal discount;
	
	@Column(name = "PRICE")
	private int price;
	
	@Column(name = "EXPIRY")
	private String expiry;
	
	@Column(name = "IMAGE")
	private String image;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ROOTID", nullable = false, updatable = false)
	private Product product;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getMrp() {
		return mrp;
	}

	public void setMrp(int mrp) {
		this.mrp = mrp;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
