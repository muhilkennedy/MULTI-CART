package com.user.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.platform.user.Permissions;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;

/**
 * @author Muhil
 *
 */
public interface EmployeeService extends UserService {

	List<Employee> findAllCSAUsers();

	boolean doesEmployeeHavePermission(Permissions permission, Long employeeId);

	User createEmployeeInfo(Employee employee, EmployeeInfo info);
	
	void uploadEmployeeDocumentProof(Employee employee, File document) throws IOException;

}
