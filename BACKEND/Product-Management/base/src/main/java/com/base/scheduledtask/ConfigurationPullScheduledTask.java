package com.base.scheduledtask;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGJob;
import com.base.bgwork.BGWorkUtil;
import com.base.entity.ConfigType;
import com.base.messages.ConfigRequest;
import com.base.server.BaseSession;
import com.base.service.ConfigurationService;
import com.base.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.exception.BGWorkException;
import com.platform.messages.GenericResponse;
import com.platform.service.HttpClient;
import com.platform.util.HttpUtil;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformPropertiesUtil;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 * pulls configs from Tenant service. TODO: config update should be handled by EDD(KAFKA).
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ConfigurationPullScheduledTask extends BGJob {

	@Autowired
	private ConfigurationService configService;

	@Override
	public void schedule() throws SchedulerException {
		BGWorkUtil.scheduleBasicJob(this.getClass().getSimpleName(), this.getClass(), TimeUnit.HOURS, 1);
	}

	@Override
	public void run(JobExecutionContext context) throws BGWorkException {
		Log.base.info("Starting ConfigurationPullTask");
		Long lastTimeUpdated = (Long) context.getJobDetail().getJobDataMap().get(HttpHeaders.IF_MODIFIED_SINCE);
		if (lastTimeUpdated == null) {
			lastTimeUpdated = 0L;
		}
		List<ConfigRequest> configRequests = new ArrayList<ConfigRequest>();
		HttpClient<GenericResponse> client = new HttpClient<GenericResponse>(new GenericResponse());
		Header tenantHeader = new BasicHeader(HttpUtil.HEADER_TENANT, PlatformUtil.INTERNAL_SYSTEM);
		Header authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, JWTUtil.generateSystemUserToken());
		Header fromTimeHeader = new BasicHeader(HttpHeaders.IF_MODIFIED_SINCE, String.valueOf(lastTimeUpdated));
		NameValuePair tenantIdParam = new BasicNameValuePair("tenantId", String.valueOf(BaseSession.getTenantId()));
		try {
			GenericResponse response = client.get(
					PlatformPropertiesUtil.getTMFrontDoorUrl() + "/admin/base/config/pull",
					Arrays.asList(tenantHeader, authHeader, fromTimeHeader), Arrays.asList(tenantIdParam));
			Gson gson = new Gson();
			String json = gson.toJson(response.getDataList());
			Type listType = new TypeToken<List<ConfigType>>() {
			}.getType();
			List<ConfigType> configs = new Gson().fromJson(json, listType);
			if (configs != null && !configs.isEmpty()) {
				for (ConfigType config : configs) {
					ConfigRequest request = new ConfigRequest();
					request.setKey(config.getName());
					request.setValue(config.getVal());
					request.setType(config.getConfigtype());
					configRequests.add(request);
					if (config.getTimeupdated() > lastTimeUpdated) {
						lastTimeUpdated = config.getTimeupdated();
					}
				}
			}
			configService.createOrUpdateConfigs(configRequests);
			Log.base.info("Configs last updated time : {}", lastTimeUpdated);
			context.getJobDetail().getJobDataMap().put(HttpHeaders.IF_MODIFIED_SINCE, lastTimeUpdated);
		} catch (IOException | URISyntaxException e) {
			Log.base.error("Tenant fetch exception : " + e.getMessage());
			throw new BGWorkException(e.getMessage());
		}
		Log.base.info("Completed ConfigurationPullTask");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			runForAllTenants(arg0);
		} catch (BGWorkException e) {
			throw new JobExecutionException(e);
		}
	}

}
