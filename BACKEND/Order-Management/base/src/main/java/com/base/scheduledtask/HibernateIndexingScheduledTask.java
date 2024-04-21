package com.base.scheduledtask;

import java.util.concurrent.TimeUnit;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGJob;
import com.base.bgwork.BGWorkUtil;
import com.base.service.HibernateSearchService;
import com.base.util.Log;

/**
 * @author Muhil
 * runs spring scheduled task every 6 hours to clean temp files over 24hours.
 */
@Component
public class HibernateIndexingScheduledTask extends BGJob {

	@Autowired
	private HibernateSearchService searchService;

	@Override
	public void schedule() throws SchedulerException {
		BGWorkUtil.scheduleBasicJob(this.getClass().getSimpleName(), this.getClass(), TimeUnit.HOURS, 6);
	}

	@Override
	public void run(JobExecutionContext context) {
		Log.base.info("Starting HibernateIndexingScheduledTask");
		searchService.performIndexing();
		Log.base.info("Completed HibernateIndexingScheduledTask");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.run(arg0);
	}

}
