package com.base.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.base.entity.ConfigType;
import com.base.service.ConfigurationService;
import com.base.util.EmailUtil;
import com.base.util.Log;
import com.platform.cache.EmailCache;
import com.platform.messages.ConfigurationType;

/**
 * @author Muhil
 */
@Configuration
public class EmailConfiguration {

	@Autowired
	private ConfigurationService configService;

	public void loadTenantsEmailConfiguration() {
		Log.base.debug("Starting to load email configurations into platform cache");
		List<ConfigType> emailConfigs = configService.findAllConfig(ConfigurationType.EMAIL);
		Map<Long, Properties> configMap = new HashMap<Long, Properties>();
		emailConfigs.stream().forEach(config -> {
			if (configMap.containsKey(config.getTenantid())) {
				configMap.get(config.getTenantid()).put(EmailUtil.getPropertyKey(config.getName()), config.getVal());
			} else {
				Properties property = new Properties();
				property.put(EmailUtil.getPropertyKey(config.getName()), config.getVal());
				configMap.put(config.getTenantid(), property);
			}
		});
		configMap.entrySet().stream().forEach(entry -> {
			EmailCache.getInstance().add(entry.getKey(), entry.getValue());
		});
		Log.base.info("Loaded Email configurations into platform cache.");
		//Log.base.debug("Email Template Names and Placeholders : {} ", EmailTemplateNames.getAvailableTemplateNames());
	}

}
