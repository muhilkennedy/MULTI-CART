package com.user.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
import com.base.service.HibernateSearchService;
import com.base.util.Log;
import com.platform.cache.UserCache;
import com.platform.service.StorageService;
import com.platform.user.Permissions;
import com.platform.util.ImageUtil;
import com.platform.util.PlatformUtil;
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

	@Autowired
	private HibernateSearchService searchService;

	@Override
	public BaseEntity findById(Long rootId) {
		return empDaoService.findById(rootId);
	}

	@Override
	public User save(User user) {
		return (User) empDaoService.save(user);
	}
	
	@Override
	public User saveAndFlush(User user) {
		return (User) empDaoService.saveAndFlush(user);
	}
	
	@Override
	public void refreshObjectInCache(BaseEntity entity) {
		empDaoService.refreshObjectInCache((Employee) entity);
	}
	
	@Override
	public User register(User user) {
		Employee employee = (Employee) user;
		String generatedPassword = SecurityUtil.generateRandomPassword();
		Log.user.debug(
				String.format("Generated password for user {%s} is {%s}", employee.getEmailid(), generatedPassword));
		employee.setPassword(BCrypt.hashpw(generatedPassword, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS)));
		employee = (Employee) empDaoService.saveAndFlush(employee);
		return employee;
	}

	@Override
	public User createEmployeeInfo(Employee employee, EmployeeInfo info) {
		employee.setEmployeeInfo(info);
		info.setEmployee(employee);
		UserInfo userInfo = new UserInfo();
		userInfo.setSkipTutorial(false);
		String code = UUID.randomUUID().toString();
		Log.user.debug("Account Activation code : {} : {}", employee.getUniquename(), code);
		userInfo.setActivationCode(code);
		info.setDetails(userInfo);
		User user = (User) empDaoService.save(employee);
		emailService.sendWelcomeActivationEmail(user, PlatformUtil.EMPTY_STRING, code);
		return user;
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
	public User findBySecondaryEmailId(String emailId) {
		return empDaoService.findBySecondaryEmailId(emailId);
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
	public Flux findAllUserIdsReactive() {
		return empDaoService.findAllUserIdsReactive();
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
		employee.getEmployeeInfo().setProofFileId(store.getRootid());
		empDaoService.save(employee);
	}

	@Override
	public List<Employee> findMatchingTypeAheadEmployees(String name) {
		return empDaoService.getTypeAheadEmployees(name);
	}

	@Override
	public void initiatePasswordReset(User user) {
		String otp = passwordOtp(user.getUniquename());
		emailService.sendPasswordResetEmail(user, otp);
	}

	@Override
	public void resetPassword(User user, String password, String otp) throws UserException {
		if (otp.equals(UserCache.getInstance().getOtp(user.getUniquename()))) {
			Log.user.info("User initiated password reset : {}", user.getUniquename());
			user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS)));
			empDaoService.saveAndFlush(user);
			UserCache.getInstance().removeOtp(user.getUniquename());

		}
		else {
			throw new UserException("Invalid OTP");
		}
	}

	@Override
	public User updateProfilePicture(File file) throws IOException {
		Employee user = (Employee) BaseSession.getUser();
		file = ImageUtil.getPNGThumbnailImage(file, true);
		String fileUrl = StorageService.getStorage().saveFile(file, user.getUniquename());
		Log.user.debug("Profile picture url : {}", fileUrl);
		user.getEmployeeInfo().setProfilepic(fileUrl);
		return (User) empDaoService.save(user);
	}

	@Override
	public void sendBirthdayWishesMail() {
		String today = PlatformUtil.SIMPLE_UI_DATE_ONLY_FORMAT.format(new Date());
		List<Employee> employees = empDaoService.findEmployeesByDob(today);
		employees.stream().forEach(employee -> emailService.sendBirthdayWishesEmail(employee));
	}

	private String passwordOtp(String userUniqueName) {
		String otp = UUID.randomUUID().toString();
		Log.user.debug("Password reset otp : {} : {}", userUniqueName, otp);
		UserCache.getInstance().addOtp(userUniqueName, otp);
		return otp;
	}
	
	@Override
	public User updateSecondaryEmail(String email) {
		Employee user = (Employee) BaseSession.getUser();
		user.setSecondaryemail(email);
		return (User) empDaoService.save(user);
	}

	@Override
	public void activateAccount(User user, String password, String otp) throws UserException {
		if(user instanceof Employee) {
			Employee employee = (Employee) user;
			if (otp.equals(employee.getEmployeeInfo().getDetails().getActivationCode())) {
				Log.user.info("Employee account activation password reset : {}", user.getUniquename());
				employee.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS)));
				employee.getEmployeeInfo().getDetails().setActivationCode(null);
				empDaoService.saveAndFlush(employee);
			}
			else {
				throw new UserException("Invalid OTP");
			}
		}
	}

	@Override
	public List searchEmployeesByName(String name, int limit) {
		return searchService.fuzzySearch(Employee.class, name, limit, User.KEY_FNAME, User.KEY_LNAME);
	}

	@Override
	public List searchEmployeesByNameOrEmail(String key, int limit) {
		return searchService.fuzzySearch(Employee.class, key, limit, User.KEY_FNAME, User.KEY_LNAME, User.KEY_EMAILID);
	}

	@Override
	public void updateLocale(String langCode) {
		User user = (User) BaseSession.getUser();
		user.setLocale(langCode);
		empDaoService.save(user);
	}

}
