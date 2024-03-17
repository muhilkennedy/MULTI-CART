package com.product.messages;

import java.math.BigDecimal;
import java.util.List;

import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;

public class ProductInfoResponse {
	private long rootid;
	private int mrp;
	private BigDecimal discount;
	private int price;
	private String image;
	private String size;
	private List<ProductInventory> inventory;

	public ProductInfoResponse(ProductInfo info) {
		this.rootid = info.getRootid();
		this.mrp = info.getMrp();
		this.discount = info.getDiscount();
		this.price = info.getPrice();
		this.image = info.getImage();
		this.size = info.getSize();
	}

	public long getRootid() {
		return rootid;
	}

	public void setRootid(long rootid) {
		this.rootid = rootid;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<ProductInventory> getInventory() {
		return inventory;
	}

	public void setInventory(List<ProductInventory> inventory) {
		this.inventory = inventory;
	}

}
