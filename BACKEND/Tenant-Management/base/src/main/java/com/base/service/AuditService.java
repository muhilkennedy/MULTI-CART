package com.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.Audit;
import com.base.jpa.repository.AuditRepository;
import com.platform.messages.AuditOperation;

/**
 * @author Muhil
 *
 */
@Service
public class AuditService {

	@Autowired
	private AuditRepository auditRepository;

	public Audit logAuditInfo(AuditOperation operation, String message) {
		return logAuditInfo(operation, message, null);
	}

	public Audit logAuditInfo(AuditOperation operation, String message, String auditId) {
		Audit audit = new Audit();
		audit.setAuditid(auditId);
		audit.setMessage(message);
		audit.setOperation(operation.name());
		return auditRepository.saveAndFlush(audit);
	}

}
