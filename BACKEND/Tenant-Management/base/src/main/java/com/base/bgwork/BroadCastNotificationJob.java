package com.base.bgwork;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.messages.NotificationRequest;
import com.base.service.NotificationService;
import com.google.gson.Gson;

/**
 * @author Muhil 
 * Job to create notfication for all employees
 */
@Component
public class BroadCastNotificationJob extends BGJob {

	@Autowired
	private NotificationService notificationService;

	@Override
	public void schedule() throws SchedulerException {
		// No-Op
	}

	@Override
	public void run(JobExecutionContext context) {
		// No-Op
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap map = arg0.getJobDetail().getJobDataMap();
		setupSession(map.getLong("tenant"), map.getLong("user"));
		try {
			// This is a workaround fix for unnamed module error (need to check by disabling spring devtools)
			Object obj = map.get("request");
			NotificationRequest nr = new NotificationRequest();
			Gson gson = new Gson();
			String jsonString = gson.toJson(obj);
			nr = gson.fromJson(jsonString, NotificationRequest.class);
			notificationService.broadCastNotificationJob(nr);
		} catch (Exception e) {
			throw new JobExecutionException(e.getMessage());
		} finally {
			teardownSession();
		}
	}

}