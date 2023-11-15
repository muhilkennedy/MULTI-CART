package com.base.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.entity.ConfigType;
import com.base.messages.ConfigRequest;
import com.base.service.ConfigurationService;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;

import jakarta.validation.Valid;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/base")
@ValidateUserToken
public class BaseController {

	@Autowired
	private ConfigurationService configService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PostMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ConfigType> addConfig(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestBody @Valid ConfigRequest config) {
		GenericResponse<ConfigType> response = new GenericResponse<>();
		configService.createConfig(config.getKey(), config.getValue(), config.getType());
		return response.setStatus(Response.Status.OK).build();
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PostMapping(value = "/configs", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ConfigType> addConfigs(@RequestParam(value = "tenantId", required = false) Long tenantId,
			@RequestBody @Valid List<ConfigRequest> configList) {
		GenericResponse<ConfigType> response = new GenericResponse<>();
		configList.stream().forEach(config -> {
			configService.createConfig(config.getKey(), config.getValue(), config.getType());
		});
		return response.setStatus(Response.Status.OK).build();
	}

}
