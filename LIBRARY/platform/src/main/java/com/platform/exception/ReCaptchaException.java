package com.platform.exception;

public class ReCaptchaException extends CustomWrapperException{
	
	private static final long serialVersionUID = 1L;
	
	public ReCaptchaException(String msg) {
		super(msg);
	}

}
