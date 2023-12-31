package com.user.messages;

import com.platform.messages.ReCaptchaRequest;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class PasswordResetRequest extends ReCaptchaRequest {

	@NotBlank
	private String uniqueName;
	@NotBlank
	private String otp;
	@NotBlank
	private String newPassword;
	@NotBlank
	private boolean activation;

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isActivation() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

}
