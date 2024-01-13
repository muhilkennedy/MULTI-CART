package com.platform.service;

import java.util.List;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.platform.cloud.messaging.DirectNotification;
import com.platform.cloud.messaging.SubscriptionRequest;
import com.platform.cloud.messaging.TopicNotification;

/**
 * @author Muhil
 * We have only one type of cloud messaging service. FCM - firebase cloud  Messaging.
 */
public class PushMessageService {

	private static PushMessageService instance = new PushMessageService();
	
	public static PushMessageService getInstance() {
		return instance;
	}

	public void sendNotificationToTarget(DirectNotification notification) {
		GoogleMessagingService.getInstance().sendNotificationToTarget(notification);
	}

	public void sendNotificationToTarget(TopicNotification notification) {
		GoogleMessagingService.getInstance().sendNotificationToTarget(notification);
	}

	public void subscribeToTopic(SubscriptionRequest request) throws FirebaseMessagingException {
		GoogleMessagingService.getInstance().subscribeToTopic(request);
	}

	public void unSubscribeFromTopic(List<String> tokens, String topic) throws FirebaseMessagingException {
		GoogleMessagingService.getInstance().unSubscribeFromTopic(tokens, topic);
	}

}
