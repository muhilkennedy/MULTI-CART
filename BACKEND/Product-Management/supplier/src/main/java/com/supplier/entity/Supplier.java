package com.supplier.entity;

import java.sql.SQLException;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;
import com.platform.annotations.PIIData;
import com.platform.security.AttributeEncryptor;
import com.platform.user.Permissions;
import com.supplier.util.SupplierUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "SUPPLIER")
@ClassMetaProperty(code = "SUP")
@Indexed(index = "supplier_index")
public class Supplier extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@FullTextField
	@Column(name = "NAME")
	private String name;

	@Column(name = "UNIQUENAME")
	private String uniquename;

	@FullTextField
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "ADDRESS")
	private String address;

	@PIIData(allowedRolePermissions = { Permissions.ADMIN })
	@Column(name = "EMAILID")
	private String emailid;

	@Convert(converter = AttributeEncryptor.class)
	@Column(name = "CONTACT")
	private String contact;

	@Convert(converter = AttributeEncryptor.class)
	@Column(name = "SECONDARYCONTACT")
	private String secondarycontact;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSecondarycontact() {
		return secondarycontact;
	}

	public void setSecondarycontact(String secondarycontact) {
		this.secondarycontact = secondarycontact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@PrePersist
	private void preProcess() throws SQLException {
		setUniquename(SupplierUtil.generateSupplierUniqueName());
	}
}
