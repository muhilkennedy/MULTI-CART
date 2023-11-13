package com.tenant.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.server.BaseSession;
import com.platform.service.StorageService;
import com.platform.util.ImageUtil;
import com.tenant.dao.TenantDaoService;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantSubscription;
import com.tenant.messages.TenantRequestBody;
import com.tenant.model.TenantInfo;
import com.tenant.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantDaoService tenantDao;

	@Override
	public BaseEntity findById(Long rootId) {
		return tenantDao.findById(rootId);
	}

	@Override
	public Tenant findByUniqueName(String uniqueName) {
		Tenant tenant = tenantDao.findByUniqueName(uniqueName);
		return tenant;
	}

	@Override
	public List<Tenant> findAllTenants() {
		return tenantDao.findAll();
	}
	
	@Override
	public Tenant createTenant(TenantRequestBody tenantModel) {
		Tenant newTenant = new Tenant();
		newTenant.setName(tenantModel.getTenantName());
		newTenant.setUniquename(tenantModel.getUniqueName());
		newTenant.setParent(tenantModel.getParentTenant());
		tenantDao.saveAndFlush(newTenant);
		// reset base session to new thread context.
		BaseSession.setTenant(newTenant);
		TenantDetails newTenantDetails = new TenantDetails();
		newTenantDetails.setBusinessemail(tenantModel.getBusinessEmail());
		newTenantDetails.setBusinessemailpassword(tenantModel.getBusinessPassword());
		newTenantDetails.setTagline(tenantModel.getTagLine());
		newTenantDetails.setContact(tenantModel.getContact());
		newTenantDetails.setCity(tenantModel.getCity());
		newTenantDetails.setStreet(tenantModel.getStreet());
		newTenantDetails.setEmailid(tenantModel.getEmail());
		TenantInfo tenantInfo = new TenantInfo();
		tenantInfo.setGmapUrl(tenantModel.getDetails().getGmapUrl());
		tenantInfo.setFssai(tenantModel.getDetails().getFssai());
		tenantInfo.setGstin(tenantModel.getDetails().getGstin());
		newTenantDetails.setDetails(tenantInfo);
		newTenant.setTenantDetail(newTenantDetails);
		if(tenantModel.isStartAndEndDatePresent()){
			TenantSubscription newSubscription = new TenantSubscription();
			newSubscription.setStartdate(tenantModel.getStartDate());
			newSubscription.setEnddate(tenantModel.getEndDate());
			// save tenant subscription.
		}
		tenantDao.saveAndFlush(newTenantDetails);
		//emailService.sendOnbardingMail(newTenant, newSubscription);
		return newTenant;
	}

	@Override
	public Tenant uploadLogo(File logo) throws IOException {
		Tenant tenant = (Tenant) BaseSession.getTenant();
		tenant.getTenantDetail().getDetails().setLogoUrl(StorageService.getStorage().saveFile(logo));
		// now compress and store thumbnail image
		tenant.getTenantDetail().getDetails()
				.setLogoThumbnail(StorageService.getStorage().saveFile(ImageUtil.getPNGThumbnailImage(logo)));
		return (Tenant) tenantDao.save(tenant);
	}
	
	
}
