package com.user.entity;

import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "EMPLOYEEINFO")
@ClassMetaProperty(code = "EMPINFO")
public class EmployeeInfo extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "PROOFBLOBID")
	private Long proofFileId;
	
	@Column(name = "DETAILS", length = 5000)
	@Convert(converter = UserInfoConvertor.class)
	private UserInfo details;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "EMPLOYEEID", referencedColumnName = "ROOTID", nullable = false, updatable = false)
	private Employee employee;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public UserInfo getDetails() {
		return details;
	}

	public void setDetails(UserInfo details) {
		this.details = details;
	}

	public Long getProofFileId() {
		return proofFileId;
	}

	public void setProofFileId(Long proofFileId) {
		this.proofFileId = proofFileId;
	}

}
