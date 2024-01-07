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
			if (entity.getTimecreated() == 0L) {
				entity.setTimecreated(System.currentTimeMillis());
			}
			entity.setActive(true);
			entity.setTimeupdated(System.currentTimeMillis());
			entity.setModifiedby(getCurrentUser());
			entity.setCreatedby(getCurrentUser());
		}
    }
	
	@PreUpdate
	private void preUpdate(Object object) {
		if (object instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) object;
			entity.setTimeupdated(System.currentTimeMillis());
			entity.setModifiedby(getCurrentUser());
			entity.setVersion(entity.getVersion() + 1);
		}
    }
	
	private long getCurrentUser() {
		if (BaseSession.getUser() == null) {
			return 0L;
		}
		return BaseSession.getUser().getObjectId();
	}

}

