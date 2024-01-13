package com.product.entity;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
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
@Table(name = "CATEGORY")
@ClassMetaProperty(code = "CAT")
@Indexed(index = "category_index")
public class Category extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@FullTextField
	@Column(name = "NAME")
	private String name;

	@OneToOne
	@JoinColumn(name = "PARENTID", referencedColumnName = "rootid")
	private Category parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

}
