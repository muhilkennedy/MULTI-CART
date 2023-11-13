package com.user.exception;

import com.platform.exception.CustomWrapperException;

/**
 * @author muhil
 *
 */
public class InvalidUserPermission extends CustomWrapperException {

	private static final long serialVersionUID = 1L;

	public InvalidUserPermission() {
		super();
	}
	
	public InvalidUserPermission(String message) {
		super(message);
	}
}
