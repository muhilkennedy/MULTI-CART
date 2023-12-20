package com.tenant.api;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.bgwork.TempFileCleanUpScheduledTask;
import com.base.server.BaseSession;
import com.platform.exception.EncryptionException;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.tenant.entity.Tenant;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("tenant")
public class TenantController {
	
	@Autowired
	TempFileCleanUpScheduledTask task;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> pingTenant(HttpServletRequest request) throws EncryptionException, NoSuchAlgorithmException, IOException, ParseException, SchedulerException {
		GenericResponse<Tenant> response = new GenericResponse<>();
		response.setStatus(Response.Status.OK).setData((Tenant)BaseSession.getTenant());
		return response;
	}
	
	
}
