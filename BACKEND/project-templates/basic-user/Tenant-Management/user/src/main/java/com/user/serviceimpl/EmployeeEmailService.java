package com.user.serviceimpl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.EmailService;
import com.base.util.Log;
import com.user.entity.User;

import freemarker.template.TemplateException;

/**
 * @author Muhil
 *
 */
@Service
public class EmployeeEmailService {

	private static final String DEFAULT_REGISTRATION_FTL = "Registration-Default";

	@Autowired
	private EmailService emailService;

	//TODO: complete this
	public void sendWelcomeActivationEmail(User user, String generatedPassword) {
		Map<String, String> contentMap = new HashedMap<String, String>();
		contentMap.put("userName", String.format("%s %s", user.getFname(), user.getLname()));
		contentMap.put("password", String.valueOf(user.getRootId()));
		contentMap.put("mobile", user.getMobile());
		contentMap.put("email", user.getEmailid());
		try {
			emailService.sendMail(user.getEmailid(), String.format("Welcome %s", user.getFname()),
					emailService.constructEmailBody(DEFAULT_REGISTRATION_FTL, contentMap), null);
		} catch (IOException | TemplateException e) {
			Log.user.error("Exception sending mail to user {0} :: error :: {1}", user.getEmailid(), e);
		}
	}

}
