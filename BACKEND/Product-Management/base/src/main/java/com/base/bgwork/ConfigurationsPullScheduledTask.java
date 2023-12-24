package com.base.bgwork;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.configuration.EmailConfiguration;
import com.base.configuration.PushMessageConfiguration;
import com.base.configuration.StorageConfiguration;
import com.base.entity.ConfigType;
import com.base.server.BaseSession;
import com.base.service.ConfigurationService;
import com.base.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.exception.BGWorkException;
import com.platform.exception.CryptoException;
import com.platform.messages.GenericResponse;
import com.platform.service.HttpClient;
import com.platform.util.HttpUtil;
import com.platform.util.PlatformPropertiesUtil;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 * runs spring scheduled task every 6 hours to pull configurations
 *         for tenant.
 */
@Component
@PersistJobDataAfterExecution //we can persist job data jobdata map for subsequent executions
@DisallowConcurrentExecution
public class ConfigurationsPullScheduledTask extends BGJob {

	private static boolean _initialLoadDone = false;

	@Autowired
	private ConfigurationService configService;
	
	@Autowired
	private StorageConfiguration storageConfig;
	
	@Autowired
	private EmailConfiguration emailConfig;
	
	@Autowired
	private PushMessageConfiguration pushMessageConfig;

	@Override
	public void schedule() throws SchedulerException {
		BGWorkUtil.scheduleBasicJob(this.getClass().getSimpleName(), this.getClass(), TimeUnit.HOURS, 6);
		// triggering on server startup to sync configs
		BGWorkUtil.forceFireJob(new JobKey(this.getClass().getSimpleName(), PlatformUtil.INTERNAL_SYSTEM));
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.runForAllTenants(arg0);
			/* After startup initial load lets update the storage configs */
			if (!_initialLoadDone) {
				storageConfig.setUpStorageServices();
				pushMessageConfig.setUpCloudMessagingService();
				emailConfig.loadTenantsEmailConfiguration();
			}
		} catch (BGWorkException | IOException | CryptoException e) {
			throw new JobExecutionException(e);
		}
	}

	@Override
	public void run(JobExecutionContext context) throws BGWorkException {
		Log.base.info("Starting ConfigurationsPullScheduledTask");
		try {
			NameValuePair tenantParam = new BasicNameValuePair("tenantId", BaseSession.getTenantId().toString());
			Long lastUpdatedTime = (Long) context.getJobDetail().getJobDataMap().get(BaseSession.getTenantUniqueName());
			if (lastUpdatedTime == null) {
				lastUpdatedTime = 0L;
			}
			NameValuePair timeParam = new BasicNameValuePair("fromTime", String.valueOf(lastUpdatedTime));
			HttpClient<GenericResponse> client = new HttpClient<GenericResponse>(new GenericResponse());
			GenericResponse response = client.get(
					PlatformPropertiesUtil.getTMFrontDoorUrl().concat("/admin/base/config/pull"),
					HttpUtil.getDefaultSystemHeaders(), Arrays.asList(tenantParam, timeParam));
			Gson gson = new Gson();
			String json = gson.toJson(response.getDataList());
			Type listType = new TypeToken<List<ConfigType>>() {
			}.getType();
			List<ConfigType> configs = new Gson().fromJson(json, listType);
			for (ConfigType config : configs) {
				configService.createConfig(config.getName(), config.getVal(), config.getConfigtype());
				lastUpdatedTime = config.getTimeupdated();
			}
			/* update last updated time. */
			context.getJobDetail().getJobDataMap().put(BaseSession.getTenantUniqueName(), lastUpdatedTime);
		} catch (IOException | URISyntaxException e) {
			throw new BGWorkException(e);
		}

		Log.base.info("Completed ConfigurationsPullScheduledTask");
	}

}
