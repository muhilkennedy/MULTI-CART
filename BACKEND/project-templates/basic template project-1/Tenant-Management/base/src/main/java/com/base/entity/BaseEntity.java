package com.base.entity;

import java.io.Serializable;

import com.base.hibernate.configuration.BaseEntityListener;
import com.platform.entity.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Muhil
 * Custom rootId generated for BaseEnttites
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(BaseEntityListener.class)
public class BaseEntity implements Serializable, BaseObject {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROOTID", updatable = false, nullable = false)
	private long rootid;

	@Column(name = "TIMEUPDATED")
	private long timeupdated;

	@Column(name = "TIMECREATED")
	private long timecreated;

	@Column(name = "MODIFIEDBY")
	private long modifiedby;
	
	@Column(name = "CREATEDBY")
	private long createdby;
	
	@Column(name = "VERSION")
	private long version;

	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active;

	public long getRootId() {
		return rootid;
	}

	public void setRootId(long rootId) {
		this.rootid = rootId;
	}

	public long getTimeUpdated() {
		return timeupdated;
	}

	public void setTimeUpdated(long timeUpdated) {
		this.timeupdated = timeUpdated;
	}

	public long getTimeCreated() {
		return timecreated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timecreated = timeCreated;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getModifiedBy() {
		return modifiedby;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedby = modifiedBy;
	}

	public long getCreatedBy() {
		return createdby;
	}

	public void setCreatedBy(long createdBy) {
		this.createdby = createdBy;
	}
	
	@Override
	public Long getObjectRootId() {
		return rootid;
	}
	
}

