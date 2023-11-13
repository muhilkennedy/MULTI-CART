package com.tenant.bgwork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGTask;
import com.base.bgwork.BGWorkUtil;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.tenant.service.TenantService;

/**
 * @author muhil
 */
@Component
public class TenantValidityScheduledTask extends BGTask {
	
	@Autowired
	private TenantService tenantService;
	
	@Override
	public void schedule() {
		BGWorkUtil.scheduleJob(getJobId(getClass()), "0 0 * * *", () -> {
			this.runForAllTenants();
		});
	}
	
	@Override
	public void run() {
		Log.tenant.info("Executing Tenant validity check for tenant : {}", BaseSession.getTenantUniqueName());
		tenantService.checkAndRenewTenant();
	}

}
