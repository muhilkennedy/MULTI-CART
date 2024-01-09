package com.platform.cloud.messaging;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.annotations.NotNull;

/**
 * @author Muhil
 */
public class SubscriptionRequest {

	@NotNull
	private String subscriber;
	private List<String> subscribers = new ArrayList<String>();
	private String topic;
	private String deviceInfo;

	public void addSusbscriber(String subscriber) {
		this.subscribers.add(subscriber);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
		this.addSusbscriber(subscriber);
	}

	public List<String> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<String> subscribers) {
		this.subscribers = subscribers;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
