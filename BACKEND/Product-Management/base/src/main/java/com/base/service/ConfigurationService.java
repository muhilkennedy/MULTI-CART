package com.base.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.ConfigType;
import com.base.jpa.repository.ConfigTypeRepository;
import com.base.messages.ConfigRequest;
import com.platform.configuration.ConfigurationFactory;
import com.platform.messages.ConfigurationType;
import com.platform.service.Configurable;

/**
 * @author Muhil
 */
@Service
public class ConfigurationService implements BaseService {

	@Autowired
	private ConfigTypeRepository configRepository;

	@Override
	public BaseEntity findById(Long rootId) {
		return configRepository.findById(rootId).get();
	}

	public List<ConfigType> findAllConfigs() {
		return configRepository.findAll();
	}
	
	public List<ConfigType> findAllConfigsFromTimeUpdated(Long fromTime) {
		return configRepository.findAllFromTime(fromTime);
	}

	public List<ConfigType> findAllConfig(ConfigurationType type) {
		return configRepository.findAllConfigByType(type.name());
	}
	
	public List<ConfigType> findByConfigNameAndType(String name, ConfigurationType type) {
		return configRepository.findByConfig(name, type.name());
	}
	
	public ConfigType save(ConfigType config) {
		return configRepository.save(config);
	}
	
	private void applyConfigForTenant(ConfigurationType type) {
		Optional<Configurable> configurable = ConfigurationFactory.getInstance().getConfigurable(type);
		if(configurable.isPresent()) {
			configurable.get().applyConfiguration();
		}
	}
	
	public ConfigType createOrUpdateConfig(String key, String value, ConfigurationType type) {
		return createOrUpdateConfig(key, value, type.name());
	}

	public ConfigType createOrUpdateConfig(String key, String value, String type) {
		ConfigType config = getConfigIfPresent(key, type);
		if (config == null) {
			config = new ConfigType();
			config.setName(key);
			config.setConfigtype(type);
		}
		config.setVal(value);
		configRepository.saveAndFlush(config);
		applyConfigForTenant(ConfigurationType.valueOf(type));
		return config;
	}

	public void createOrUpdateConfigs(List<ConfigRequest> requestList) {
		requestList.stream().forEach(request -> {
			ConfigType config = getConfigIfPresent(request.getKey(), request.getType());
			if (config == null) {
				config = new ConfigType();
				config.setName(request.getKey());
				config.setConfigtype(request.getType());
			}
			config.setVal(request.getValue());
			configRepository.saveAndFlush(config);
		});
		requestList.stream().map(request -> ConfigurationType.valueOf(request.getType())).distinct().forEach(type -> {
			applyConfigForTenant(type);
		});
	}

	/**
	 * @param name
	 * @param type
	 * @return unique key and type combination for tenant.
	 */
	public String getConfigValueIfPresent(String name, ConfigurationType type) {
		List<ConfigType> config = configRepository.findByConfig(name, type.name());
		if (config != null && !config.isEmpty()) {
			return config.get(0).getVal();
		}
		return null;
	}
	
	public ConfigType getConfigIfPresent(String name, ConfigurationType type) {
		return getConfigIfPresent(name, type.name());
	}
	
	public ConfigType getConfigIfPresent(String name, String type) {
		List<ConfigType> config = configRepository.findByConfig(name, type);
		if (config != null && !config.isEmpty()) {
			return config.get(0);
		}
		return null;
	}

}
