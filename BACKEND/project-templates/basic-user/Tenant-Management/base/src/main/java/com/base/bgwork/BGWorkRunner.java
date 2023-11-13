package com.base.bgwork;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.lambdas.JobLambda;
import org.jobrunr.scheduling.BackgroundJob;

import com.base.util.Log;

/**
 * @author muhil job scheduler using jobrunr library. Using free version we can
 *         create upto 100 scheduled jobs.
 */
public class BGWorkRunner {

	/**
	 * @param job Lambda Fire the bg job immedietly
	 */
	public static void fireAndForget(JobLambda job) {
		fireAndForget(0, null, job);
	}

	/**
	 * @param plusSeconds trigger job from current instant + secs
	 * @param job         Lamda
	 */
	public static void fireAndForget(long plusSeconds, JobLambda job) {
		fireAndForget(plusSeconds, TimeUnit.SECONDS, job);
	}

	/**
	 * @param plusTime from time to trigger job
	 * @param timeUnit millis/secs
	 * @param job      Lambda
	 */
	public static void fireAndForget(long plusTime, TimeUnit timeUnit, JobLambda job) {
		Instant currentInstant = Instant.now();
		switch (timeUnit) {
		case SECONDS:
			currentInstant.plusSeconds(plusTime);
			break;
		case MILLISECONDS:
			currentInstant.plusMillis(plusTime);
			break;
		case HOURS:
			currentInstant.plusSeconds(plusTime * 60 * 60);
			break;
		default:
			throw new IllegalArgumentException("TimeUnit not supported!");
		}
		JobId jobId = BackgroundJob.schedule(currentInstant, job);
		Log.base.info("fireAndForget : " + jobId.toString());
	}

	/**
	 * @param cronExpression
	 * @param job
	 */
	public static void fireAndSchedule(String cronExpression, JobLambda job) {
		BackgroundJob.scheduleRecurrently(cronExpression, job);
	}

	/**
	 * @param jobId
	 * @param cronExpression
	 * @param job
	 */
	public static void fireAndSchedule(String jobId, String cronExpression, JobLambda job) {
		BackgroundJob.scheduleRecurrently(jobId, cronExpression, job);
	}

}
