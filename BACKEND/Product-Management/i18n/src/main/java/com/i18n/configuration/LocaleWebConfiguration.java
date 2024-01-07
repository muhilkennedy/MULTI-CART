package com.i18n.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Muhil
 *
 */
@Configuration
public class LocaleWebConfiguration implements WebMvcConfigurer {

	private final LocaleInterceptor localeInterceptor;

	public LocaleWebConfiguration(LocaleInterceptor localeInterceptor) {
		this.localeInterceptor = localeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(localeInterceptor);
	}

}
