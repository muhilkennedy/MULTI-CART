package com.base.hibernate.configuration;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * @author Muhil
 * manipulate basic required fields before persist. 
 *
 */
public class BaseEntityListener {

	@PrePersist
    private void prePersist(Object object) {
		if (object instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) object;
			if (entity.getTimeCreated() == 0L) {
				entity.setTimeCreated(System.currentTimeMillis());
			}
			entity.setActive(true);
			entity.setTimeUpdated(System.currentTimeMillis());
			entity.setModifiedBy(getCurrentUser());
			entity.setCreatedBy(getCurrentUser());
		}
    }
	
	@PreUpdate
	private void preUpdate(Object object) {
		if (object instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) object;
			entity.setTimeUpdated(System.currentTimeMillis());
			entity.setModifiedBy(getCurrentUser());
			entity.setVersion(entity.getVersion() + 1);
		}
    }
	
	private long getCurrentUser() {
		if (BaseSession.getUser() == null) {
			return 0L;
		}
		return BaseSession.getUser().getRootId();
	}
	
}

