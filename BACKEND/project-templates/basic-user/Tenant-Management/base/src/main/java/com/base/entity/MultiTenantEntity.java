package com.base.entity;

import java.io.Serializable;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.base.hibernate.configuration.MultiTenantEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Muhil
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(MultiTenantEntityListener.class)
//@FilterDef(name = "tenantFilter", parameters = { @ParamDef(name = "tenantid", type = Long.class) })
//@Filter(name = "tenantFilter", condition = "tenantid = :tenantid")
public class MultiTenantEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TENANTID", updatable = false, nullable = false)
	private Long tenantid;

	public Long getTenantid() {
		return tenantid;
	}

	public void setTenantid(Long tenantid) {
		this.tenantid = tenantid;
	}

}
