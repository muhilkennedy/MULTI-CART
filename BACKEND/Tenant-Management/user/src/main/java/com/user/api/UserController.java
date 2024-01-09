package com.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.service.ReCaptchaService;
import com.platform.exception.ReCaptchaException;
import com.platform.exception.UserNotFoundException;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.exception.UserException;
import com.user.messages.PasswordResetRequest;
import com.user.messages.UserLoginRequest;
import com.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;
	
	@Autowired
	private ReCaptchaService captchaService;
	
	@PostMapping(value = "/employee/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> getUserDetails(@RequestBody UserLoginRequest requestbody,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value = "rememberMe", required = false) boolean rememberMe) throws UserException {
		GenericResponse<User> response = new GenericResponse<>();
		User employee = new User();
		employee.setEmailid(requestbody.getEmailId());
		employee.setPassword(requestbody.getPassword());
		employee.setMobile(requestbody.getMobile());
		employee.setUniquename(requestbody.getUniqueName());
		employee = (Employee) empService.login(employee);
		httpResponse.addHeader(PlatformUtil.TOKEN_HEADER,
				JWTUtil.generateToken(employee.getUniquename(), String.valueOf(employee.getObjectId()),
						JWTUtil.USER_TYPE_EMPLOYEE, httpRequest.getRemoteAddr(), rememberMe));
		return response.setStatus(Response.Status.OK).setData(employee).build();
	}

	@PostMapping(value = "/employee/password/reset/initiate", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> initiatePasswordReset(
			@RequestParam(value = "emailId", required = false) String emailId)
			throws UserException, UserNotFoundException {
		GenericResponse<User> response = new GenericResponse<>();
		User user = empService.findByEmailId(emailId);
		if (user == null) {
			throw new UserNotFoundException();
		}
		empService.initiatePasswordReset(user);
		return response.setStatus(Response.Status.OK).build();
	}

	@PostMapping(value = "/employee/password/reset", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> resetEmployeePassword(@RequestBody PasswordResetRequest request)
			throws UserException, UserNotFoundException, ReCaptchaException {
		GenericResponse<User> response = new GenericResponse<>();
		User user = empService.findByUniqueName(request.getUniqueName());
		if (user == null) {
			throw new UserNotFoundException();
		}
		if (captchaService.verify(request.getCaptchaResponse())) {
			if(request.isActivation()) {
				empService.activateAccount(user,request.getNewPassword(), request.getOtp());
			}
			else {
				empService.resetPassword(user, request.getNewPassword(), request.getOtp());
			}
			return response.setStatus(Response.Status.OK).build();
		}
		else {
			throw new ReCaptchaException("Captcha Validation Failed!");
		}
	}

}