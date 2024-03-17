package com.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.user.entity.User;
import com.user.exception.EmployeeWidgetPreferences;
import com.user.service.EmployeeService;

import jakarta.ws.rs.QueryParam;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("employee/preference")
@ValidateUserToken
public class EmployeePreferenceController {

	@Autowired
	private EmployeeService empService;

	@PutMapping(value = "/widget/notification", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> updateNotificationWidgetPreference(@QueryParam("status") boolean status) {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(empService.updateWidgetVisiblityPreference(EmployeeWidgetPreferences.NOTIFICATIONS, status))
				.build();
	}

	@PutMapping(value = "/widget/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> updateTasksWidgetPreference(@QueryParam("status") boolean status) {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(empService.updateWidgetVisiblityPreference(EmployeeWidgetPreferences.TASKS, status))
				.build();
	}

	@PutMapping(value = "/widget/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<User> updateCalendarWidgetPreference(@QueryParam("status") boolean status) {
		GenericResponse<User> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(empService.updateWidgetVisiblityPreference(EmployeeWidgetPreferences.CALENDAR, status))
				.build();
	}

}
