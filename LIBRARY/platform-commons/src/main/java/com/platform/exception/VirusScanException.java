package com.platform.exception;

/**
 * @author Muhil
 */
public class VirusScanException extends CustomWrapperException {

	private static final long serialVersionUID = 1L;
	
	public VirusScanException(){
		super();
	}
	
	public VirusScanException(String message) {
		super(message);
	}

}
