package com.supplier.entity;

import java.sql.SQLException;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;
import com.supplier.util.SupplierUtil;

import jakarta.persistence.Column;
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
public class Supplier extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "UNIQUENAME")
	private String uniquename;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EMAILID")
	private String emailid;

	@Column(name = "CONTACT")
	private String contact;

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

	@PrePersist
	private void preProcess() throws SQLException {
		setUniquename(SupplierUtil.generateSupplierUniqueName());
	}
}
