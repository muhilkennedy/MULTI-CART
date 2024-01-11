package com.mken.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author muhil
 * Allow all CORS request.
 */
@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
public class SecurityConfiguration {
	
	/**
	 * added to avoid auto user/password generation by spring security.
	 */
//	@Bean
//	UserDetailsService emptyDetailsService() {
//		return username -> {
//			throw new UsernameNotFoundException("N/A");
//		};
//	}

	@Bean
	public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// csrf doesnt matter as ours are stateless apis.
//		return http.csrf((csrf) -> csrf.disable())
//				 .cors((cor) -> cor.configurationSource(corsConfigurationSource()))
//				.authorizeHttpRequests(config -> {
//					config.anyRequest().permitAll();
//				})
//				.sessionManagement(config -> {
//					config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				}).build();
		
		return http.csrf((csrf) -> csrf.disable()).cors().disable().build();
	}
	
	
	@Bean
	public org.springframework.web.cors.reactive.CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("X-Token"));
		configuration.setMaxAge(3600L);
		org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
//	@Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
//		
//		return http.csrf((csrf) -> csrf.disable())
//				.cors((cor) -> cor.disable()).build();
//				 .cors((cor) -> cor.configurationSource(corsConfigurationSource())).build();
//				.authorizeHttpRequests(config -> {
//					config.anyRequest().permitAll();
//				})
//				.sessionManagement(config -> {
//					config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				}).build();
				
//        http.cors().configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//                    configuration.setAllowedMethods(List.of("*"));
//                    configuration.setAllowedHeaders(List.of("*"));
//                    return configuration;
//                });
//        http.csrf().disable();
//        return http.build();
//    }

}


//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//	/**
//	 * added to avoid auto user/password generation by spring security.
//	 */
//	@Bean
//	UserDetailsService emptyDetailsService() {
//		return username -> {
//			throw new UsernameNotFoundException("N/A");
//		};
//	}
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		// csrf doesnt matter as ours are stateless apis
//		// http.csrf().csrfTokenRepository(csrfTokenRepository());
//		// allowing all request patterns
//		return http.csrf((csrf) -> csrf.disable())
//				// .headers((header) -> header.frameOptions((frame) -> frame.disable()))
//				 .cors((cor) -> cor.configurationSource(corsConfigurationSource()))
//				.authorizeHttpRequests(config -> {
//					config.anyRequest().permitAll();
//				})
//				// Disable "JSESSIONID" cookies
//				.sessionManagement(config -> {
//					config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				}).build();
//				
//				/*.oauth2Login(config -> {
//					// incase of server side oauth verification
//				}).build();*/
//	}
//
//	/*
//	 * @Bean public CsrfTokenRepository csrfTokenRepository() {
//	 * CookieCsrfTokenRepository repository =
//	 * CookieCsrfTokenRepository.withHttpOnlyFalse(); repository.setCookiePath("/");
//	 * // repository.setCookieName("mkencookie"); //
//	 * repository.setHeaderName("csrf-token"); return repository; }
//	 */
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));//read from props file
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE"));
//		configuration.setAllowedHeaders(Arrays.asList("*"));
//		configuration.setExposedHeaders(Arrays.asList("*"));
//		configuration.setMaxAge(3600L);
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//
////}
