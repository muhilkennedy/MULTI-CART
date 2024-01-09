package com.base.bgwork;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.base.server.BaseSession;
import com.base.service.AuditService;
import com.base.service.QuartzJobService;
import com.base.util.Log;
import com.platform.entity.PlatformTenant;
import com.platform.entity.PlatformUser;
import com.platform.exception.BGWorkException;
import com.platform.exception.TenantException;
import com.platform.messages.AuditOperation;
import com.platform.service.TenantService;
import com.platform.service.UserService;

import jakarta.annotation.PostConstruct;

/**
 * @author muhil.
 */
@Component
public abstract class BGJob implements Job {

	@Autowired
	private Scheduler quartzScheduler;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private QuartzJobService quartzJobService;

	@Value("${spring.quartz.overwrite-existing-jobs}")
	private boolean overrideJobs;
	
	@PostConstruct
	private void initialize() {
		BGWorkUtil.setScheduler(quartzScheduler);
		BGWorkUtil.setQuartzJobService(quartzJobService);
	}

	/**
	 * @param event Execute after application startup to schedule the default jobs.
	 * @throws SchedulerException
	 */
	@EventListener
	private void onApplicationEvent(ApplicationReadyEvent event) throws SchedulerException {
		schedule();
	}

	/**
	 * Schedule the job
	 * 
	 * @throws SchedulerException
	 */
	public abstract void schedule() throws SchedulerException;

	public void schedule(JobDetail jobdetail, Trigger trigger) throws SchedulerException {
		quartzScheduler.scheduleJob(jobdetail, trigger);
	}

	/**
	 * Actual work to be executed. (business logic)
	 */
	public abstract void run(JobExecutionContext context) throws BGWorkException;

	public void runForAllTenants(JobExecutionContext context) throws BGWorkException {
		try {
			List<PlatformTenant> tenants = TenantService.getAllTenants();
			tenants.stream().peek(tenant -> Log.tenant.info("Executing BGWork {} for tenant : {}",
					this.getClass().getSimpleName(), tenant.getUniquename())).forEach(tenant -> {
						try {
							setupSession(tenant.getUniquename());
							run(context);
						} catch (BGWorkException | TenantException e) {
							String msg = String.format("Error running job for tenant : {%s} : {%s}",
									tenant.getUniquename(), e);
							Log.base.error(msg);
							auditService.logAuditInfo(AuditOperation.SCHEDULEDTASKERROR, msg);
						}
						teardownSession();
					});
		} catch (TenantException e) {
			Log.base.error("Error querying tenants : {}", e);
			auditService.logAuditInfo(AuditOperation.SCHEDULEDTASKERROR, e.getMessage());
			throw new BGWorkException("Error querying tenants : " + e.getMessage());
		}
	}

	protected String getJobId(Class<?> cls) {
		String jobId = cls.getSimpleName();
		Log.base.debug("Registering BG job with Id {} for {}", jobId, cls.getCanonicalName());
		return jobId;
	}

	protected void setupSession(String tenantUniqueName) throws TenantException {
		PlatformTenant tenant = TenantService.findByUniqueName(tenantUniqueName);
		BaseSession.setCurrentTenant(tenant);
	}

	protected void setupSession(String tenantUniqueName, Long userId) throws TenantException {
		PlatformTenant tenant = TenantService.findByUniqueName(tenantUniqueName);
		BaseSession.setCurrentTenant(tenant);
		PlatformUser user = UserService.findById(userId);
		BaseSession.setUser(user);
	}

	protected void teardownSession() {
		BaseSession.clear();
	}

}
