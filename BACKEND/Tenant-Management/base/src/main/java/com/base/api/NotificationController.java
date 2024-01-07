package com.base.api;

import org.apache.http.HttpHeaders;
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
import com.base.entity.Notificationtoken;
import com.base.messages.NotificationRequest;
import com.base.service.NotificationService;
import com.base.service.NotificationTokenService;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.platform.annotations.ValidateUserToken;
import com.platform.cloud.messaging.DirectNotification;
import com.platform.cloud.messaging.SubscriptionRequest;
import com.platform.cloud.messaging.TopicNotification;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.service.PushMessageService;

import jakarta.servlet.http.HttpServletRequest;
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
	
	@Autowired
	private NotificationTokenService tokenService;

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
	
	@PostMapping("/push/topic/subscription")
	public void subscribeToTopic(HttpServletRequest httpRequest, @RequestBody SubscriptionRequest request) throws FirebaseMessagingException {
		PushMessageService.getInstance().subscribeToTopic(request);
		request.setDeviceInfo(httpRequest.getHeader(HttpHeaders.USER_AGENT));
		tokenService.registerIfNotExists(request);
	}
	
	@PostMapping("/push")
	public void direct(@RequestBody DirectNotification request) {
		if (request.getUserIds() == null) {
			PushMessageService.getInstance().sendNotificationToTarget(request);
		}
		request.getUserIds().stream().forEach(userId -> {
			Flux<Notificationtoken> userTokens = tokenService.findAllUserTokens(userId);
			userTokens.toStream().forEach(token -> {
				request.setTarget(token.getToken());
				PushMessageService.getInstance().sendNotificationToTarget(request);
			});
		});
	}

	@PostMapping("/push/topic")
	public void topic(@RequestBody TopicNotification request) {
		PushMessageService.getInstance().sendNotificationToTarget(request);
	}

}
