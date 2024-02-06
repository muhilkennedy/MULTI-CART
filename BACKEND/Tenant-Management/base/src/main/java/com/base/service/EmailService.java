package com.base.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.base.bgwork.BGWorkUtil;
import com.base.entity.BaseEntity;
import com.base.entity.EmailTemplate;
import com.base.entity.FileStore;
import com.base.jpa.repository.EmailTemplateRepository;
import com.base.scheduledtask.EmailJob;
import com.base.server.BaseSession;
import com.base.util.EmailUtil;
import com.base.util.Log;
import com.platform.email.EmailTask;
import com.platform.messages.AuditOperation;
import com.platform.messages.ConfigurationType;
import com.platform.messages.EmailConfigurations;
import com.platform.messages.StoreType;
import com.platform.session.PlatformBaseSession;
import com.platform.util.FileUtil;
import com.platform.util.PlatformUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.jsonwebtoken.lang.Assert;

/**
 * @author Muhil
 * Execute email sending as fixed number of threads.
 */
@Component
public class EmailService {

	private final ExecutorService emailThreadPool = Executors.newFixedThreadPool(EmailUtil.MAX_EMAIL_THREADS);

	@Autowired
	private Configuration freeMarkerConfig;

	@Autowired
	private EmailTemplateRepository templateRepository;

	@Autowired
	private FileStoreService fileStore;
	
	@Autowired
	private ConfigurationService configService;
	
	@Autowired
	private AuditService audit;

	@Value("${app.email.enabled}")
	private boolean isEmailFeatureEnabled;

	public Template getTemplate(String name) throws IOException {
		return freeMarkerConfig.getTemplate(name);
	}
	
	public Template getTenantTemplate(String name) throws IOException {
		Template emailTemplate = null;
		try {
			emailTemplate = freeMarkerConfig.getTemplate(name + PlatformUtil.MINUS_SEPERATOR
					+ BaseSession.getTenantUniqueName() + PlatformUtil.TEMPLATE_EXTENTION);
		} catch (IOException e) {
			Log.base.error("Unable to find tenant email template : {}", e.getMessage());
		}
		return (emailTemplate != null) ? emailTemplate
				: freeMarkerConfig.getTemplate(name + PlatformUtil.TEMPLATE_EXTENTION);
	}

	public Template getTemplate(String name, Locale locale) throws IOException {
		return freeMarkerConfig.getTemplate(name + PlatformUtil.TEMPLATE_EXTENTION, locale);
	}

