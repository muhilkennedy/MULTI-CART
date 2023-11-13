package com.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.ConfigType;
import com.base.jpa.repository.ConfigTypeRepository;
import com.platform.messages.ConfigurationType;

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

	public List<ConfigType> findAllConfig(ConfigurationType type) {
		return configRepository.findAllConfigByType(type.name());
	}
	
	public List<ConfigType> findByConfigNameAndType(String name, ConfigurationType type) {
		return configRepository.findByConfig(name, type.name());
	}
	
	public ConfigType save(ConfigType config) {
		return configRepository.save(config);
	}
	
	public ConfigType createConfig(String key, String value, ConfigurationType type) {
		ConfigType config = getConfigIfPresent(key, type);
		if(config == null) {
			config = new ConfigType();
			config.setName(key);
			config.setConfigtype(type.name());
		}
		config.setVal(value);
		return configRepository.saveAndFlush(config);
	}
	
	public ConfigType createConfig(String key, String value, String type) {
		ConfigType config = getConfigIfPresent(key, type);
		if(config == null) {
			config = new ConfigType();
			config.setName(key);
			config.setConfigtype(type); //TODO: validate against enum to assert
		}
		config.setVal(value);
		return configRepository.saveAndFlush(config);
	}

	/**
	 * @param name
	 * @param type
	 * @return unique key and type combination for tenant.
	 */
	public String getConfigValueIfPresent(String name, ConfigurationType type) {
		List<ConfigType> config = configRepository.findByConfig(name, type.name());
		if (config != null) {
			return config.get(0).getVal();
		}
		return null;
	}
	
	public ConfigType getConfigIfPresent(String name, ConfigurationType type) {
		List<ConfigType> config = configRepository.findByConfig(name, type.name());
		if (config != null && !config.isEmpty()) {
			return config.get(0);
		}
		return null;
	}
	
	public ConfigType getConfigIfPresent(String name, String type) {
		List<ConfigType> config = configRepository.findByConfig(name, type);
		if (config != null && !config.isEmpty()) {
			return config.get(0);
		}
		return null;
	}

}
