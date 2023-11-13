package com.platform.entity;
/**
 * @author Muhil
 * Parent interface for all entities.
 */
public interface BaseObject {
	
	default Long getObjectId() {
		return null;
	};

	default String getUniqueId() {
		return null;
	};
	
}
