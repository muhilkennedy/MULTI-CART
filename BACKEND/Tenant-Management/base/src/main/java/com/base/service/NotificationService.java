package com.base.service;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.base.bgwork.BGWorkUtil;
import com.base.bgwork.BroadCastNotificationJob;
import com.base.entity.BaseEntity;
import com.base.entity.Notification;
import com.base.entity.Notificationtoken;
import com.base.messages.NotificationRequest;
import com.base.reactive.repository.NotificationRepository;
import com.base.server.BaseSession;
import com.base.util.BaseUtil;
import com.platform.cloud.messaging.DirectNotification;
import com.platform.service.PushMessageService;

import reactor.core.publisher.Flux;

/**
 * @author muhil
 */
@Service
public class NotificationService implements BaseService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private NotificationTokenService tokenService;

	@Autowired
	@Qualifier("EmployeeService")
	private BaseService employeeService;

	@Override
	public BaseEntity findById(Long rootId) {
		return notificationRepository.findById(BaseSession.getTenantId(), rootId).block();
	}

	public Flux<Notification> findAllUnReadForUser() {
		return notificationRepository.getAllUserUnreadNotification(BaseSession.getTenantId(),
				BaseSession.getUser().getRootid());
	}

	public void createNotification(NotificationRequest notificationRequest) throws SchedulerException {
		if (notificationRequest.isBroadcastNotification()) {
			// TODO: check permissions, only superuser , admin, manage users can broadcast
			JobDataMap map = new JobDataMap();
			map.put("request", notificationRequest);
			map.put("tenant", BaseSession.getTenant().getRootid());
			map.put("user", BaseSession.getUser().getRootid());
			BGWorkUtil.fireAndForget(BroadCastNotificationJob.class, map, "INTERNAL-NOTIFICATION");
			return;
		}
		BaseEntity emp = employeeService.findById(notificationRequest.getUserId());
		Assert.notNull(emp, "Invalid UserId");
		Notification notification = new Notification(BaseSession.getTenantId(), emp.getRootid(),
				notificationRequest.getTitle(), notificationRequest.getContent(), notificationRequest.getRecirectPath(),
				notificationRequest.getType());
		notificationRepository.save(notification).block();
		sendOutPushNotification(notificationRequest);
	}
	
	private void sendOutPushNotification(NotificationRequest notificationRequest) {
		if (notificationRequest.isPushNotification()) {
			Flux<Notificationtoken> tokens = tokenService.findAllUserTokens(notificationRequest.getUserId());
			tokens.toStream().forEach(token -> {
				DirectNotification directNotification = new DirectNotification();
				directNotification.setMessage(notificationRequest.getContent());
				directNotification.setTitle(notificationRequest.getTitle());
				directNotification.setTarget(token.getToken());
				PushMessageService.getInstance().sendNotificationToTarget(directNotification);
			});
		}
	}

	public int getUnreadNotificationCount() {
		return notificationRepository
				.getAllUserUnreadNotificationCount(BaseSession.getTenantId(), BaseSession.getUser().getRootid())
				.block();
	}

	/**
	 * @param notificationRequest
	 * recursive method to create notification for all users in batch
	 */
	public void broadCastNotificationJob(NotificationRequest notificationRequest) {
		List<BaseEntity> employees = (List<BaseEntity>) employeeService
				.findAll(BaseUtil.getPageable("timecreated", "desc", 0, 100));
		if (employees == null || employees.isEmpty()) {
			return;
		}
		List<Notification> notifications = employees.stream()
				.map(emp -> new Notification(BaseSession.getTenantId(), emp.getRootid(), notificationRequest.getTitle(),
						notificationRequest.getContent(), notificationRequest.getRecirectPath(),
						notificationRequest.getType()))
				.toList();
		notificationRepository.saveAll(notifications).buffer();
		employees.stream().forEach(employee -> {
			notificationRequest.setUserId(employee.getRootid());
			sendOutPushNotification(notificationRequest);
		});
		broadCastNotificationJob(notificationRequest);
	}

	public void markAsRead(Long rootId) {
		Notification notification = (Notification) findById(rootId);
		if (notification != null) {
			notificationRepository.markAsRead(BaseSession.getTenantId(), rootId).block();
		}
	}

}
