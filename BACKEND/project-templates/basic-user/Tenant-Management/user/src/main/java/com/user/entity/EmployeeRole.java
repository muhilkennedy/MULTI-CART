package com.user.entity;

import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name="EMPLOYEEROLE")
@ClassMetaProperty(code = "ER")
public class EmployeeRole extends MultiTenantEntity {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "EMPLOYEEID")
	private String employeeid;
	
	@Column(name = "ROLEID")
	private String roleid;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "EMPLOYEEID", nullable = false, insertable = false, updatable = false)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	private Role role;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmployeeId() {
		return employeeid;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeid = employeeId;
	}

	public String getRoleId() {
		return roleid;
	}

	public void setRoleId(String roleId) {
		this.roleid = roleId;
	}
	
}