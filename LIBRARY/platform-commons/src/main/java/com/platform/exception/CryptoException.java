package com.platform.exception;

public class CryptoException extends Exception {

	public CryptoException(String msg) {
		super(msg);
	}
	
	public CryptoException(Exception ex) {
		super(ex);
	}
	
}
