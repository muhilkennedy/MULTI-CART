package com.i18n.configuration;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


/**
 * @author Muhil
 * Load message bundles messages_<locale>.properties
 */
@Configuration
@PropertySource("classpath:i18n.properties")
public class LocaleConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(LocaleConfiguration.class);
	
	@Value("${i18n.message.baseNames}")
    private String[] baseNames;

	/*@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}*/

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(baseNames);
		messageSource.setDefaultEncoding("UTF-8");
		logger.info("I18N message source initialized - {}", messageSource);
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
		return new MessageSourceAccessor(messageSource, Locale.US);
	}

}
