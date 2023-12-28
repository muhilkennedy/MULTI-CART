package com.base.bgwork;

import java.sql.SQLException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.base.service.AuditService;
import com.base.service.BaseService;
import com.base.service.QuartzJobService;
import com.base.util.DatabaseUtil;
import com.base.util.Log;
import com.platform.entity.BaseObject;
import com.platform.exception.BGWorkException;
import com.platform.messages.AuditOperation;
import com.platform.util.PlatformUtil;

import jakarta.annotation.PostConstruct;

/**
 * @author muhil.
 * NOTE: make sure to add tenant filter for all JPA queries
 *         executed via scheduled job.(unable to unwarp hibernate session in quartz job)
 */
@Component
public abstract class BGJob implements Job {

	private final static String FETCH_TENANTS_QUERY = "select rootid from tenant";

	@Autowired
	@Qualifier("TenantService")
	private BaseService baseTenantService;
	
	@Autowired
	@Qualifier("EmployeeService")
	private BaseService baseEmployeeService;

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
			List<?> tenantIds = DatabaseUtil.executeDQL(FETCH_TENANTS_QUERY);
			tenantIds.stream().peek(tenantId -> Log.tenant.info("Executing BGWork {} for tenant : {}",
					this.getClass().getSimpleName(), tenantId)).forEach(tenantId -> {
						setupSession((Long) tenantId);
						try {
							run(context);
						} catch (BGWorkException e) {
							String msg = String.format("Error running job for tenant : {%s} : {%s}", tenantId, e);
							Log.base.error(msg);
							auditService.logAuditInfo(AuditOperation.SCHEDULEDTASKERROR, msg);
						}
						teardownSession();
					});
		} catch (SQLException e) {
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

	protected void setupSession(Long tenantId) {
		BaseEntity tenant = baseTenantService.findById(tenantId);
		BaseSession.setCurrentTenant(tenant);
	}

	protected void setupSession(Long tenantId, Long userId) {
		BaseEntity tenant = baseTenantService.findById(tenantId);
		BaseSession.setCurrentTenant(tenant);
		BaseEntity user = baseEmployeeService.findById(userId);
		BaseSession.setUser(user);
	}

	protected void teardownSession() {
		BaseSession.clear();
	}

}
