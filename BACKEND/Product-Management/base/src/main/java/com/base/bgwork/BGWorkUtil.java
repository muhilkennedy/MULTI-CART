package com.base.bgwork;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.base.service.QuartzJobService;
import com.base.util.Log;
import com.platform.util.PlatformUtil;

/**
 * @author muhil
 * job scheduler using jobrunr library. Using free version we can create upto 100 scheduled jobs. 
 * quartz scheduler is custom implementation. (Advised to use quartz for all new BG operations as its inhouse)
 */
public class BGWorkUtil {

	private static Scheduler scheduler;
	private static QuartzJobService quartzService;

	public static void setScheduler(Scheduler scheduer) {
		BGWorkUtil.scheduler = scheduer;
	}

	public static void setQuartzJobService(QuartzJobService quartzService) {
		BGWorkUtil.quartzService = quartzService;
	}

	/******************************************
	 * Quartz implementations
	 ******************************************/

	/**
	 * @param job Lambda Fire the bg job immedietly
	 * @throws SchedulerException
	 */

	/**
	 * @param jobClass
	 * @throws SchedulerException
	 */
	public static void fireAndForget(Class<? extends Job> jobClass) throws SchedulerException {
		JobDetail jobDetail = createBasicJobDetail(jobClass);
		Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()).startNow().build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * @param jobClass
	 * @param jobData
	 * @throws SchedulerException
	 */
	public static void fireAndForget(Class<? extends Job> jobClass, JobDataMap jobData) throws SchedulerException {
		JobDetail jobDetail = createAdvancedJobDetail(jobClass, jobData, null, false, false);
		Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()).startNow().build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	/**
	 * @param jobClass
	 * @param jobData
	 * @param jobGroup
	 * @throws SchedulerException
	 */
	public static void fireAndForget(Class<? extends Job> jobClass, JobDataMap jobData, String jobGroup) throws SchedulerException {
		JobDetail jobDetail = createAdvancedJobDetail(jobClass, jobData, jobGroup, false, false);
		Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()).startNow().build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * @param jobName
	 * @param jobClass
	 * @param timeUnit
	 * @param time
	 * @throws SchedulerException
	 */
	public static void scheduleBasicJob(String jobName, Class<? extends Job> jobClass, TimeUnit timeUnit, int time)
			throws SchedulerException {
		scheduleBasicJob(jobName, null, jobClass, timeUnit, time, false);
	}
	
	public static void scheduleBasicJob(String jobName, Class<? extends Job> jobClass, TimeUnit timeUnit, int time, boolean overrideJob)
			throws SchedulerException {
		scheduleBasicJob(jobName, null, jobClass, timeUnit, time, overrideJob);
	}

	/**
	 * @param jobName
	 * @param jobGroup
	 * @param jobClass
	 * @param timeUnit
	 * @param time
	 * @param overrideJob
	 * @throws SchedulerException
	 */
	public static void scheduleBasicJob(String jobName, String jobGroup, Class<? extends Job> jobClass,
			TimeUnit timeUnit, int time, boolean overrideJob) throws SchedulerException {
		JobKey key = new JobKey(jobName, StringUtils.isAllBlank(jobGroup) ? PlatformUtil.INTERNAL_SYSTEM : jobGroup);
		if (overrideJob) {
			deleteJobIfExists(key);
		} 
		if (!scheduler.checkExists(key)) { //TODO: check if schedule changed and override
			JobDetail job = createBasicJobDetail(jobClass, key, true, true);
			Trigger trigger = createBasicRecurrentTrigger(job, timeUnit, time);
			scheduler.scheduleJob(job, trigger);
			Log.base.info("scheduleBasicJob : New job scheduled : {} with trigger : {}", job, trigger);
		}
		else {
			Log.base.info("scheduleBasicJob : Existing scheduled job : {}", scheduler.getJobDetail(key));
		}
	}
	
	/**
	 * @param jobName
	 * @param jobClass
	 * @param cron - Seconds Minutes Hours DayOfMonth Month DayOfWeek Year
	 * @throws SchedulerException
	 */
	public static void scheduleCronJob(String jobName, Class<? extends Job> jobClass, String cron)
			throws SchedulerException {
		scheduleCronJob(jobName, null, jobClass, cron, false);
	}
	
	public static void scheduleCronJob(String jobName, Class<? extends Job> jobClass, String cron, boolean overrideJob)
			throws SchedulerException {
		scheduleCronJob(jobName, null, jobClass, cron, overrideJob);
	}

	/**
	 * @param jobName
	 * @param jobGroup
	 * @param jobClass
	 * @param cron
	 * @throws SchedulerException
	 */
	public static void scheduleCronJob(String jobName, String jobGroup, Class<? extends Job> jobClass, String cron, boolean overrideJob)
			throws SchedulerException {
		JobKey key = new JobKey(jobName, StringUtils.isAllBlank(jobGroup) ? PlatformUtil.INTERNAL_SYSTEM : jobGroup);
		if (overrideJob) {
			deleteJobIfExists(key);
		}
		if(!scheduler.checkExists(key)) {
			JobDetail job = createBasicJobDetail(jobClass, key, true, true);
			Trigger trigger = createCronTrigger(job, cron);
			scheduler.scheduleJob(job, trigger);
			Log.base.info("scheduleBasicJob : New job scheduled : {} with trigger : {}", job, trigger);
			Log.base.info("{} - {} - scheduled at {}", jobName, jobGroup, trigger.getNextFireTime());
		}
		else {
			Log.base.info("scheduleBasicJob : Existing scheduled cron job : {}", scheduler.getJobDetail(key));
		}
	}
	/**
	 * @param jobClass
	 * @return
	 * @throws SchedulerException
	 */
	private static JobDetail createBasicJobDetail(Class<? extends Job> jobClass) throws SchedulerException {
		String id = UUID.randomUUID().toString();
		JobKey key = new JobKey(id, PlatformUtil.INTERNAL_SYSTEM);
		return createBasicJobDetail(jobClass, key, false, false);
	}

