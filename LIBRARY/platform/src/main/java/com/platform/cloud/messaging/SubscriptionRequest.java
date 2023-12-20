package com.platform.cloud.messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Muhil
 */
public class SubscriptionRequest {

	private String subscriber;
	private List<String> subscribers = new ArrayList<String>();
	private String topic;

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

}
