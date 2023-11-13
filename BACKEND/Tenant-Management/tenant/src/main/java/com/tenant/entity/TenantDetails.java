package com.tenant.entity;

import java.io.Serializable;

import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.security.AttributeEncryptor;
import com.tenant.model.TenantInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TENANTDETAILS")
@ClassMetaProperty(code = "TD")
public class TenantDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TENANTID", referencedColumnName = "ROOTID", updatable = false)
	private Tenant tenant;
	
	@Column(name = "CONTACT")
	private String contact;
	
	@Column(name = "EMAILID")
	private String emailid;
	
	@Column(name = "STREET")
	private String street;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "PINCODE")
	private String pincode;
	
	@Column(name = "BUSINESSEMAIL")
	@Convert(converter = AttributeEncryptor.class)
	private String businessemail;
	
	@JsonIgnore
	@Convert(converter = AttributeEncryptor.class)
	@Column(name = "BUSINESSEMAILPASSWORD")
	private String businessemailpassword;
	
	@Column(name = "TAGLINE")
	private String tagline;

	@Column(name = "DETAILS", length = 5000)
	@Convert(converter = TenantInfoConvertor.class)
	private TenantInfo details;
	
	public TenantDetails() {
		super();
	}
	
	public TenantDetails(Tenant tenant) {
		super();
		this.tenant = tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getBusinessemail() {
		return businessemail;
	}

	public void setBusinessemail(String businessemail) {
		this.businessemail = businessemail;
	}

	public String getBusinessemailpassword() {
		return businessemailpassword;
	}

	public void setBusinessemailpassword(String businessemailpassword) {
		this.businessemailpassword = businessemailpassword;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public TenantInfo getDetails() {
		return details;
	}

	public void setDetails(TenantInfo details) {
		this.details = details;
	}
	
}
