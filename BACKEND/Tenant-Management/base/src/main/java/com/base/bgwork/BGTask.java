package com.base.bgwork;

import java.sql.SQLException;
import java.util.List;

import org.jobrunr.JobRunrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.base.service.BaseService;
import com.base.util.DatabaseUtil;
import com.base.util.Log;

/**
 * @author muhil
 */
public abstract class BGTask {
	
	private final static String FETCH_TENANTS_QUERY = "select rootid from tenant";
	
	@Autowired
	@Qualifier("TenantService")
	private BaseService baseTenantService;

	/**
	 * Schedule the job
	 */
	public abstract void schedule();

	/**
	 * Actual work to be executed
	 */
	public abstract void run();
	
	public void runForAllTenants() {
		
		try {
			List<?> tenantIds = DatabaseUtil.executeDQL(FETCH_TENANTS_QUERY);
			tenantIds.stream().peek(tenantId -> Log.tenant.info("Executing BGWork for tenant : {}", tenantId))
					.forEach(tenantId -> {
						setupSession((Long) tenantId);
						run();
						teardownSession();
					});
		} catch (SQLException e) {
			Log.base.error("Error querying tenants : {}",e);
			throw new JobRunrException("Error querying tenants : ",e);
		}
	}

	/**
	 * @param 
	 * event Execute after application startup
	 */
	@EventListener
	private void onApplicationEvent(ApplicationReadyEvent event) {
		schedule();
	}

	protected String getJobId(Class<?> cls) {
		String jobId = cls.getSimpleName();
		Log.base.debug("Registering BG job with Id {} for {}", jobId, cls.getCanonicalName());
		return jobId;
	}
	
	protected void setupSession(Long tenantId) {
		BaseEntity tenant = baseTenantService.findById(tenantId);
		BaseSession.setTenant(tenant);
	}
	
	protected void teardownSession() {
		BaseSession.clear();
	}

}
