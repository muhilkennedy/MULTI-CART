package com.base.entity;

import com.platform.annotations.ClassMetaProperty;
import com.platform.security.AttributeEncryptor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "CONFIGTYPE")
@ClassMetaProperty(code = "CONF")
public class ConfigType extends MultiTenantEntity {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VAL", length = 5000)
	@Convert(converter = AttributeEncryptor.class)
	private String val;

	@Column(name = "CONFIGTYPE")
	private String configtype;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getConfigtype() {
		return configtype;
	}

	public void setConfigtype(String configtype) {
		this.configtype = configtype;
	}

}