	public String constructEmailBody(Template template, Map<String, String> contentMap)
			throws IOException, TemplateException {
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, contentMap);
	}

	public String constructEmailBody(String templateName, Map<String, String> contentMap)
			throws IOException, TemplateException {
		return FreeMarkerTemplateUtils
				.processTemplateIntoString(getTenantTemplate(templateName), contentMap);
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

	public void sendMail(EmailTask task) {
		this.postEmailTask(task);
	}
	
	private JobDataMap createJobDataMap(List<String> recipientEmail, String subject, String body,
			List<File> attachmemnts) {
		return createJobDataMap(recipientEmail, null, subject, body, null, attachmemnts);
	}

	private JobDataMap createJobDataMap(List<String> recipientEmail, String subject, String body,
			Map<String, File> inlineImages) {
		return createJobDataMap(recipientEmail, null, subject, body, inlineImages, null);
	}

	private JobDataMap createJobDataMap(List<String> recipientEmail, List<String> cc, String subject, String body,
			Map<String, File> inlineImages, List<File> attachmemnts) {
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(EmailTask.KEY_TENANATID, BaseSession.getCurrentTenant().getRootid());
		dataMap.put(EmailTask.KEY_RECIPIENTS, recipientEmail);
		dataMap.put(EmailTask.KEY_CARBONCOPY, cc);
		dataMap.put(EmailTask.KEY_SUBJECT, subject);
		dataMap.put(EmailTask.KEY_BODY, body);
		dataMap.put(EmailTask.KEY_INLINEIMAGES, inlineImages);
		dataMap.put(EmailTask.KEY_ATTACHMENTS, attachmemnts);
		return dataMap;
	}

	public void sendMail(String recipientEmail, String subject, String body, Map<String, File> inlineImages) {
		/*
		 * EmailTask task = new EmailTask(BaseSession.getCurrentTenant().getRootId(),
		 * Arrays.asList(recipientEmail), subject, body, inlineImages);
		 * this.postEmailTask(task);
		 */
		try {
			BGWorkUtil.fireAndForget(EmailJob.class,
					createJobDataMap(Arrays.asList(recipientEmail), subject, body, inlineImages), EmailJob.JOB_GROUP);
		} catch (SchedulerException e) {
			Log.base.error("Exception sending email : {}", e);
			audit.logAuditInfo(AuditOperation.EMAILERROR, e.getMessage());
		}
	}

	public void sendMail(List<String> recipientEmail, String subject, String body, Map<String, File> inlineImages) {
		try {
			BGWorkUtil.fireAndForget(EmailJob.class, createJobDataMap(recipientEmail, subject, body, inlineImages),
					EmailJob.JOB_GROUP);
		} catch (SchedulerException e) {
			Log.base.error("Exception sending email : {}", e);
			audit.logAuditInfo(AuditOperation.EMAILERROR, e.getMessage());
		}
	}

	public void sendMail(String recipientEmail, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		try {
			BGWorkUtil.fireAndForget(EmailJob.class,
					createJobDataMap(Arrays.asList(recipientEmail), null, subject, body, inlineImages, attachments),
					EmailJob.JOB_GROUP);
		} catch (SchedulerException e) {
			Log.base.error("Exception sending email : {}", e);
			audit.logAuditInfo(AuditOperation.EMAILERROR, e.getMessage());
		}
	}

	public void sendMail(String recipientEmail, String cc, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		try {
			BGWorkUtil.fireAndForget(EmailJob.class, createJobDataMap(Arrays.asList(recipientEmail), Arrays.asList(cc),
					subject, body, inlineImages, attachments), EmailJob.JOB_GROUP);
		} catch (SchedulerException e) {
			Log.base.error("Exception sending email : {}", e);
			audit.logAuditInfo(AuditOperation.EMAILERROR, e.getMessage());
		}
	}

	public void sendMail(List<String> recipientEmail, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		try {
			BGWorkUtil.fireAndForget(EmailJob.class,
					createJobDataMap(recipientEmail, null, subject, body, inlineImages, attachments),
					EmailJob.JOB_GROUP);
		} catch (SchedulerException e) {
			Log.base.error("Exception sending email : {}", e);
			audit.logAuditInfo(AuditOperation.EMAILERROR, e.getMessage());
		}
	}

	public void sendMail(List<String> recipientEmail, List<String> cc, String subject, String body,
			Map<String, File> inlineImages, List<File> attachments) {
		try {
			BGWorkUtil.fireAndForget(EmailJob.class,
					createJobDataMap(recipientEmail, cc, subject, body, inlineImages, attachments), EmailJob.JOB_GROUP);
		} catch (SchedulerException e) {
			Log.base.error("Exception sending email : {}", e);
			audit.logAuditInfo(AuditOperation.EMAILERROR, e.getMessage());
		}
	}

	/**
	 * Load all templates to local storage for free marker to pickup.
	 */
	public void loadAllTemplatesToLocalStorage() {
		List<EmailTemplate> templates = templateRepository.findAll();
		templates.stream().forEach(template -> {
			try {
				BaseEntity dummyTenant = new BaseEntity();
				dummyTenant.setRootid(template.getRootid());
				PlatformBaseSession.setTenant(template);
				File file = fileStore.getFileById(template.getStoreid());
				File destFile = EmailUtil.getLocalEmailTemplatesDirectory();
				if (!destFile.exists()){
					destFile.mkdirs();
				}
				if(file != null) {
					FileUtil.moveFile(file.getPath(), destFile.getPath() + File.separator + file.getName());
					Log.base.debug("Email Template loaded : {}", file.getPath());
				}
			} catch (IOException e) {
				Log.base.error("Exception loading email template : {} : {}", template.getName(), e);
			}
			finally {
				PlatformBaseSession.clear();
			}
		});
	}

	public EmailTemplate createTemplate(String templateName, File file) throws IOException {
		EmailTemplate template = templateRepository.findEmailTemplate(templateName);
		if (template == null) {
			template = new EmailTemplate();
			template.setName(templateName);
			FileStore store = fileStore.uploadToFileStore(StoreType.GCP, file, true, PlatformUtil.TEMPLATES_FOLDER);
			template.setStoreid(store.getRootid());
		} else {
			Optional<FileStore> store = fileStore.getFileStoreById(template.getStoreid());
			if (store.isEmpty()) {
				throw new UnsupportedOperationException();
			}
			template.setName(templateName);
			fileStore.updateFileStore(store.get(), file, PlatformUtil.TEMPLATES_FOLDER);
		}
		templateRepository.saveAndFlush(template);
		FileUtil.moveFile(file.getPath(),
				EmailUtil.getLocalEmailTemplatesDirectory() + File.separator + file.getName());
		return template;
	}
	
	public List<EmailTemplate> getAllTemplates() {
		return templateRepository.findAll();
	}
	
	public List<String> getAllTemplateNamesForTenant(){
		return templateRepository.findEmailTemplateNames();
	}
	
	public File downloadTemplateFile(String templateName) throws IOException {
		EmailTemplate template = templateRepository.findEmailTemplate(templateName);
		Assert.notNull(template, "Email Template is not configured");
		return fileStore.getFileById(template.getStoreid());
	}
	
	public String getMailInboxUrl() {
		return configService.getConfigValueIfPresent(EmailConfigurations.MAIL_INBOX_URL.name(),
				ConfigurationType.EMAIL);
	}

}
