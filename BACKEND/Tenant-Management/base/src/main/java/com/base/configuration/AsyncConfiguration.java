package com.base.configuration;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author muhil
 */
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

	private static final int ASYNC_THREADS = 10;

	@Bean
	protected WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
				configurer.setTaskExecutor(getTaskExecutor());
			}
		};
	}

	// TODO: read threadpool size from config file
	@Bean
	protected ConcurrentTaskExecutor getTaskExecutor() {
		return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(ASYNC_THREADS));
	}
}
