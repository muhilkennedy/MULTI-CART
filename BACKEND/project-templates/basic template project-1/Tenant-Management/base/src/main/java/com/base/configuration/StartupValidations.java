package com.base.configuration;


import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.PostConstruct;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.base.util.CacheUtil;
import com.base.util.Log;
import com.platform.annotations.ClassMetaProperty;


/**
 * @author Muhil
 *
 */
@Configuration
public class StartupValidations {

	@Autowired
	@Qualifier("requestMappingHandlerMapping")
	private RequestMappingHandlerMapping requestHandlerMapping;
	
//	@Autowired
//	private CacheManager cacheManager;          
	
	/**
	 * Execute methods on post construct
	 */
	@PostConstruct
	private void postStartupProcessing() {
		validateClassCode();
		logRestEndpoints();
		//clearCacheOnStartup();
	}
	
	/**
	 * Identify if same class code for entity tables.
	 */
	private void validateClassCode() {
		Reflections reflection = new Reflections("com.mken.");
		Set<String> uniqueSet = new HashSet<String>();
		Set<Class<?>> classes = reflection.getTypesAnnotatedWith(ClassMetaProperty.class);
		long count = classes.stream().map(cls -> (ClassMetaProperty) cls.getAnnotation(ClassMetaProperty.class))
				.filter(cls -> !uniqueSet.add(cls.code())).count();
		Assert.isTrue(!(count > 0), "Duplicate Class Codes Detected");
		Log.base.debug("Class codes validated");
	}
	
	/**
	 * Print api endpoint details.
	 */
	private void logRestEndpoints() {
		Log.base.debug("Available Rest Endpoints :");
		this.requestHandlerMapping.getHandlerMethods().forEach((key, value) -> {
			Log.base.debug("Endpoint detail {} :: description {}", key, value.getMethod().toGenericString());
		});
	}
	
	/**
	 * @param event
	 * Execute after application startup
	 */
	@EventListener
	private void onApplicationEvent(ApplicationReadyEvent event) {
		clearInitialCaches();
	}

	private void clearInitialCaches() {
//		try {
//			CacheUtil.getAllCacheNames().parallelStream().filter(name -> cacheManager.getCache(name) != null)
//					.peek(cache -> Log.base.warn("Clearing cache {} ", cache)).forEach(cache -> {
//						cacheManager.getCache(cache).clear();
//					});
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			Log.base.error("Exception while cleaning cache {}", e);
//		}
	}

}
