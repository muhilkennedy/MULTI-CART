package com.product.messages;

import java.math.BigDecimal;

import com.google.firebase.database.annotations.NotNull;

/**
 * @author Muhil
 */
public class ProductInfoRequest {

	@NotNull
	private Long productId;
	private int mrp;
	private BigDecimal discount;
	private int price;
	private String size;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
