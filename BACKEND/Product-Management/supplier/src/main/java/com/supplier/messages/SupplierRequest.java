package com.supplier.messages;

/**
 * @author Muhil
 */
public class SupplierRequest {

	private String name;
	private String description;
	private String address;
	private String emailId;
	private String contact;
	private String secondarycontact;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSecondarycontact() {
		return secondarycontact;
	}

	public void setSecondarycontact(String secondarycontact) {
		this.secondarycontact = secondarycontact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
