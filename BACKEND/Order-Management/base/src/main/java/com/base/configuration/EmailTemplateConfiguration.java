package com.base.configuration;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.platform.util.PlatformUtil;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * @author Muhil
 */
@Configuration
@PropertySource("classpath:email.properties")
public class EmailTemplateConfiguration {

	@Value("${app.admin.email}")
	private String adminEmailId;

	@Value("${app.admin.email.password}")
	private String adminEmailPassword;

	@Value("${app.email.templates.path}")
	private String[] templatePath;

	@Value("${app.nfs.path}")
	private String nfsPath;

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean emailTemplateBean() throws IOException {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		File templatesDirectory = new File(nfsPath + File.separator + PlatformUtil.TEMPLATES_FOLDER);
		if(!templatesDirectory.exists())
			templatesDirectory.mkdirs();
		bean.setTemplateLoaderPaths(templatePath);
		FileTemplateLoader ftl1 = new FileTemplateLoader(templatesDirectory);
		// ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
		TemplateLoader[] loaders = new TemplateLoader[] { ftl1 };
		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
		bean.setPreTemplateLoaders(mtl);
		return bean;
	}

}
