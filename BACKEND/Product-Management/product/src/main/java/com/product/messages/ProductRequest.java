package com.product.messages;

import java.io.Serializable;

import com.product.validators.ValidMeasurement;

import jakarta.validation.constraints.NotNull;

/**
 * @author Muhil
 */
public class ProductRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long categoryId;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@ValidMeasurement()
	private String measurement;
	private Long supplierId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
