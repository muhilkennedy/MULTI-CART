package com.base.quartz;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author muhil
 *
 */
@Configuration
@ConditionalOnProperty(name = "spring.quartz.enabled", havingValue = "true")
public class QuartzSchedulerConfiguration {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private QuartzProperties quartzProperties;

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${spring.quartz.overwrite-existing-jobs}")
	private boolean overrideJobs;

	@Bean
	public JobFactory jobFactory() {
		ApplicationContextHolder jobFactory = new ApplicationContextHolder();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory());
		// TODO: override not happening need to be checked (for now check existing jobs before creating)
		factory.setOverwriteExistingJobs(true);
		factory.setWaitForJobsToCompleteOnShutdown(true);
		factory.setGlobalJobListeners(new QuartzJobListener());
		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());
		factory.setQuartzProperties(properties);
		return factory;
	}
}
