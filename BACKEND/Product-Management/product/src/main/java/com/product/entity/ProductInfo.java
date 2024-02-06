package com.product.entity;

import java.math.BigDecimal;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column(name = "MRP")
	private int mrp;
	
	@Column(name = "DISCOUNT")
	private BigDecimal discount;
	
	@Column(name = "PRICE")
	private int price;
	
	@Column(name = "IMAGE")
	private String image;
	
	@Column(name = "SIZE")
	private String size;

	//@JsonBackReference
	@JsonIgnoreProperties(value = {"infos"})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ROOTID", nullable = false, updatable = false)
	private Product product;

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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}
