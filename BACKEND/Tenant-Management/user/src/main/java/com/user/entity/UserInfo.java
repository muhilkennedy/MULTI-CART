package com.user.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author muhil
 * can be used to store simple user preferences
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean skipTutorial;
	@JsonIgnore
	private String activationCode;
	private boolean showCalendar;
	private boolean showNotifications;
	private boolean showTasks;

	public boolean isSkipTutorial() {
		return skipTutorial;
	}

	public void setSkipTutorial(boolean skipTutorial) {
		this.skipTutorial = skipTutorial;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public boolean isShowCalendar() {
		return showCalendar;
	}

	public void setShowCalendar(boolean showCalendar) {
		this.showCalendar = showCalendar;
	}

	public boolean isShowNotifications() {
		return showNotifications;
	}

	public void setShowNotifications(boolean showNotifications) {
		this.showNotifications = showNotifications;
	}

	public boolean isShowTasks() {
		return showTasks;
	}

	public void setShowTasks(boolean showTasks) {
		this.showTasks = showTasks;
	}

}
