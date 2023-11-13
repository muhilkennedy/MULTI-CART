package com.platform.entity;

/**
 * @author Muhil
 * generic user object
 */
public class PlatformUser implements BaseObject {
	
	private Long rootId;
	
	@Override
	public Long getObjectId() {
		return this.rootId;
	}
	
}
