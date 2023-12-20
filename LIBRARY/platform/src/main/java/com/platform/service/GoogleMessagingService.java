package com.platform.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.util.Asserts;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.platform.cloud.messaging.DirectNotification;
import com.platform.cloud.messaging.SubscriptionRequest;
import com.platform.cloud.messaging.TopicNotification;
import com.platform.util.Log;

/**
 * @author muhil cloud push notification service - firebase
 */
public class GoogleMessagingService {

	private static GoogleMessagingService instance;

	public static GoogleMessagingService getInstance() {
		Asserts.notNull(instance, "Google Instance is not initialized");
		return instance;
	}

	private GoogleMessagingService(File gcpConfigFile) throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new FileInputStream(gcpConfigFile))).build();
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
			Log.platform.info("Firebase application has been initialized");
		}
	}

	public static void init(File gcpConfigFile) throws IOException {
		synchronized (GoogleMessagingService.class) {
			if (instance == null) {
				instance = new GoogleMessagingService(gcpConfigFile);
			}
		}
	}

	public void sendNotificationToTarget(DirectNotification notification) {
		Message message = Message.builder()
				// Set the configuration for our web notification
				.setWebpushConfig(
						// Create and pass a WebpushConfig object setting the notification
						WebpushConfig.builder().setNotification(
								// Create and pass a web notification object with the specified title, body, and
								// icon URL
								WebpushNotification.builder().setTitle(notification.getTitle())
										.setBody(notification.getMessage())
										// .setIcon("https://assets.mapquestapi.com/icon/v2/circle@2x.png")
										.build())
								.build())
				// Specify the user to send it to in the form of their token
				.setToken(notification.getTarget()).build();
		FirebaseMessaging.getInstance().sendAsync(message);
	}
	
	public void sendNotificationToTarget(TopicNotification notification) {
		Message message = Message.builder()
				// Set the configuration for our web notification
				.setWebpushConfig(
						// Create and pass a WebpushConfig object setting the notification
						WebpushConfig.builder().setNotification(
								// Create and pass a web notification object with the specified title, body, and
								// icon URL
								WebpushNotification.builder().setTitle(notification.getTitle())
										.setBody(notification.getMessage())
										// .setIcon("https://assets.mapquestapi.com/icon/v2/circle@2x.png")
										.build())
								.build())
				// Specify the user to send it to in the form of their token
				.setTopic(notification.getTopic()).build();
		FirebaseMessaging.getInstance().sendAsync(message);
	}
	
	public void subscribeToTopic(SubscriptionRequest request) throws FirebaseMessagingException {
		FirebaseMessaging.getInstance().subscribeToTopic(request.getSubscribers(), request.getTopic());
	}

}
