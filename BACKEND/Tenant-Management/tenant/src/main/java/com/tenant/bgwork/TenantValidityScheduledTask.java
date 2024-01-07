package com.tenant.bgwork;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGJob;
import com.base.bgwork.BGWorkUtil;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.exception.BGWorkException;
import com.tenant.service.TenantService;

/**
 * @author muhil
 * Scheduled for every day at 12AM
 */
@Component
@PersistJobDataAfterExecution //we can persist some data if required in jobdata map for subsequent executions
@DisallowConcurrentExecution
public class TenantValidityScheduledTask extends BGJob {
	
	@Autowired
	private TenantService tenantService;
	
	@Override
	public void schedule() throws SchedulerException {
		BGWorkUtil.scheduleCronJob(this.getClass().getSimpleName(), this.getClass(), "0 0 0 ? * * *");
	}
	
	@Override
	public void run(JobExecutionContext context) {
		Log.tenant.info("Executing Tenant validity check for tenant : {}", BaseSession.getTenantUniqueName());
		tenantService.checkAndRenewTenant();
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
