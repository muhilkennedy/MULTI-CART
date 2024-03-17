package com.product.messages;

import java.util.List;

import com.product.entity.Category;
import com.product.entity.Product;
import com.supplier.entity.Supplier;

/**
 * @author Muhil
 */
public class ProductResponse {

	private long rootid;
	private String name;
	private String description;
	private int rating;
	private String measurement;
	private boolean active;

	private Category category;
	private Supplier supplierid;
	private List<ProductInfoResponse> infos;
	
	public ProductResponse(Product product) {
		this.rootid = product.getRootid();
		this.name = product.getName();
		this.description = product.getDescription();
		this.rating = product.getRating();
		this.measurement = product.getMeasurement();
		this.active = product.isActive();
		if (product.getInfos() != null) {
			this.infos = product.getInfos().stream().map(info -> new ProductInfoResponse(info)).toList();
		}
	}

	public long getRootid() {
		return rootid;
	}

	public void setRootid(long rootid) {
		this.rootid = rootid;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
		this.category.setParent(null);
	}

	public Supplier getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Supplier supplierid) {
		this.supplierid = supplierid;
	}

	public List<ProductInfoResponse> getInfos() {
		return infos;
	}

	public void setInfos(List<ProductInfoResponse> infos) {
		this.infos = infos;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
