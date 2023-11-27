package com.tenant.serviceimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.server.BaseSession;
import com.base.service.EmailService;
import com.base.util.Log;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantSubscription;

import freemarker.template.TemplateException;

@Service
public class TenantEmailService {

	@Autowired
	private EmailService emailService;

	private static final String ONBOARD_FTL = "Tenant-Onboarding";

	public void sendOnbardingMail(Tenant tenant, TenantSubscription subscription) {
		/*List<Map> inlineMapList = EmailUtil.getBasicInlineImages(tenant.getUniqueId(), tenant.getTenantName(),
				tenant.getTenantDetail().getTagline(), tenant.getTenantDetail().getContact(),
				tenant.getTenantDetail().getEmailid(), tenant.getTenantUniqueName());
		Map<String, File> inlineImages = inlineMapList.get(0);*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("tenantLogo", ((Tenant)BaseSession.getCurrentTenant()).getTenantDetail().getDetails().getLogoUrl());
		map.put("tenantName", tenant.getName());
		map.put("tenantUniqueName", tenant.getUniquename());
		map.put("validity", subscription.getStartdate().toString() + " to " + subscription.getEnddate().toString());
		map.put("adminUrl", "");
		try {
			emailService.sendMail(tenant.getTenantDetail().getEmailid(),
					tenant.getTenantName() + " : NEW ACCOUNT CREATED",
					emailService.constructEmailBody(ONBOARD_FTL, map), null);
		} catch (IOException | TemplateException e) {
			Log.tenant.error("Exception sending onboarding mail : {}", e);
		}
	}

}
