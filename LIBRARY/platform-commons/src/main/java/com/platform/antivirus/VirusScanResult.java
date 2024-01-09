package com.platform.antivirus;

/**
 * @author muhil
 */
public class VirusScanResult {

	private VirusScanStatus status;
	private String result;
	private String signature;

	public VirusScanResult() {
		super();
	}

	public VirusScanResult(VirusScanStatus status, String result) {
		super();
		this.status = status;
		this.result = result;
	}

	public VirusScanResult(VirusScanStatus status, String result, String signature) {
		super();
		this.status = status;
		this.result = result;
		this.signature = signature;
	}

	public VirusScanStatus getStatus() {
		return status;
	}

	public void setStatus(VirusScanStatus status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "VirusScanResult [status=" + status + ", result=" + result + ", signature=" + signature + "]";
	}

}