package com.tenant.entity;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;
import com.platform.security.AttributeEncryptor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author muhil
 */
@Entity
@Table(name = "TENANTCONFIG")
@ClassMetaProperty(code = "TC")
public class TenantConfig extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "PNAME")
	private String pname;
	
	@Column(name = "PVALUE")
	@Convert(converter = AttributeEncryptor.class)
	private String pvalue;
	
	@Column(name = "PTYPE")
	private String ptype;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

}
