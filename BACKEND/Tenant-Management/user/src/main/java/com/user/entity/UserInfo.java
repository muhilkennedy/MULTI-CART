package com.user.entity;

import java.io.Serializable;

/**
 * @author muhil
 * can be used to store simple user preferences
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean skipTutorial;
	private String activationCode;

	public boolean isSkipTutorial() {
		return skipTutorial;
	}

	public void setSkipTutorial(boolean skipTutorial) {
		this.skipTutorial = skipTutorial;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

}
