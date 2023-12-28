package com.user.bgwork;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGJob;
import com.base.bgwork.BGWorkUtil;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.exception.BGWorkException;
import com.user.exception.TaskException;
import com.user.service.TaskService;

/**
 * @author muhil
 * Scheduled for every day at 12AM
 */
@Component
public class TaskStatusUpdaterScheduledTask extends BGJob {
	
	@Autowired
	private TaskService taskService;
	
	@Override
	public void schedule() throws SchedulerException {
//		BGWorkUtil.scheduleCronJob(this.getClass().getSimpleName(), this.getClass(), "0 * 0 ? * * *");
		BGWorkUtil.scheduleCronJob(this.getClass().getSimpleName(), this.getClass(), "0 0/1 * ? * * *", true);
	}
	
	@Override
	public void run(JobExecutionContext context) throws BGWorkException {
		Log.tenant.info("Executing Task status check for tenant : {}", BaseSession.getTenantUniqueName());
		try {
			taskService.checkAndUpdateTaskStatus();
		} catch (TaskException e) {
			throw new BGWorkException(e.getMessage());
		}
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
