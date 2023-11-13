package com.tenant.messages;

import java.text.ParseException;
import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

import com.platform.util.PlatformUtil;
import com.tenant.model.TenantInfo;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class TenantRequestBody {

	@NotBlank
	private String uniqueName;

	@NotBlank
	private String tenantName;

	private Long parentTenant;

	private String email;
	private String contact;
	private String street;
	private String city;
	private String pincode;
	private String tagLine;
	private String businessEmail;
	private String businessPassword;
	private TenantInfo details;

	private String startDate;
	private String endDate;
	
	public TenantRequestBody() {
		this.details = new TenantInfo();
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public Long getParentTenant() {
		return parentTenant;
	}

	public void setParentTenant(Long parentTenant) {
		this.parentTenant = parentTenant;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public String getBusinessPassword() {
		return businessPassword;
	}

	public void setBusinessPassword(String businessPassword) {
		this.businessPassword = businessPassword;
	}

	public TenantInfo getDetails() {
		return details;
	}

	public void setDetails(TenantInfo details) {
		this.details = details;
	}

	public Date getStartDate() {
		return parseDate(startDate);
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return parseDate(endDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isStartAndEndDatePresent() {
		return StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate);
	}

	private Date parseDate(String date) {
		try {
			return new Date(PlatformUtil.SIMPLE_DATE_ONLY_FORMAT.parse(date).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
