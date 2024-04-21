package com.base.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.base.entity.ConfigType;
import com.base.server.BaseSession;
import com.base.service.ConfigurationService;
import com.base.util.EmailUtil;
import com.base.util.Log;
import com.platform.cache.EmailCache;
import com.platform.configuration.ConfigurationFactory;
import com.platform.messages.ConfigurationType;
import com.platform.service.Configurable;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 */
@Configuration
public class EmailConfiguration implements Configurable {

	@Autowired
	private ConfigurationService configService;

	@PostConstruct
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
		this.register();
	}

	public void loadEmailCacheForTenant() {
		List<ConfigType> emailConfigs = configService.findAllConfig(ConfigurationType.EMAIL);
		if(!emailConfigs.isEmpty()) {
			Properties property = new Properties();
			emailConfigs.stream().forEach(config -> {
				property.put(EmailUtil.getPropertyKey(config.getName()), config.getVal());
			});
			if (EmailCache.getInstance().get(BaseSession.getTenantId()) != null) {
				Log.base.warn("Evicted email config for tenant {}", BaseSession.getTenantId());
				EmailCache.getInstance().evict(BaseSession.getTenantId());
			}
			EmailCache.getInstance().add(BaseSession.getTenantId(), property);
		}
		else {
			Log.base.warn("No Email configs to load for tenant {}", BaseSession.getTenantId());
		}
	}

	@Override
	public void applyConfiguration() {
		this.loadEmailCacheForTenant();
	}

	@Override
	public void register() {
		ConfigurationFactory.getInstance().register(ConfigurationType.EMAIL, this);
	}
}
