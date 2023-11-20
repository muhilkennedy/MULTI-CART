package com.user.entity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;
import com.platform.entity.BasePermissions;
import com.platform.user.Permissions;
import com.user.util.UserUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "EMPLOYEE")
@ClassMetaProperty(code = "EMP")
public class Employee extends User implements BasePermissions {

	private static final long serialVersionUID = 2L;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "REPORTSTO")
	private Long reportsto;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<EmployeeRole> employeeeRoles;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private EmployeeInfo employeeInfo;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getReportsto() {
		return reportsto;
	}

	public void setReportsto(Long reportsto) {
		this.reportsto = reportsto;
	}

	public List<EmployeeRole> getEmployeeeRoles() {
		return employeeeRoles;
	}

	public void setEmployeeeRoles(List<EmployeeRole> employeeeRoles) {
		this.employeeeRoles = employeeeRoles;
	}

	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}
	
	@Override
	protected void generateUniqueName() throws SQLException {
		setUniquename(UserUtil.generateEmployeeUniqueName());
	}

	@Override
	public Set<Permissions> getUserPermissions() {
		try {
			Optional<Set<Permissions>> obj = employeeeRoles.stream()
					.map(role -> role.getRole().getPermissions().stream()
							.map(rp -> Permissions.getPermissionIfValid(rp.getPermission().getPermission()))
							.collect(Collectors.toSet()))
					.findFirst();
			return obj.get();
		} catch (RuntimeException e) {
			// Not a good idea, lets take a look to have a defensive fix here
			return super.getUserPermissions();
		}
	}

}
