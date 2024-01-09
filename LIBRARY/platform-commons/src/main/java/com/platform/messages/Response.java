package com.platform.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author muhil
 */
public class Response {
	public static enum Status {
		OK, CREATED, NO_CONTENT, ERROR, BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, NOT_IMPLEMENTED, INTERNAL_SERVER_ERROR
	}

	private Status status;

	private int statusCode;

	private List<String> errorMessages = new ArrayList<>();

	public Status getStatus() {
		return status;
	}

	public void setResponseStatus(Status status) {
		if (status != null) {
			switch (status) {
			case OK:
				setStatusCode(200);
				break;
			case CREATED:
				setStatusCode(201);
				break;
			case NO_CONTENT:
				setStatusCode(204);
				break;
			case BAD_REQUEST:
				setStatusCode(400);
				break;
			case UNAUTHORIZED:
				setStatusCode(401);
				break;
			case FORBIDDEN:
				setStatusCode(403);
				break;
			case ERROR:
				// Service Unavailable
				setStatusCode(503);
				break;
			case INTERNAL_SERVER_ERROR:
				setStatusCode(500);
				break;
			case NOT_FOUND:
				setStatusCode(404);
				break;
			case NOT_IMPLEMENTED:
				setStatusCode(501);
				break;
			default:
				setStatusCode(500);
				break;
			}
		}
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
