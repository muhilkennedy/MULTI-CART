package com.tenant.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.server.BaseSession;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.tenant.entity.Tenant;
import com.tenant.reactive.repository.TenantR2Repository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("tenant")
public class TenantController {
	
	@Autowired
	TenantR2Repository repo;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Tenant> pingTenant(HttpServletRequest request) {
		GenericResponse<Tenant> response = new GenericResponse<>();
		response.setStatus(Response.Status.OK).setData((Tenant)BaseSession.getTenant());
		return response;
	}
	
}
