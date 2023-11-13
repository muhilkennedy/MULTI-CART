package com.base.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailTemplateConfiguration {

	@Value("${app.admin.email}")
	private String adminEmailId;

	@Value("${app.admin.email.password}")
	private String adminEmailPassword;
	
	@Value("${app.email.templates.path}")
	private String[] templatePath;

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean emailTemplateBean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPaths(templatePath);
		return bean;
	}

}

