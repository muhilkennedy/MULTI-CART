package com.base.api;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.entity.Notification;
import com.base.messages.NotificationRequest;
import com.base.service.NotificationService;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("notification")
@ValidateUserToken
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Notification> getAllUnreadNotifications() {
		return notificationService.findAllUnReadForUser();
	}

	@GetMapping(value = "/unread/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Integer> getAllUnreadNotificationsCount() {
		GenericResponse<Integer> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(notificationService.getUnreadNotificationCount()).build();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Notification> createNotification(@RequestBody NotificationRequest request)
			throws SchedulerException {
		GenericResponse<Notification> response = new GenericResponse<>();
		notificationService.createNotification(request);
		return response.setStatus(Response.Status.OK).build();
	}

	@PutMapping(value = "/{id}/read", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Notification> markNotificationAsRead(@PathVariable("id") Long id) throws SchedulerException {
		GenericResponse<Notification> response = new GenericResponse<>();
		notificationService.markAsRead(id);
		return response.setStatus(Response.Status.OK).build();
	}

}
