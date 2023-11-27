package com.user.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.FileStore;
import com.base.server.BaseSession;
import com.base.service.FileStoreService;
import com.base.util.Log;
import com.platform.cache.UserCache;
import com.platform.service.StorageService;
import com.platform.user.Permissions;
import com.platform.util.ImageUtil;
import com.platform.util.SecurityUtil;
import com.user.dao.EmployeeDaoService;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;
import com.user.entity.UserInfo;
import com.user.exception.UserException;
import com.user.service.EmployeeService;

import jakarta.ws.rs.NotFoundException;
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
		Log.user.debug(
				String.format("Generated password for user {%s} is {%s}", employee.getEmailid(), generatedPassword));
		employee.setPassword(BCrypt.hashpw(generatedPassword, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS)));
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
		User emp = (User) empDaoService.findById(rootId);
		if (emp == null) {
			throw new NotFoundException();
		}
		emp.setActive(!emp.isActive());
		return (User) empDaoService.saveAndFlush(emp);
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
		FileStore store = fileService.uploadToFileStore(document, true, "/EmployeeProof/" + employee.getUniquename());
		employee.getEmployeeInfo().setProofFileId(store.getRootId());
		empDaoService.save(employee);
	}

	@Override
	public List<Employee> findMatchingTypeAheadEmployees(String name) {
		return empDaoService.getTypeAheadEmployees(name);
	}

	@Override
	public void initiatePasswordReset(User user) {
		String otp = UUID.randomUUID().toString();
		Log.user.debug("Password reset otp : {} : {}", user.getUniquename(), otp);
		UserCache.getInstance().addOtp(user.getUniquename(), otp);
		emailService.sendPasswordResetEmail(user, otp);
	}

	@Override
	public void resetPassword(User user, String password, String otp) throws UserException {
		if (otp.equals(UserCache.getInstance().getOtp(user.getUniquename()))) {
			user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS)));
			empDaoService.saveAndFlush(user);
			UserCache.getInstance().removeOtp(user.getUniquename());
		} else {
			throw new UserException("Invalid OTP");
		}
	}

	@Override
	public User updateProfilePicture(File file) throws IOException {
		Employee user = (Employee) BaseSession.getUser();
		file = ImageUtil.getPNGThumbnailImage(file);
		String fileUrl = StorageService.getStorage().saveFile(file, user.getUniquename());
		Log.user.debug("Profile picture url : {}", fileUrl);
		user.getEmployeeInfo().setProfilepic(fileUrl);
		return (User) empDaoService.save(user);
	}

}
