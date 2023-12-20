package com.base.bgwork;

import java.util.concurrent.TimeUnit;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import com.base.util.Log;
import com.platform.util.FileUtil;

/**
 * @author Muhil 
 * runs spring scheduled task every 6 hours to clean temp files over 24hours.
 */
@Component
public class TempFileCleanUpScheduledTask extends BGJob {

	//@Scheduled(fixedDelay = 21600000)
	@Override
	public void schedule() throws SchedulerException {
		BGWorkUtil.scheduleBasicJob(this.getClass().getSimpleName(), this.getClass(), TimeUnit.HOURS, 6);
	}

	@Override
	public void run() {
		Log.base.info("Starting TempFileCleanUpScheduledTask");
		FileUtil.cleanUpTempDirectory();
		Log.base.info("Completed TempFileCleanUpScheduledTask");
	}
 
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.run();
	}

}
