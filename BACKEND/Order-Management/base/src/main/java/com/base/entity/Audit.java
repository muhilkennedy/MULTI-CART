package com.base.entity;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "AUDIT")
@ClassMetaProperty(code = "AUDIT")
public class Audit extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;
	private static final int ERROR_SIZE_LIMIT = 2048;

	@Column(name = "AUDITID")
	private String auditid;

	@Column(name = "MESSAGE", length = 2048)
	private String message;

	@Column(name = "OPERATION")
	private String operation;

	public String getAuditid() {
		return auditid;
	}

	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if (StringUtils.isNotBlank(message) && message.length() >= ERROR_SIZE_LIMIT) {
			this.message = message.substring(0, ERROR_SIZE_LIMIT);
		} else {
			this.message = message;
		}
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@PrePersist
	private void prePersistValidations() {
		if (StringUtils.isAllBlank(this.auditid)) {
			this.auditid = UUID.randomUUID().toString();
		}
	}

}
