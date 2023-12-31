package com.user.serviceimpl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.server.BaseSession;
import com.base.service.EmailService;
import com.base.util.Log;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.user.entity.User;

import freemarker.template.TemplateException;

/**
 * @author Muhil
 */
@Service
public class EmployeeEmailService {

	private static final String EMP_REG = "EMPLOYEE_REGISTRATION";
	private static final String EMP_PWD_RST = "EMPLOYEE_PASSWORD_RESET";
	private static final String EMP_BIRTHDAY = "EMPLOYEE_BIRTHDAY_WISHES";
	private static final String PASSWORD_RESET_URL = "/#/password/reset?uniqueName=%s&otp=%s&activation=%s";

	@Autowired
	private EmailService emailService;

	public void sendWelcomeActivationEmail(User user, String generatedPassword, String otp) {
		Map<String, String> contentMap = new HashedMap<String, String>();
		contentMap.put("userName", String.format("%s %s", user.getFname(), user.getLname()));
		contentMap.put("password", String.valueOf(user.getUniqueId()));
		contentMap.put("mobile", user.getMobile());
		contentMap.put("email", user.getEmailid());
		contentMap.put("tenantLogo",
				((Tenant) BaseSession.getCurrentTenant()).getTenantDetail().getDetails().getLogoUrl());
		TenantDetails tenantDetails = ((Tenant) BaseSession.getCurrentTenant()).getTenantDetail();
		contentMap.put("forgotPasswordUrl", tenantDetails.getDetails().getAdminUrl()
				.concat(String.format(PASSWORD_RESET_URL, user.getUniquename(), otp, true)));
		try {
			emailService.sendMail(user.getEmailid(), String.format("Welcome %s", user.getFname()),
					emailService.constructEmailBody(EMP_REG, contentMap), null);
		} catch (IOException | TemplateException e) {
			Log.user.error("Exception sending mail to user {0} :: error :: {1}", user.getEmailid(), e);
		}
	}

	public void sendPasswordResetEmail(User user, String otp) {
		TenantDetails tenantDetails = ((Tenant) BaseSession.getCurrentTenant()).getTenantDetail();
		Map<String, String> contentMap = new HashedMap<String, String>();
		contentMap.put("userName", String.format("%s %s", user.getFname(), user.getLname()));
		contentMap.put("password", String.valueOf(user.getUniqueId()));
		contentMap.put("userId", user.getUniquename());
		contentMap.put("mobile", user.getMobile());
		contentMap.put("email", user.getEmailid());
		contentMap.put("tenantLogo", tenantDetails.getDetails().getLogoUrl());
		contentMap.put("forgotPasswordUrl", tenantDetails.getDetails().getAdminUrl()
				.concat(String.format(PASSWORD_RESET_URL, user.getUniquename(), otp)));
		try {
			emailService.sendMail(user.getEmailid(), String.format("Reset Password : %s", user.getUniquename()),
					emailService.constructEmailBody(EMP_PWD_RST, contentMap), null);
		} catch (IOException | TemplateException e) {
			Log.user.error("Exception sending mail to user {0} :: error :: {1}", user.getEmailid(), e);
		}
	}

	public void sendBirthdayWishesEmail(User user) {
		TenantDetails tenantDetails = ((Tenant) BaseSession.getTenant()).getTenantDetail();
		Map<String, String> contentMap = new HashedMap<String, String>();
		contentMap.put("userName", String.format("%s %s", user.getFname(), user.getLname()));
		contentMap.put("tenantName", ((Tenant) BaseSession.getTenant()).getName());
		contentMap.put("tenantLogo", tenantDetails.getDetails().getLogoUrl());
		contentMap.put("tenantMobile", tenantDetails.getContact());
		contentMap.put("tenantEmail", tenantDetails.getEmailid());
		try {
			emailService.sendMail(user.getEmailid(), String.format("Wishing you a Happy Birthday"),
					emailService.constructEmailBody(EMP_BIRTHDAY, contentMap), null);
		} catch (IOException | TemplateException e) {
			Log.user.error("Exception sending mail to user {0} :: error :: {1}", user.getEmailid(), e);
		}
	}

}
