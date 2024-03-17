package com.product.entity;

import java.util.List;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PRODUCT")
@ClassMetaProperty(code = "PROD")
@Indexed(index = "product_index")
public class Product extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	public static final String KEY_NAME = "name";
	public static final String KEY_DESC = "description";
	
	@FullTextField
	@Column(name = "NAME")
	private String name;
	
	@FullTextField
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "RATING")
	private int rating;
	
	@Column(name = "CATEGORYID")
	private Long categoryid;
	
	@Column(name = "MEASUREMENT")
	private String measurement;
	
	@Column(name = "SUPPLIERID")
	private Long supplierid;

	//@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductInfo> infos;

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

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public List<ProductInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<ProductInfo> infos) {
		this.infos = infos;
	}

	public Long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}

}
