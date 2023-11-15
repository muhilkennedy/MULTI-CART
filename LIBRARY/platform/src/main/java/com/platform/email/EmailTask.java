package com.platform.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.platform.cache.EmailCache;
import com.platform.configuration.PlatformConfiguration;
import com.platform.messages.EmailConfigurations;
import com.platform.util.FileUtil;
import com.platform.util.Log;
import com.platform.util.PlatformPropertiesUtil;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.InternetHeaders;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

/**
 * @author Muhil
 * send email based on config provided.
 */
public class EmailTask implements Runnable {

	private Long tenantId;
	private String businessEmail;
	private String businessPassword;
	private List<String> recipientEmail;
	private List<String> carbonCopy;
	private String subject;
	private String body;
	private Map<String, File> inlineImages;
	private List<File> attachments;

	public EmailTask(Long tenantId, List<String> recipientEmail, String subject, String body,
			Map<String, File> inlineImages) {
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.body = body;
		this.inlineImages = inlineImages;
		this.tenantId = tenantId;
	}

	public EmailTask(Long tenantId, List<String> recipientEmail, String subject, String body,
			Map<String, File> inlineImages, List<File> attachments) {
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.body = body;
		this.inlineImages = inlineImages;
		this.attachments = attachments;
		this.tenantId = tenantId;
	}

	public EmailTask(Long tenantId, List<String> recipientEmail, List<String> carbonCopy, String subject, String body,
			Map<String, File> inlineImages, List<File> attachments) {
		this.recipientEmail = recipientEmail;
		this.carbonCopy = carbonCopy;
		this.subject = subject;
		this.body = body;
		this.inlineImages = inlineImages;
		this.attachments = attachments;
		this.tenantId = tenantId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public void setBusinessPassword(String businessPassword) {
		this.businessPassword = businessPassword;
	}

	public List<String> getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(List<String> recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public List<String> getCarbonCopy() {
		return carbonCopy;
	}

	public void setCarbonCopy(List<String> carbonCopy) {
		this.carbonCopy = carbonCopy;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, File> getInlineImages() {
		return inlineImages;
	}

	public void setInlineImages(Map<String, File> inlineImages) {
		this.inlineImages = inlineImages;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	public void run() {
		try {
			// check for email status
			sendEmail();
		} catch (Exception e) {
			Log.platform.error("Error in Side Thread : " + e.getMessage());
		} finally {
			if (inlineImages != null) {
				for (Map.Entry<String, File> entry : inlineImages.entrySet()) {
					FileUtil.deleteDirectoryOrFile(entry.getValue());
				}
			}
			if (attachments != null) {
				attachments.stream().forEach(attachment -> {
					FileUtil.deleteDirectoryOrFile(attachment);
				});
			}
		}
	}

	public void sendEmail() throws Exception {
		Properties props = PlatformConfiguration.getEmailProperties();
		if (tenantId != null) {
			Log.platform.debug("Loaded tenant based email config : {}", tenantId);
			props = EmailCache.getInstance().get(tenantId);
			businessEmail = props.getProperty(EmailConfigurations.MAIL_USER_ID.getProperty());
			businessPassword = props.getProperty(EmailConfigurations.MAIL_USER_PASSWORD.getProperty());
		}
		if(StringUtils.isAllBlank(businessEmail)) {
			Log.platform.debug("Loaded default email config");
			businessEmail = PlatformPropertiesUtil.getDefaultEmail();
			businessPassword = PlatformPropertiesUtil.getDefaultEmailPassword();
		}
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(businessEmail, businessPassword);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(businessEmail));
			message.setSubject(subject);
			Multipart multipartObject = new MimeMultipart();
			// Creating first MimeBodyPart object which contains body text.
			InternetHeaders headers = new InternetHeaders();
			headers.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML);
			BodyPart bodyText = new MimeBodyPart(headers, body.getBytes(StandardCharsets.UTF_8.name()));
			multipartObject.addBodyPart(bodyText);
			// Creating second MimeBodyPart which contains inline body images.
			if (inlineImages != null) {
				inlineImages.entrySet().parallelStream().forEach(image -> {
					BodyPart imagePart = new MimeBodyPart();
					try {
						imagePart.setHeader("Content-ID", "<" + image.getKey() + ">");
						imagePart.setDisposition(MimeBodyPart.INLINE);
						imagePart.setFileName(image.getKey());
						InputStream stream = new FileInputStream(image.getValue());
						DataSource fds = new ByteArrayDataSource(IOUtils.toByteArray(stream),
								MediaType.APPLICATION_OCTET_STREAM);// check
						imagePart.setDataHandler(new DataHandler(fds));
						multipartObject.addBodyPart(imagePart);
					} catch (MessagingException | IOException e) {
						Log.platform.error("Exception while constructing Inline Images - " + e.getMessage());
					}

				});
			}
			// Creating third MimeBodyPart object which contains attachment.
			if (attachments != null && !attachments.isEmpty()) {
				attachments.parallelStream().filter(attachment -> attachment != null).forEach(attachment -> {
					try {
						BodyPart fileBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(attachment);
						fileBodyPart.setDataHandler(new DataHandler(source));
						fileBodyPart.setFileName(attachment.getName());
						multipartObject.addBodyPart(fileBodyPart);
					} catch (Exception ex) {
						Log.platform.error("Exception in adding Attachments - " + ex.getMessage());
					}

				});

			}
			// Attach body text and file attachment to the email.
			message.setContent(multipartObject, "multipart/mixed");
			recipientEmail.stream().forEach(recipient -> {
				try {
					message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
				} catch (MessagingException e) {
					Log.platform.error("Error Adding Recipients - {}", e);
				}
			});
			if (carbonCopy != null) {
				carbonCopy.stream().forEach(recipient -> {
					try {
						message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(recipient));
					} catch (MessagingException e) {
						Log.platform.error("Error Adding CC - {}", e);
					}
				});
			}
			Log.platform.info("sendEmail :: Sending email to Recipient - {}", StringUtils.join(recipientEmail, ","));
			Transport.send(message);
			Log.platform.info("sendEmail :: Email sent Successfully to Recipient - {}",
					StringUtils.join(recipientEmail, ","));

		} catch (Exception e) {
			Log.platform.error("sendEmail :: Error Sending email to Recipient - {} - {}",
					StringUtils.join(recipientEmail, ","), e);
			throw e;
		}
	}

}