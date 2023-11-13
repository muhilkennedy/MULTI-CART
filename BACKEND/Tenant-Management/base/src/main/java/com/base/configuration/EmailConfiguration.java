package com.base.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.base.entity.ConfigType;
import com.base.service.ConfigurationService;
import com.base.util.Log;
import com.platform.cache.EmailCache;
import com.platform.messages.ConfigurationType;
import com.platform.messages.EmailConfigurations;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;

/**
 * @author Muhil
 */
@Configuration
public class EmailConfiguration {

	@Autowired
	private ConfigurationService configService;

	@PostConstruct
	private void loadTenantsEmailConfiguration() {
		Log.base.debug("Starting to load email configurations into platform cache");
		List<ConfigType> emailConfigs = configService.findAllConfig(ConfigurationType.EMAIL);
		Map<Long, Properties> configMap = new HashMap<Long, Properties>();
		emailConfigs.parallelStream().forEach(config -> {
			if (configMap.containsKey(config.getTenantid())) {
				configMap.get(config.getTenantid()).put(getPropertyKey(config.getName()), config.getVal());
			} else {
				Properties property = new Properties();
				property.put(getPropertyKey(config.getName()), config.getVal());
				configMap.put(config.getTenantid(), property);
			}
		});
		configMap.entrySet().stream().forEach(entry -> {
			EmailCache.getInstance().add(entry.getKey(), entry.getValue());
		});
		Log.base.info("Loaded Email configurations into platform cache.");
	}

	private String getPropertyKey(String name) {
		Optional<EmailConfigurations> config = EmailConfigurations.getConfigByName(name);
		if (config.isEmpty()) {
			throw new NotFoundException();
		}
		return config.get().getProperty();
	}

}
