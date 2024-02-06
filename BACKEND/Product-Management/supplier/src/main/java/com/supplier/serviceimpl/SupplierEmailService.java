package com.supplier.serviceimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.server.BaseSession;
import com.base.service.EmailService;
import com.base.util.Log;
import com.platform.entity.PlatformTenant;
import com.platform.entity.PlatformTenantDetails;
import com.supplier.entity.Supplier;

import freemarker.template.TemplateException;

/**
 * @author Muhil
 */
@Service
public class SupplierEmailService {

	private static final String SUPPLIER_REG = "SUPPLIER_REGISTRATION";

	@Autowired
	private EmailService emailService;

	public void sendSupplierOnboardingMail(Supplier supplier) {
		PlatformTenantDetails tenantDetails = ((PlatformTenant) BaseSession.getCurrentTenant()).getTenantDetail();
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("supplierName", supplier.getName());
		contentMap.put("uniqueName", supplier.getUniquename());
		contentMap.put("mobile", tenantDetails.getContact());
		contentMap.put("email", tenantDetails.getEmailid());
		contentMap.put("tenantLogo", tenantDetails.getDetails().getLogoUrl());
		try {
			emailService.sendMail(supplier.getEmailid(),
					String.format("Supplier onboarded to %s",
							((PlatformTenant) BaseSession.getCurrentTenant()).getName()),
					emailService.constructEmailBody(SUPPLIER_REG, contentMap), null);
		} catch (IOException | TemplateException e) {
			Log.supplier.error("Exception sending mail to user {0} :: error :: {1}", supplier.getEmailid(), e);
		}
	}

}
