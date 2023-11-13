package com.tenant.TenantManagement;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("employee")
@ValidateUserToken
public class Controller {
	
	@GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse ping()
	{
		GenericResponse response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData("pinged").build();
	}
	
	@GetMapping(value = "/ping1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux fetchUsersReactive(){
		
		return Flux.range(1, 100);
	}

}
