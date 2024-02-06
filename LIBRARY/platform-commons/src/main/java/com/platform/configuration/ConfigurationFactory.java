package com.platform.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.platform.messages.ConfigurationType;
import com.platform.service.Configurable;
import com.platform.util.Log;

/**
 * @author I339628
 */
public class ConfigurationFactory {

	private static ConfigurationFactory instance = new ConfigurationFactory();

	private Map<ConfigurationType, Configurable> configurables = new HashMap<ConfigurationType, Configurable>();

	private ConfigurationFactory() {

	}

	public static ConfigurationFactory getInstance() {
		return instance;
	}

	public void register(ConfigurationType type, Configurable configBean) {
		Log.platform.info("Registering Configurable for type : {}", type.name());
		configurables.put(type, configBean);
	}

	public Optional<Configurable> getConfigurable(ConfigurationType type) {
		return configurables.get(type) == null ? Optional.empty() : Optional.of(configurables.get(type));
	}

}
