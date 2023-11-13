package com.base.configuration;

import java.util.Properties;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.base.util.Log;
import com.base.util.PropertiesUtil;
import com.platform.configuration.PlatformConfiguration;
import com.platform.util.PlatformPropertiesUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 * configure required platform properties.
 *
 */
@Configuration
public class BasePlatformConfiguration implements EnvironmentAware {

	private PlatformConfiguration platformConfig;

	private Environment env;
	
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

	@PostConstruct
	private void initialize() {
		PropertiesUtil.initialize(env);
		Log.base.info("----- Initializing BasePlatformConfiguration -----");
		Properties frontDoorProperties = new Properties();
		PlatformPropertiesUtil.getMandatoryFrontdoorProperties().stream().forEach(property -> {
			frontDoorProperties.put(property, PropertiesUtil.getProperty(property));
		});
		Properties emailProperties = new Properties();
		PlatformPropertiesUtil.getMandatoryEmailProperties().stream().forEach(property -> {
			emailProperties.put(property, PropertiesUtil.getProperty(property));
		});
		Properties encryptionProperties = new Properties();
		PlatformPropertiesUtil.getMandatorySecretsProperties().stream().forEach(property -> {
			encryptionProperties.put(property, PropertiesUtil.getProperty(property));
		});
		platformConfig = new PlatformConfiguration.Builder().withFrontDoorProperties(frontDoorProperties)
				.withEmailProperties(emailProperties).withEncryptionProperties(encryptionProperties)
				.withAppName(PropertiesUtil.getProperty("spring.application.name")).isMultitenantApp(true).build();
	}

}
