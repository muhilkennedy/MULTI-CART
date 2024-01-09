package com.platform.exception;

/**
 * @author Muhil
 *
 */
public class BGWorkException extends CustomWrapperException {

	private static final long serialVersionUID = 1L;

	public BGWorkException() {
		super();
	}
	
	public BGWorkException(Exception e) {
		super(e);
	}

	public BGWorkException(String message) {
		super(message);
	}

}
