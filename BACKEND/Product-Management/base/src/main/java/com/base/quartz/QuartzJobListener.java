package com.base.quartz;

import java.sql.SQLException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.stereotype.Component;

import com.base.util.DatabaseUtil;
import com.base.util.Log;
import com.platform.messages.ScheduledTaskStatus;

/**
 * @author Muhil
 * This listener will be invoked before and after job trigger everytime.
 * we make us of this to persist job state on our internal table.
 */
@Component
public class QuartzJobListener extends JobListenerSupport {

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		Log.base.info("QuartzJobListener : Starting to execute job : {}", context.toString());
		JobKey key = context.getJobDetail().getKey();
		updateJobStatus(key.getName(), key.getGroup(), ScheduledTaskStatus.INPROGRESS.name(), null);
		super.jobToBeExecuted(context);
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		super.jobWasExecuted(context, jobException);
		Log.base.info("QuartzJobListener : Completed executing job : {}", context.toString());
		JobKey key = context.getJobDetail().getKey();
		if (jobException != null) {
			Log.base.error("QuartzJobListener : Failed to execute job : {} with error : {}", key, jobException);
			updateJobStatus(key.getName(), key.getGroup(), ScheduledTaskStatus.FAILED.name(),
					jobException.getMessage());
			return;
		}
		updateJobStatus(key.getName(), key.getGroup(), ScheduledTaskStatus.SUCCESS.name(), null);
	}

	private void updateJobStatus(String jobName, String jobGroup, String status, String errorMsg) {
		try {
			//TODO: sanitize this query
			DatabaseUtil.executeDML(String.format(
					"update quartzjobinfo set jobstatus='%s', errorinfo='%s' where jobname='%s' and jobgroup='%s'",
					status, errorMsg, jobName, jobGroup));
		} catch (SQLException e) {
			Log.base.error("Exception updating job status : {}", e);
			throw new RuntimeException(e);
		}
	}

}
