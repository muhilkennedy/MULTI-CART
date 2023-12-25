package com.user.bgwork;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGJob;
import com.base.bgwork.BGWorkUtil;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.exception.BGWorkException;
import com.user.service.UserService;

/**
 * @author muhil
 * Scheduled for every day at 3AM
 */
@Component
public class BirthdayWishesScheduledTask extends BGJob {
	
	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;
	
	@Override
	public void schedule() throws SchedulerException {
		BGWorkUtil.scheduleCronJob(this.getClass().getSimpleName(), this.getClass(), "0 * 3 ? * * *");
	}
	
	@Override
	public void run(JobExecutionContext context) {
		Log.tenant.info("Executing Birthday wishes task for tenant : {}", BaseSession.getTenantUniqueName());
		empService.sendBirthdayWishesMail();
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
