package com.platform.cloud.messaging;

/**
 * @author Muhil 
 */
public class DirectNotification extends AppNotification {

	private String target;
	private Long userId;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
