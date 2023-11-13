package com.tenant.TenantManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.platform.util.Log;

@SpringBootApplication
@ComponentScan(basePackages = { "com.base.*", "com.tenant.*", "com.user.*", "com.service.*" })
@ConfigurationPropertiesScan(basePackages = { "com.base.*", "com.tenant.*", "com.user.*", "com.service.*" })
@EntityScan(basePackages = { "com.base.*", "com.tenant.*", "com.user.*", "com.service.*" })
@EnableJpaRepositories(basePackages = { "com.base.jpa.*", "com.tenant.jpa.*", "com.user.jpa.*"})
@EnableR2dbcRepositories(basePackages = {"com.base.r2db.*"})
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
//@EnableCaching
public class TenantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantManagementApplication.class, args);
		Log.logger.info("Heap Size = " + (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		Log.logger.info("Max Memory Size = " + (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		Log.logger.info("Total Memory Size = " + (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
		Log.logger.info("##############################");
		Log.logger.info("Application Startup Completed");
		Log.logger.info("##############################");
	}

}
