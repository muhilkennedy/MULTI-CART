package com.platform.cloud.messaging;

import java.util.List;

/**
 * @author Muhil 
 */
public class DirectNotification extends AppNotification {

	private String target;
	private List<Long> userIds;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

}
