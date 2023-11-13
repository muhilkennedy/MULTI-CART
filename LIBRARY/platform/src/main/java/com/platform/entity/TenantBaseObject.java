package com.platform.entity;

/**
 * @author Muhil
 *
 */
public interface TenantBaseObject extends BaseObject {

	String getTenantName();
	
	String getTenantUniqueName();
	
	Long getTenantRootId();

}
