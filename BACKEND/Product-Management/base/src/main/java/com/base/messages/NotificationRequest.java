package com.base.messages;

import java.io.Serializable;

import com.platform.messages.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author muhil
 */
public class NotificationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long userId;
	@NotBlank
	private String content;
	@NotBlank
	private String title;
	private int type;
	private String recirectPath;
	private boolean broadcastNotification;
	
	public NotificationRequest() {
		
	}

	public NotificationRequest(@NotNull Long userId, @NotBlank String title, @NotBlank String content, NotificationType type,
			String recirectPath) {
		super();
		this.userId = userId;
		this.content = content;
		this.type = type.ordinal();
		this.recirectPath = recirectPath;
		this.title = title;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRecirectPath() {
		return recirectPath;
	}

	public void setRecirectPath(String recirectPath) {
		this.recirectPath = recirectPath;
	}

	public boolean isBroadcastNotification() {
		return broadcastNotification;
	}

	public void setBroadcastNotification(boolean broadcastNotification) {
		this.broadcastNotification = broadcastNotification;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
