package com.base.entity;

import com.platform.annotations.ClassMetaProperty;
import com.platform.messages.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "NOTIFICATION")
@ClassMetaProperty(code = "NOTI")
public class Notification extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "USERID")
	private Long userid;
	
	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "REDIRECTPATH")
	private String redirectpath;
	
	@Column(name = "NOTIFICATIONREAD")
	private boolean notificationread;
	
	@Column(name = "NOTIFICATIONTYPE")
	private int notificationtype;

	public Notification() {
		super();
	}

	public Notification(Long userid, String title, String content, NotificationType type) {
		super();
		this.userid = userid;
		this.title = title;
		this.content = content;
		this.title = title;
		this.notificationtype = type.ordinal();
	}

	public Notification(Long tenantId, Long userid, String title, String content, String redirectpath,
			NotificationType type) {
		super();
		this.title = title;
		this.userid = userid;
		this.content = content;
		this.redirectpath = redirectpath;
		this.notificationtype = type.ordinal();
		this.title = title;
		this.setTenantid(tenantId);
		this.setActive(true);
	}

	public Notification(Long tenantId, Long userid, String title, String content, String redirectpath, int type) {
		super();
		this.title = title;
		this.userid = userid;
		this.content = content;
		this.redirectpath = redirectpath;
		this.notificationtype = type;
		this.title = title;
		this.setTenantid(tenantId);
		this.setActive(true);
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRedirectpath() {
		return redirectpath;
	}

	public void setRedirectpath(String redirectpath) {
		this.redirectpath = redirectpath;
	}

	public int getNotificationtype() {
		return notificationtype;
	}

	public void setNotificationtype(int notificationtype) {
		this.notificationtype = notificationtype;
	}

	public boolean isRead() {
		return notificationread;
	}

	public void setRead(boolean read) {
		this.notificationread = read;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
