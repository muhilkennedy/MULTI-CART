package com.user.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.util.Log;
import com.platform.util.SecurityUtil;
import com.user.dao.EmployeeDaoService;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;
import com.user.exception.UserException;
import com.user.service.EmployeeService;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDaoService empDaoService;
	
	@Autowired
	private EmployeeEmailService emailService;

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

}
