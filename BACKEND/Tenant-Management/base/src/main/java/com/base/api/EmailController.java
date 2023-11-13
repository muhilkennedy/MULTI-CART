package com.base.api;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.platform.messages.EmailConfigurations;
import com.platform.messages.EmailTemplateNames;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.platform.util.PlatformUtil;

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
		Optional<EmailTemplateNames> template = EmailTemplateNames.getTemplateName(templateName);
		if (template.isEmpty()) {
			return response.setStatus(Response.Status.NO_CONTENT).build();
		}
		return response.setStatus(Response.Status.OK)
				.setData(
						emailService.createTemplate(template.get().name(),
								BaseUtil.generateFileFromMutipartFile(file,
										template.get().name() + PlatformUtil.MINUS_SEPERATOR
												+ BaseSession.getTenantUniqueName(),
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
	@GetMapping(value = "/templatenames", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Map> getAllAvailableEmailTemplatesInfo() {
		GenericResponse<Map> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(EmailTemplateNames.getAvailableTemplateNames()).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/configurationkeys", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<EmailConfigurations> getEmailConfigurationProperties() {
		GenericResponse<EmailConfigurations> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(EmailConfigurations.stream().toList()).build();
	}

}
