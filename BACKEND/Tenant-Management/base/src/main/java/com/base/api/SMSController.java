package com.base.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.ConfigurationType;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.messages.SMSConfigurations;
import com.platform.user.Permissions;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/sms")
@ValidateUserToken
public class SMSController {

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/configurationkeys", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ConfigurationType> getEmailConfigurationProperties() {
		GenericResponse<ConfigurationType> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(ConfigurationType.SMS)
				.setDataList(SMSConfigurations.stream().toList()).build();
	}

}
