package com.base.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.base.entity.ConfigType;
import com.base.service.ConfigurationService;
import com.base.util.Log;
import com.base.util.PropertiesUtil;
import com.platform.exception.CryptoException;
import com.platform.messages.ConfigurationType;
import com.platform.messages.StoreType;
import com.platform.service.GoogleMessagingService;
import com.platform.util.FileCryptoUtil;
import com.platform.util.FileUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author Muhil
 * Initialize cloud messaging objects.
 */
@Configuration
public class PushMessageConfiguration {

	@Value("${app.gcs.dev.config}")
	private boolean loadDevFile;

	@Autowired
	private ConfigurationService configService;

	@PostConstruct
	private void setUpCloudMessagingService() throws IOException, CryptoException {
		if (PropertiesUtil.isProdDeployment()) {
			initFirebaseMessagingForTenants();
			Log.base.info("----- PROD Cloud Messaging Service intialized -----");
		} else {
			if (loadDevFile) {
				File tempFile = null;
				try (InputStream is = getClass().getResourceAsStream("/gcs/config.json");) {
					tempFile = File.createTempFile("config", ".json");
					FileUtils.copyInputStreamToFile(is, tempFile);
					tempFile = FileCryptoUtil
							.decrypt(new String(Base64.getDecoder().decode(PropertiesUtil.getFileSecret()),
									StandardCharsets.UTF_8), tempFile);
					GoogleMessagingService.init(tempFile);
				} finally {
					FileUtil.deleteDirectoryOrFile(tempFile);
				}

			} else {
				initFirebaseMessagingForTenants();
			}
			Log.base.info("----- DEV Cloud Messaging Service intialized -----");
		}
		Log.base.info("----- Default Cloud messaing type {} -----", StoreType.GCP.name());
	}

	private void initFirebaseMessagingForTenants() throws IOException {
		List<ConfigType> storageConfigs = configService
				.findByConfigNameAndType(StoreType.GCP_CONSTANTS.GCPCONFIG.name(), ConfigurationType.STORAGE);
		Map<Long, String> storageMap = storageConfigs.stream()
				.collect(Collectors.toMap(ConfigType::getTenantid, ConfigType::getVal));
		GoogleMessagingService.initForTenants(storageMap);
	}

}
