package com.base.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.entity.EmailTemplate;
import com.base.server.BaseSession;
import com.base.service.EmailService;
import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.email.EmailTemplatePlaceholderConfiguration;
import com.platform.messages.ConfigurationType;
import com.platform.messages.EmailConfigurations;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.platform.util.FileUtil;
import com.platform.util.PlatformUtil;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/email")
@ValidateUserToken
public class EmailController {

	@Autowired
	private EmailService emailService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PROMOTIONS })
	@PostMapping(value = "/template", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<EmailTemplate> addEmailTemplate(@RequestParam("name") String templateName,
			@RequestParam("file") MultipartFile file) throws IOException {
		GenericResponse<EmailTemplate> response = new GenericResponse<>();
		if (!EmailTemplatePlaceholderConfiguration.isValidTemplateName(templateName)) {
			return response.setStatus(Response.Status.NO_CONTENT).build();
		}
		return response.setStatus(Response.Status.OK)
				.setData(emailService.createTemplate(templateName,
						BaseUtil.generateFileFromMutipartFile(file,
								templateName + PlatformUtil.MINUS_SEPERATOR + BaseSession.getTenantUniqueName(),
								PlatformUtil.TEMPLATE_EXTENTION)))
				.build();
	}                                                                                                   

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PROMOTIONS })
	@GetMapping(value = "/template", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<EmailTemplate> getAllTemplates() {
		GenericResponse<EmailTemplate> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(emailService.getAllTemplates()).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PROMOTIONS })
	@GetMapping(value = "/download/{templateName}")
	public void downloadTemplate(@PathVariable String templateName, HttpServletResponse response) throws IOException {
		File downloadFile = null;
		try (OutputStream os = response.getOutputStream()) {
			downloadFile = emailService.downloadTemplateFile(templateName);
			Assert.notNull(downloadFile, "Template File is not available");
			byte[] isr = Files.readAllBytes(downloadFile.toPath());
			ByteArrayOutputStream out = new ByteArrayOutputStream(isr.length);
			out.write(isr, 0, isr.length);
			response.setContentType("application/ftl");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			// Use 'inline' for preview and 'attachement' for download in browser.
			response.addHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
			out.writeTo(os);
		} finally {
			response.flushBuffer();
			FileUtil.deleteDirectoryOrFile(downloadFile);
		}
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PROMOTIONS })
	@GetMapping(value = "/templatenames", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Map<String, List<String>>> getAllAvailableEmailTemplatesInfo() {
		GenericResponse<Map<String, List<String>>> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(EmailTemplatePlaceholderConfiguration.getAllTemplateNamesmap())
				.setDataList(emailService.getAllTemplateNamesForTenant()).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/configurationkeys", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ConfigurationType> getEmailConfigurationProperties() {
		GenericResponse<ConfigurationType> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(ConfigurationType.EMAIL)
				.setDataList(EmailConfigurations.stream().toList()).build();
	}
	
	/*@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PatchMapping(value = "/loadconfig", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<?> loadTenantEmailConfig() {
		GenericResponse<?> response = new GenericResponse<>();
		//emailService.loadEmailCacheForTenant();
		return response.setStatus(Response.Status.OK).build();
	}*/
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PROMOTIONS })
	@GetMapping(value = "/inbox", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<String> getInboxUrl() {
		GenericResponse<String> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(emailService.getMailInboxUrl()).build();
	}

}
