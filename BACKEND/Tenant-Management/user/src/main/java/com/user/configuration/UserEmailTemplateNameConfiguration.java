package com.user.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;

import com.base.util.Log;
import com.platform.email.EmailTemplatePlaceholderConfiguration;
import com.platform.util.FileUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 * load user email template names into platform cache.
 */
@Configuration
public class UserEmailTemplateNameConfiguration {
	
	@PostConstruct
	private void loadTemplateNameFile() throws InterruptedException {
		File tempFile = null;
		try (InputStream is = getClass()
				.getResourceAsStream("/userEmailTemplates/UserTemplatePlaceholdersConfig.json");) {
			tempFile = File.createTempFile("UserTemplatePlaceholdersConfig", ".json");
			FileUtils.copyInputStreamToFile(is, tempFile);
			EmailTemplatePlaceholderConfiguration.getInstance().loadTemplateNameFile(tempFile);
		} catch (IOException e) {
			Log.user.error("Error loading user email templates : " , e);
		} finally {
			FileUtil.deleteDirectoryOrFile(tempFile);
		}	
	}
	
}
