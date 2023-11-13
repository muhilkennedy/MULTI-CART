package com.user.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.FileStore;
import com.base.service.FileStoreService;
import com.base.util.Log;
import com.platform.user.Permissions;
import com.platform.util.SecurityUtil;
import com.user.dao.EmployeeDaoService;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;
import com.user.entity.UserInfo;
import com.user.exception.UserException;
import com.user.service.EmployeeService;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("EmployeeService")
@Primary
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDaoService empDaoService;
	
	@Autowired
	private EmployeeEmailService emailService;
	
	@Autowired
	private FileStoreService fileService;

	@Override
	public BaseEntity findById(Long rootId) {
		return empDaoService.findById(rootId);
	}

	@Override
	public User register(User user) {
		Employee employee = (Employee) user;
		String generatedPassword = SecurityUtil.generateRandomPassword();
		Log.user.debug(String.format("Generated password for user {%s} is {%s}", employee.getEmailid(), generatedPassword));
		employee.setPassword(generatedPassword);
		employee = (Employee) empDaoService.saveAndFlush(employee);
		emailService.sendWelcomeActivationEmail(user, generatedPassword);
		return employee;
	}
	
	@Override
	public User createEmployeeInfo(Employee employee, EmployeeInfo info) {
		employee.setEmployeeInfo(info);
		info.setEmployee(employee);
		UserInfo userInfo = new UserInfo();
		userInfo.setSkipTutorial(false);
		info.setDetails(userInfo);
		return (User) empDaoService.saveAndFlush(employee);
	}

	@Override
	public User login(User user) throws UserException {
		Employee employee = (Employee) empDaoService.findUserForLogin(user);
		if(employee == null) {
			throw new UserException("User Not Found");
		}
		if (!employee.isActive()) {
			throw new UserException(String.format("Employee %s Account is deactivated!", employee.getUniquename()));
		}
		if(!BCrypt.checkpw(user.getPassword(), employee.getPassword())) {
			throw new UserException("Invalid Password");
		}
		return employee;
	}

	@Override
	public User findByEmailId(String emailId) {
		return empDaoService.findByEmailId(emailId);
	}

	@Override
	public User findByUniqueName(String uniqueName) {
		return empDaoService.findByUniqueName(uniqueName);
	}

	@Override
	public User toggleStatus(Long rootId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllUsers() {
		return (List<User>) empDaoService.findAll();
	}

	@Override
	public Flux findAllUsersReactive() {
		return empDaoService.findAllReactive();
	}

	@Override
	public List<Employee> findAllCSAUsers() {
		return empDaoService.findEmployeesWithPermission(Permissions.ADMIN);
	}
	
	@Override
	public boolean doesEmployeeHavePermission(Permissions permission, Long employeeId) {
		Employee emp = empDaoService.findEmployeeWithPermission(permission, employeeId);
		return (emp != null);
	}

	@Override
	public Page<Employee> findAll(Pageable pageable) {
		return empDaoService.findAll(pageable);
	}

	@Override
	public void uploadEmployeeDocumentProof(Employee employee, File document) throws IOException {
		FileStore store = fileService.uploadToFileStore(document, true, "/Proof");
		employee.getEmployeeInfo().setProofFileId(store.getRootId());
		empDaoService.save(employee);
	}

}
