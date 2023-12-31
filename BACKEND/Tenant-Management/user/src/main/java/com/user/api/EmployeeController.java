package com.user.api;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.server.BaseSession;
import com.base.util.BaseUtil;
import com.base.util.Log;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.exception.UserNotFoundException;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.platform.util.PlatformUtil;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;
import com.user.messages.EmployeeRequest;
import com.user.service.EmployeeService;
import com.user.service.RolePermissionService;

import jakarta.ws.rs.NotFoundException;
import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("employee")
@ValidateUserToken
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private RolePermissionService rpService;

	@GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> pingUser() {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData((User) BaseSession.getUser()).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@PostMapping(value = "/admin/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> createEmployeeUser(@RequestBody EmployeeRequest empRequest) {
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		// TODO: validations as required.
		employee.setEmailid(empRequest.getEmailId());
		employee.setFname(empRequest.getFname());
		employee.setLname(empRequest.getLname());
		employee.setMobile(empRequest.getMobile());
		employee.setDesignation(PlatformUtil.ADMIN_CUSTOMER_SUPPORT_DESIGNATION);
		employee = (Employee) empService.register(employee);
		EmployeeInfo empInfo = new EmployeeInfo();
		empInfo.setDob(empRequest.getDob());
		empInfo.setGender(empRequest.getGender());
		empService.createEmployeeInfo(employee, empInfo);
		if (empRequest.getRoleIds() != null && !empRequest.getRoleIds().isEmpty()) {
			rpService.addRolesToEmployee(employee, empRequest.getRoleIds());
		}
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> fetchUser(@RequestParam(value = "uniqueName", required = false) String uniqueName,
			@RequestParam(value = "userId", required = false) Long rootId,
			@RequestParam(value = "emailId", required = false) String emailId,
			@RequestParam(value = "tenantId", required = false) Long tenantId) {
		GenericResponse<User> response = new GenericResponse<>();
		empService.sendBirthdayWishesMail();
		if (!StringUtils.isEmpty(uniqueName)) {
			return response.setStatus(Response.Status.OK).setData(empService.findByUniqueName(uniqueName)).build();
		} else if (rootId != null) {
			return response.setStatus(Response.Status.OK).setData((User) empService.findById(rootId)).build();
		} else if (!StringUtils.isEmpty(emailId)) {
			return response.setStatus(Response.Status.OK).setData(empService.findByEmailId(uniqueName)).build();
		} else {
			return response.setStatus(Response.Status.OK).setDataList(empService.findAllUsers()).build();
		}
	}
	
	@UserPermission(values = { Permissions.SUPER_USER })
	@GetMapping(value = "/fetch/csa", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> fetchSuperUsers(@RequestParam(value = "tenantId") Long tenantId) throws SQLException {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(empService.findAllCSAUsers()).build();
	}

	@UserPermission(values = { Permissions.ADMIN })
	@GetMapping(value = "/r2/fetch", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<User> fetchUsersReactive() {
		return empService.findAllUsersReactive();
	}

	@UserPermission(values = { Permissions.SUPER_USER })
	@PutMapping(value = "/togglestate", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> toggleStatus(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestParam(value = "userId", required = true) Long userId) {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(empService.toggleStatus(userId)).build();
	}
	
	/**
	 * Admin Management endpoints
	 * @throws UserNotFoundException 
	 * @throws ParseException 
	 * */
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_USERS })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> createEmployee(@RequestBody EmployeeRequest empRequest) throws UserNotFoundException, ParseException {
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		// TODO: validations as required.
		try {
			PlatformUtil.SIMPLE_UI_DATE_ONLY_FORMAT.parse(empRequest.getDob());
		} catch (ParseException e) {
			Log.user.error("DOB parse exception : {}", e);
			throw e;
		}
		employee.setEmailid(empRequest.getEmailId());
		employee.setFname(empRequest.getFname());
		employee.setLname(empRequest.getLname());
		employee.setMobile(empRequest.getMobile());
		employee.setDesignation(empRequest.getDesignation());
		if (StringUtils.isNotBlank(empRequest.getReportsTo())) {
			User reportsToEmp = empService.findByUniqueName(empRequest.getReportsTo());
			if (reportsToEmp != null) {
				employee.setReportsto(reportsToEmp.getRootid());
			}
		}
		employee = (Employee) empService.register(employee);
		EmployeeInfo empInfo = new EmployeeInfo();
		empInfo.setDob(empRequest.getDob());
		empInfo.setGender(empRequest.getGender());
		empService.createEmployeeInfo(employee, empInfo);
		if (empRequest.getRoleIds() != null && !empRequest.getRoleIds().isEmpty()) {
			rpService.addRolesToEmployee(employee, empRequest.getRoleIds());
		}
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_USERS })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<Employee>> getEmployees(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "timecreated") String sortByColumn) throws IOException {
		GenericResponse<Page<Employee>> response = new GenericResponse<>();
		Pageable pageable = BaseUtil.getPageable(sortByColumn, pageNumber, pageSize);
		Page<Employee> page = (Page<Employee>) empService.findAll(pageable);
		if (page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_USERS })
	@PostMapping(value = "/proof", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> updateEmployeeDocumentProof(@RequestParam("document") MultipartFile document,
			@RequestParam("uniqueName") String uniqueName) throws IllegalStateException, IOException {
		GenericResponse<User> response = new GenericResponse<>();
		Employee emp = (Employee) empService.findByUniqueName(uniqueName);
		if (emp == null) {
			throw new NotFoundException();
		}
		empService.uploadEmployeeDocumentProof(emp, BaseUtil.generateFileFromMutipartFile(document));
		return response.setStatus(Response.Status.OK).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_USERS })
	@GetMapping(value = "/typeahead", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Employee> fetchUsers(@RequestParam(value = "name") String name) throws SQLException {
		GenericResponse<Employee> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(empService.findMatchingTypeAheadEmployees(name))
				.build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_USERS })
	@PostMapping(value = "/profilepic", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> updateProfilePic(@RequestParam("picture") MultipartFile picture)
			throws IllegalStateException, IOException {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(empService.updateProfilePicture(BaseUtil.generateFileFromMutipartFile(picture))).build();
	}
	
	@PutMapping(value = "/secondaryemail", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> updateSecondaryMail(@RequestBody EmployeeRequest request)
			throws IllegalStateException, IOException {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(empService.updateSecondaryEmail(request.getEmailId())).build();
	}

}
