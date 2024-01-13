package com.mken.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
	
	public static final Logger logger = LoggerFactory.getLogger("com.mken");

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		logger.info("Heap Size = {}", (Runtime.getRuntime().totalMemory() / 1000000000.0) + " GB");
		logger.info("Max Memory Size = {}", (Runtime.getRuntime().maxMemory() / 1000000000.0) + " GB");
		logger.info("Total Memory Size = {}", (Runtime.getRuntime().freeMemory() / 1000000000.0) + " GB");
		logger.info("##############################");
		logger.info("Application Startup Completed");
		logger.info("##############################");
	}

}
