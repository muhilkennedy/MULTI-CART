package com.user.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;

import com.platform.email.EmailTemplatePlaceholderConfiguration;

import jakarta.annotation.PostConstruct;

@Configuration
public class UserEmailTemplateNameConfiguration {

	@PostConstruct
	private void loadTemplateNameFile() {
		
		InputStream is = getClass().getResourceAsStream("/userEmailTemplates/UserTemplatePlaceholdersConfig.json");
		File tempFile;
		try {
			tempFile = File.createTempFile("UserTemplatePlaceholdersConfig", ".json");
			FileUtils.copyInputStreamToFile(is, tempFile);
			EmailTemplatePlaceholderConfiguration.getInstance().loadTemplateNameFile(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
