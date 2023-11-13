package com.base.security;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.platform.util.PlatformUtil;

/**
 * @author muhil
 * Allow all CORS request and handle in filter.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	/**
	 * added to avoid auto user/password generation by spring security.
	 */
	@Bean
	UserDetailsService emptyDetailsService() {
		return username -> {
			throw new UsernameNotFoundException("N/A");
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// csrf doesnt matter as ours are stateless apis
		// http.csrf().csrfTokenRepository(csrfTokenRepository());
		// allowing all request patterns
		http.csrf((csrf) -> csrf.disable()).headers((header) -> header.frameOptions((frame) -> frame.disable()));
		http.authorizeHttpRequests((authz) -> authz.requestMatchers("/**").permitAll());
		//http.cors().and().authorizeHttpRequests().requestMatchers("/**").permitAll();
		return http.build();
	}

	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
		CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		repository.setCookiePath("/");
		// repository.setCookieName("mkencookie");
		// repository.setHeaderName("csrf-token");
		return repository;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	  CorsConfiguration configuration = new CorsConfiguration();
	  configuration.setAllowedOrigins(Arrays.asList("*"));
	  configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE"));
	  configuration.setAllowCredentials(true);
	  configuration.setAllowedHeaders(Arrays.asList("*")); 
	  configuration.setExposedHeaders(Arrays.asList(PlatformUtil.TOKEN_HEADER));
	  configuration.setMaxAge(3600L);
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  source.registerCorsConfiguration("/**", configuration);
	  return source;
	}

}

