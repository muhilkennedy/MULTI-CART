package com.base.scheduledtask;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.platform.email.EmailTask;

/**
 * @author Muhil
 *
 */
public class EmailJob implements Job {

	public static final String JOB_GROUP = "EMAIL";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		Long tenantId = dataMap.getLong(EmailTask.KEY_TENANATID);
		List<String> recipients = (List) dataMap.get(EmailTask.KEY_RECIPIENTS);
		String subject = dataMap.getString(EmailTask.KEY_SUBJECT);
		String body = dataMap.getString(EmailTask.KEY_BODY);
		Map<String, File> map = (Map) dataMap.get(EmailTask.KEY_INLINEIMAGES);
		List<File> attachments = (List) dataMap.get(EmailTask.KEY_ATTACHMENTS);
		List<String> cc = (List) dataMap.get(EmailTask.KEY_CARBONCOPY);
		EmailTask task = new EmailTask(tenantId, recipients, cc, subject, body, map, attachments);
		try {
			task.execute();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

}
