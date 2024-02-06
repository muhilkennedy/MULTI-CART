package com.product.entity;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "PRODUCTKEYINDEX")
@ClassMetaProperty(code = "PKEYS")
@Indexed(index = "product_keys_index")
public class ProductKeyIndex extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCTID")
	private Long productid;

	@FullTextField
	@Column(name = "SEARCHTEXT")
	private String searchtext;

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public String getSearchtext() {
		return searchtext;
	}

	public void setSearchtext(String searchtext) {
		this.searchtext = searchtext;
	}

}
