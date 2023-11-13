package com.base.service;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.base.util.EmailUtil;
import com.base.util.Log;
import com.platform.email.EmailTask;
import com.platform.session.PlatformBaseSession;
import com.platform.util.FileUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Muhil
 * Execute email sending as fixed number of threads.
 */
@Component
public class EmailService {

	private final ExecutorService emailThreadPool = Executors.newFixedThreadPool(EmailUtil.MAX_EMAIL_THREADS);

	@Autowired
	private Configuration freeMarkerConfig;
	
	@Value("${app.email.enabled}")
	private boolean isEmailFeatureEnabled;

	public Template getTemplate(String name) throws IOException {
		return freeMarkerConfig.getTemplate(name);
	}

	public Template getTemplate(String name, Locale locale) throws IOException {
		return freeMarkerConfig.getTemplate(name + EmailUtil.TEMPLATE_EXTENTION, locale);
	}

	public String constructEmailBody(Template template, Map<String, String> contentMap)
			throws IOException, TemplateException {
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, contentMap);
	}

	public String constructEmailBody(String templateName, Map<String, String> contentMap)
			throws IOException, TemplateException {
		return FreeMarkerTemplateUtils
				.processTemplateIntoString(getTemplate(templateName + EmailUtil.TEMPLATE_EXTENTION), contentMap);
	}
	
	private void postEmailTask(EmailTask task) {
		if (isEmailFeatureEnabled) {
			emailThreadPool.execute(task);
		} else {
			Log.base.warn("Email Feature is disabled! communication mails are not pooled!");
			if (task.getInlineImages() != null) {
				for (Map.Entry<String, File> entry : task.getInlineImages().entrySet()) {
					FileUtil.deleteDirectoryOrFile(entry.getValue());
				}
			}
			if (task.getAttachments() != null) {
				task.getAttachments().parallelStream().forEach(attachment -> {
					FileUtil.deleteDirectoryOrFile(attachment);
				});
			}
		}
	}

	public void sendMail(String recipientEmail, String subject, String body, Map<String, File> inlineImages) {
		EmailTask task = new EmailTask(PlatformBaseSession.getTenantUniqueName(), Arrays.asList(recipientEmail), subject, body,
				inlineImages);
		this.postEmailTask(task);
	}

	public void sendMail(List<String> recipientEmail, String subject, String body, Map<String, File> inlineImages) {
		EmailTask task = new EmailTask(PlatformBaseSession.getTenantUniqueName(), recipientEmail, subject, body, inlineImages);
		this.postEmailTask(task);
	}

	public void sendMail(String recipientEmail, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		EmailTask task = new EmailTask(PlatformBaseSession.getTenantUniqueName(), Arrays.asList(recipientEmail), subject, body,
				inlineImages, attachments);
		this.postEmailTask(task);
	}
	
	public void sendMail(String recipientEmail, String cc, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		EmailTask task = new EmailTask(PlatformBaseSession.getTenantUniqueName(), Arrays.asList(recipientEmail), Arrays.asList(cc), subject, body,
				inlineImages, attachments);
		this.postEmailTask(task);
	}

	public void sendMail(List<String> recipientEmail, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		EmailTask task = new EmailTask(PlatformBaseSession.getTenantUniqueName(), recipientEmail, subject, body, inlineImages,
				attachments);
		this.postEmailTask(task);
	}

	public void sendMail(List<String> recipientEmail, List<String> cc, String subject, String body,
			Map<String, File> inlineImages, List<File> attachments) {
		EmailTask task = new EmailTask(PlatformBaseSession.getTenantUniqueName(), recipientEmail, cc, subject, body, inlineImages,
				attachments);
		this.postEmailTask(task);
	}

	// setup task with tenant business auth
	public void sendMail(EmailTask task) {
		this.postEmailTask(task);
	}

}

