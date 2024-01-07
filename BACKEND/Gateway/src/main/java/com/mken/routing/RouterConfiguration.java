package com.mken.routing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfiguration {

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		//r.host("*.limited.org").and()
		return builder.routes()
				.route("Tenant-Management", r -> r.path("/tm/**").uri("http://localhost:8081"))
				.route("Product-Management", r -> r.path("/pm/**").uri("http://localhost:8082"))
				.build();
	}
	
//	@Bean
//    public CorsWebFilter corsWebFilter() {
//
//        final CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfig.setMaxAge(3600L);
//        corsConfig.setAllowedMethods(Arrays.asList("*"));
//        corsConfig.addAllowedHeader("*");
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return new CorsWebFilter(source);
//    }  
	
}
