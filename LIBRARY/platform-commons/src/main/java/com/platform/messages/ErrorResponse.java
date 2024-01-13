package com.platform.messages;

import java.time.Instant;
import java.util.UUID;

public class ErrorResponse {

	private int status;
	private String message;
	private String timeStamp;
	private String errorCode;

	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = Instant.now().toString();
		this.errorCode = UUID.randomUUID().toString();
	}
	
	public ErrorResponse(int status, String message, String errorCode) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = Instant.now().toString();
		this.errorCode = errorCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status=" + status + ", message=" + message + ", timeStamp=" + timeStamp + ", errorCode="
				+ errorCode + "]";
	}

}
