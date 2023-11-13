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
	private Long employeeid;
	
	@Column(name = "ROLEID")
	private Long roleid;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "EMPLOYEEID", nullable = false, insertable = false, updatable = false)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "ROLEID", nullable = false, insertable = false, updatable = false)
	private Role role;

	public EmployeeRole()
	{
		super();
	}
	
	public EmployeeRole(Employee employee, Role role) {
		super();
		this.employee = employee;
		this.role = role;
		this.employeeid = employee.getRootId();
		this.roleid = role.getRootId();
	}

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

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	
}