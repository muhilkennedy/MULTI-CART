package com.user.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.server.BaseSession;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.*;
import com.platform.util.PlatformUtil;
import com.user.entity.Employee;
import com.user.entity.EmployeeInfo;
import com.user.entity.User;
import com.user.messages.EmployeeRequest;
import com.user.service.EmployeeService;

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
	
	@GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> pingUser()
	{
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData((User)BaseSession.getUser()).build();
	}
	
	@PostMapping(value = "/admin/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> createEmployeeUser(@RequestBody EmployeeRequest empRequest){
		GenericResponse<User> response = new GenericResponse<>();
		Employee employee = new Employee();
		//TODO: validations as required.
		employee.setEmailid(empRequest.getEmailId());
		employee.setFname(empRequest.getFname());
		employee.setLname(empRequest.getLname());
		employee.setMobile(empRequest.getMobile());
		employee.setDesignation(PlatformUtil.ADMIN_SUPPORT_DESIGNATION);
		employee = (Employee) empService.register(employee);
		EmployeeInfo empInfo = new EmployeeInfo();
		empInfo.setDob(empRequest.getDob());
		empInfo.setGender(empRequest.getGender());
		empService.createEmployeeInfo(employee, empInfo);
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}
	
	@GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> fetchUser(@RequestParam(value = "uniqueName", required = false) String uniqueName,
			@RequestParam(value = "rootId", required = false) Long rootId,
			@RequestParam(value = "emailId", required = false) String emailId) {
		GenericResponse<User> response = new GenericResponse<>();
		if (!StringUtils.isEmpty(uniqueName)) {
			return response.setStatus(Response.Status.OK).setData(empService.findByUniqueName(uniqueName)).build();
		} else if (rootId != null) {
			return response.setStatus(Response.Status.OK).setData((User) empService.findById(rootId)).build();
		} else if (!StringUtils.isEmpty(emailId)) {
			return response.setStatus(Response.Status.OK).setData(empService.findByEmailId(uniqueName)).build();
		} else {
			List<User> emp = empService.findAllUsers();
			return response.setStatus(Response.Status.OK).setDataList(emp).build();
		}
	}
	
	@GetMapping(value = "/fetch/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<User> fetchUsersReactive(){
		return empService.findAllUsersReactive();
	}
	
}