	/**
	 * @param jobClass
	 * @param isRecurring
	 * @param isDurable
	 * @return
	 * @throws SchedulerException
	 */
	public static JobDetail createBasicJobDetail(Class<? extends Job> jobClass, boolean isRecurring, boolean isDurable)
			throws SchedulerException {
		String id = UUID.randomUUID().toString();
		JobKey key = new JobKey(id, PlatformUtil.INTERNAL_SYSTEM);
		return createBasicJobDetail(jobClass, key, isRecurring, isDurable);
	}

	/**
	 * @param jobClass
	 * @param key
	 * @param isRecurring
	 * @return
	 * @throws SchedulerException
	 */
	public static JobDetail createBasicJobDetail(Class<? extends Job> jobClass, JobKey key, boolean isRecurring)
			throws SchedulerException {
		return createBasicJobDetail(jobClass, key, isRecurring, false);
	}

	/**
	 * @param jobClass
	 * @param key
	 * @param isRecurring
	 * @param isDurable
	 * @return
	 * @throws SchedulerException
	 */
	public static JobDetail createBasicJobDetail(Class<? extends Job> jobClass, JobKey key, boolean isRecurring,
			boolean isDurable) throws SchedulerException {
		deleteJobIfExists(key);
		JobDetail job = JobBuilder.newJob().ofType(jobClass).withIdentity(key).storeDurably(isDurable)
				.requestRecovery(true).withDescription(jobClass.getName()).build();
		quartzService.createQuartzJobInfo(job.getKey().getName(), job.getKey().getGroup(), isRecurring);
		Log.base.info("New Job Created : {}", job);
		return job;
	}

	/**
	 * @param jobClass
	 * @param jobData
	 * @param jobGroup
	 * @param isRecurring
	 * @param isDurable
	 * @return
	 * @throws SchedulerException
	 */
	public static JobDetail createAdvancedJobDetail(Class<? extends Job> jobClass, JobDataMap jobData, String jobGroup,
			boolean isRecurring, boolean isDurable) throws SchedulerException {
		String id = UUID.randomUUID().toString();
		JobKey key = new JobKey(id, StringUtils.isAllBlank(jobGroup)? PlatformUtil.INTERNAL_SYSTEM : jobGroup);
		return createAdvancedJobDetail(jobClass, key, jobData, isRecurring, isDurable);
	}

	/**
	 * @param jobClass
	 * @param key
	 * @param jobData
	 * @param isRecurring
	 * @param isDurable
	 * @return
	 * @throws SchedulerException
	 */
	public static JobDetail createAdvancedJobDetail(Class<? extends Job> jobClass, JobKey key, JobDataMap jobData,
			boolean isRecurring, boolean isDurable) throws SchedulerException {
		deleteJobIfExists(key);
		JobDetail job = JobBuilder.newJob().ofType(jobClass).withIdentity(key).storeDurably(isDurable)
				.requestRecovery(true).withDescription(jobClass.getName()).setJobData(jobData).build();
		quartzService.createQuartzJobInfo(job.getKey().getName(), job.getKey().getGroup(), isRecurring);
		Log.base.info("New Job Created : {}", job);
		return job;
	}

	/***
	 * TODO: Multiple triggers possible for single job, overload to support
	 * behaviour Note: adviced not to schedule any jobs forever, if scheduled have a
	 * business logic to unschedule as required!
	 * @param jobDetail
	 * @param timeUnit
	 * @param time
	 * @return
	 */
	private static Trigger createBasicRecurrentTrigger(JobDetail jobDetail, TimeUnit timeUnit, int time) {
		Trigger trigger = null;
		switch (timeUnit) {
		case SECONDS:
			trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
					.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(time)).build();
			break;
		case MINUTES:
			trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
					.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
					.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(time)).build();
			break;
		case HOURS:
			trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
					.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
					.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(time)).build();
			break;
		default:
			throw new UnsupportedOperationException();
		}
		return trigger;
	}

	/**
	 * @param jobDetail
	 * @param cronExpression - Seconds Minutes Hours DayOfMonth Month DayOfWeek Year
	 * @return
	 */
	public static Trigger createCronTrigger(JobDetail jobDetail, final String cronExpression) {
		return TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	}

	/**
	 * @param key
	 * @throws SchedulerException
	 */
	public static void deleteJobIfExists(JobKey key) throws SchedulerException {
		if (scheduler.checkExists(key)) {
			List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(key);
			for (Trigger trigger : triggers) {
				scheduler.unscheduleJob(trigger.getKey());
			}
			scheduler.deleteJob(key);
		}
	}
	
	/**
	 * @param key
	 * @throws SchedulerException
	 */
	public static void forceFireJob(JobKey key) throws SchedulerException {
		scheduler.triggerJob(key);
		Log.base.warn("Force Triggered job : {}", key);
	}

}
