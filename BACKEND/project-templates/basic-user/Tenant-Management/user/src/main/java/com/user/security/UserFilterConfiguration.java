package com.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.service.FilterUtil;
import com.base.util.Log;

/**
 * @author Muhil
 *
 */
@Configuration
public class UserFilterConfiguration {
	@Autowired
	private UserTokenSecurityFilter userFilter;

	@Bean
	public FilterRegistrationBean<UserTokenSecurityFilter> UserSecurityFilterRegistration() {
		Log.user.info("----- User Filter Registrarion -----");
		FilterRegistrationBean<UserTokenSecurityFilter> registration = new FilterRegistrationBean<UserTokenSecurityFilter>();
		registration.setFilter(userFilter);
		registration.addUrlPatterns(FilterUtil.getValidateUserTokenUrlPatterns());
		return registration;
	}
}
