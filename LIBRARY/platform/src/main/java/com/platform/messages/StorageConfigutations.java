package com.platform.messages;

/**
 * @author muhil
 * Custom Storage configuration
 */
public enum StorageConfigutations {
	
	STORAGE_TYPE("app.storage.type"),
	STORAGE_BUCKET("app.storage.bucket"),
	STORAGE_CONFIG("app.storage.config");
	
	private String property;
	
	StorageConfigutations(String property){
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

}
