package com.product.messages;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;

/**
 *  @author Muhil
 */
public class ProductInventoryRequest {

	@NotNull
	private Long productInfoId;
	@NotBlank
	private String barcode;
	private String expiry;
	private int availableQuantity;
	private int minimumQuantity;
	private boolean autoPurchase;

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

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public int getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(int minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public boolean isAutoPurchase() {
		return autoPurchase;
	}

	public void setAutoPurchase(boolean autoPurchase) {
		this.autoPurchase = autoPurchase;
	}

	public Long getProductInfoId() {
		return productInfoId;
	}

	public void setProductInfoId(Long productInfoId) {
		this.productInfoId = productInfoId;
	}

}
