package com.platform.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.platform.session.PlatformBaseSession;
import com.platform.util.Log;

/**
 * @author muhil
 * cloud push notification service - firebase
 */
public class GoogleMessagingService {

	private static GoogleMessagingService instance;
	
	private Map<Long, FirebaseApp> tenantApps = new HashMap<Long, FirebaseApp>();

	public static GoogleMessagingService getInstance() {
		Asserts.notNull(instance, "Google Instance is not initialized");
		return instance;
	}
	
	private GoogleMessagingService(Map<Long, FirebaseApp> configs) {
		this.tenantApps = configs;
	}

	private FirebaseMessaging messageInstance() {
		if (this.tenantApps != null && !this.tenantApps.isEmpty()) {
			return FirebaseMessaging.getInstance(this.tenantApps.get(PlatformBaseSession.getTenantId()));
		}
		return FirebaseMessaging.getInstance();
	}
	
	private GoogleMessagingService(File gcpConfigFile) throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new FileInputStream(gcpConfigFile))).build();
				//.setDatabaseUrl("https://mken-test-webapp-default-rtdb.firebaseio.com").build();
		if(FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options); 
		}
		Log.platform.info("Firebase application has been initialized");
	}

	public static void init(File gcpConfigFile) throws IOException {
		synchronized (GoogleMessagingService.class) {
			if (instance == null) {
				instance = new GoogleMessagingService(gcpConfigFile);
			}
		}
	}

	public static void initForTenants(Map<Long, String> gcpConfig) throws IOException {
		synchronized (GoogleMessagingService.class) {
			if (instance == null) {
				Map<Long, FirebaseApp> firebaseConfigs = new HashMap<Long, FirebaseApp>();
				for (Map.Entry<Long, String> entry : gcpConfig.entrySet()) {
					firebaseConfigs.put(entry.getKey(), initFireBaseApp(entry.getKey(), entry.getValue()));
				}
				instance = new GoogleMessagingService(firebaseConfigs);
			}
		}
	}

	private static FirebaseApp initFireBaseApp(Long tenantId, String gcpConfig) throws IOException {
		String id = String.valueOf(tenantId);
		byte[] bytes = Base64.getDecoder().decode(gcpConfig);
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(bytes))).build();
		return FirebaseApp.initializeApp(options, id);
	}

	public void updateTenantConfig(Long tenantId, String gcpConfig) throws IOException {
		synchronized (GoogleMessagingService.class) {
			this.tenantApps.put(tenantId, initFireBaseApp(tenantId, gcpConfig));
		}
	}

	public void sendNotificationToTarget(DirectNotification notification) {
		Message message = Message.builder()
				// Set the configuration for our web notification
				.setWebpushConfig(
						// Create and pass a WebpushConfig object setting the notification
						WebpushConfig.builder().setNotification(
								// Create and pass a web notification object with the specified title, body, and icon URL
								WebpushNotification.builder().setTitle(notification.getTitle())
										.setBody(notification.getMessage())
										// .setIcon(tenantIconUrl)
										.build())
								.build())
				// Specify the user to send it to in the form of their token
				.setToken(notification.getTarget()).build();
		messageInstance().sendAsync(message);
	}
	
	public void sendNotificationToTarget(TopicNotification notification) {
		Message message = Message.builder()
				.setWebpushConfig(WebpushConfig.builder()
						.setNotification(WebpushNotification.builder().setTitle(notification.getTitle())
								.setBody(notification.getMessage()).build())
						.build())
				// Specify the user to send it to in the form of their token
				.setTopic(notification.getTopic()).build();
		messageInstance().sendAsync(message);
	}
	
	public void subscribeToTopic(SubscriptionRequest request) throws FirebaseMessagingException {
		messageInstance().subscribeToTopic(request.getSubscribers(), request.getTopic());
	}
	
	public void unSubscribeFromTopic(List<String> tokens, String topic) throws FirebaseMessagingException {
		messageInstance().unsubscribeFromTopic(tokens, topic);
	}

}
