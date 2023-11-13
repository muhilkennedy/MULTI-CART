package com.user.api;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.server.BaseSession;
import com.base.util.BaseUtil;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.util.DocxUtil;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.exception.UserException;
import com.user.messages.UserLoginRequest;
import com.user.service.EmployeeService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private EmployeeService empService;

	@PostMapping(value = "/employee/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> getUserDetails(@RequestBody UserLoginRequest requestbody,
			HttpServletResponse httpResponse) throws UserException {
		GenericResponse<User> response = new GenericResponse<>();
		User employee = new User();
		employee.setEmailid(requestbody.getEmailId());
		employee.setPassword(requestbody.getPassword());
		employee.setLocale(requestbody.getMobile());
		employee.setUniquename(requestbody.getUniqueName());
		employee = (Employee) empService.login(employee);
		httpResponse.addHeader(PlatformUtil.TOKEN_HEADER,
				JWTUtil.generateToken(String.valueOf(employee.getRootId()), JWTUtil.USER_TYPE_EMPLOYEE, false));
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}

}
