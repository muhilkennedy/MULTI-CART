package com.product.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;

import com.base.util.Log;
import com.platform.email.EmailTemplatePlaceholderConfiguration;
import com.platform.util.FileUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 * load supplier email template names into platform cache.
 */
@Configuration
public class ProductEmailTemplateNameConfiguration {

	@PostConstruct
	private void loadTemplateNameFile() throws InterruptedException {
		File tempFile = null;
		try (InputStream is = getClass()
				.getResourceAsStream("/productEmailTemplates/ProductTemplatePlaceholdersConfig.json");) {
			tempFile = File.createTempFile("ProductTemplatePlaceholdersConfig", ".json");
			FileUtils.copyInputStreamToFile(is, tempFile);
			EmailTemplatePlaceholderConfiguration.getInstance().loadTemplateNameFile(tempFile);
		} catch (IOException e) {
			Log.supplier.error("Error loading product email templates : ", e);
		} finally {
			FileUtil.deleteDirectoryOrFile(tempFile);
		}
	}
}
