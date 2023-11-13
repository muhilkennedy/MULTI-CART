package com.base.bgwork;

import javax.sql.DataSource;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.configuration.JobRunrConfiguration.JobRunrConfigurationResult;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.util.Log;

/**
 * @author Muhil 
 * Background job runr config. Default data source used for job tracking.
 */
@Configuration
@ConditionalOnProperty(prefix = "org.jobrunr.job-scheduler", value = "enabled", havingValue = "true")
public class JobRunrConfiguration {
	
	@Value("${org.jobrunr.dashboard.port}")
	private int port;

	@Bean
	public JobRunrConfigurationResult initJobRunr(DataSource dataSource, JobActivator jobActivator) {
		Log.base.info("------ Initializing JobRunr BGWork server ------");
		JobRunrConfigurationResult result = JobRunr.configure()
                .useJobActivator(jobActivator)
                .useStorageProvider(SqlStorageProviderFactory
                          .using(dataSource))
                .useBackgroundJobServer()
                .useDashboard()
                .initialize();
        Log.base.info("----- JobRunr server started in port {} -----", port);
        return result;
	}
	
}
