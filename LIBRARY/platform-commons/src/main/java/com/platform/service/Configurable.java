package com.platform.service;

/**
 * @author Muhil
 */
public interface Configurable {
	
	/**
	 * adds the bean to factory map.
	 */
	void register();
	
	/**
	 * perform configuration apply for current tenant session.
	 */
	void applyConfiguration();

}
